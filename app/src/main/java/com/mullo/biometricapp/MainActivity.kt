package com.mullo.biometricapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mullo.biometricapp.databinding.ActivityMainBinding
import com.mullo.biometricapp.viewModels.MainViewModel
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding

    //desaparece con viewBinding
    private lateinit var btnFinger: ImageView
    private lateinit var txtInfo: TextView

    //permite ejecutar aplicaciones en segundo plano
    private lateinit var executor: Executor

    //sirve para mostrar o permitir manejar los eventos del biometrico
    private lateinit var biometricPrompt: BiometricPrompt

    //es el dialogo que se muestra
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        initListeners()
        initObservables()
        autenticationVariables()
        mainViewModel.checkBiometric(this)

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // startActivity(Intent(this, MainActivity2::class.java))
            binding.etxUser.visibility = View.GONE
            binding.etxPassword.visibility = View.GONE

            binding.imgFinger.visibility = View.VISIBLE
            binding.txtInfo.text = getString(R.string.biometric_succes)
        } else {
            binding.imgFinger.visibility = View.GONE
            binding.txtInfo.text = getString(R.string.no_user)
        }
    }

    private fun initListeners() {
        //desaparece con viewBinding
        btnFinger = findViewById<ImageView>(R.id.imgFinger)
        txtInfo = findViewById(R.id.txtInfo)

        btnFinger.setOnClickListener {

            biometricPrompt.authenticate(promptInfo)
        }


        //////////////

        binding.btnSaveUser.setOnClickListener {
            createNewUsers(
                binding.etxUser.text.toString(),
                binding.etxPassword.text.toString()
            )
        }
        binding.btnSignInUser.setOnClickListener {
            signInUsers(
                binding.etxUser.text.toString(),
                binding.etxPassword.text.toString()
            )
        }
    }


    private fun autenticationVariables() {
        executor = ContextCompat.getMainExecutor(this)//esto se ejecuta en un hilo

        biometricPrompt = BiometricPrompt(this, executor,//se ejecuta en segundo plano
            object :
                BiometricPrompt.AuthenticationCallback() {//devuelve asincronicamente las llamadas de acuerdo a lo que suceda

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.e("MY_APP_TAG", "Authentication Failed")
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e("MY_APP_TAG", "Authentication Error")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(this@MainActivity, MainActivity2::class.java))
                }

            })


        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)//aqui es opara mostrar el dedo y el pin
            //.setNegativeButtonText("Cancel")
            .build()


    }

    private fun initObservables() {

        mainViewModel.resultCheckBiometric.observe(this) { code ->
            when (code) {

                BiometricManager.BIOMETRIC_SUCCESS -> {
                    btnFinger.visibility = View.VISIBLE
                    txtInfo.text = getString(R.string.biometric_succes)
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                }


                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    txtInfo.text = getString(R.string.biometric_no_hardware)
                    Log.e("MY_APP_TAG", "No biometric features available on this device.")
                }


                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                }


                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    // Prompts the user to create credentials that your app accepts.
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                        )
                    }
                    startActivityForResult(enrollIntent, 100)
                }
            }
        }

    }


    private fun createNewUsers(user: String, password: String) {
        auth.createUserWithEmailAndPassword(
            user,
            password
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Snackbar.make(
                        this, binding.etxUser,
                        "createUserWithEmail:success",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                    binding.etxUser.text.clear()
                    binding.etxPassword.text.clear()
                } else {
                    // If sign in fails, display a message to the user.l
                    Snackbar.make(
                        this, binding.etxUser,
                        task.exception!!.message.toString(),
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }
    }

    private fun signInUsers(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                //Funciona cunado se loguea
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    startActivity(Intent(this, MainActivity2::class.java))
                } else {//cuando no se loguea
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Snackbar.make(
                        this, binding.etxUser,
                        "signInWithEmail:failure",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }
    }


}