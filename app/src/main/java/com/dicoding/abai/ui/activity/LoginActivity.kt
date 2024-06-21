package com.dicoding.abai.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.dicoding.abai.R
import com.dicoding.abai.databinding.ActivityLoginBinding
import com.dicoding.abai.viewmodel.LoginViewModel
import com.dicoding.abai.viewmodel.ViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

//
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // The user is already signed in, navigate to MainActivity
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish() // finish the current activity to prevent the user from coming back to the SignInActivity using the back button
        }

        binding.btnGoogle.setOnClickListener {
            signIn()
        }

        loginViewModel.isLoginSuccess.observe(this, Observer { user ->
            if (user != null) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finish()
            }
        })

        loginViewModel.isLoginError.observe(this, Observer { exception ->
            if (exception != null) {
                if (loginViewModel.getErrorCode() != "404") {
                    Log.d(TAG, "LOGIN ERROR = ${loginViewModel.getErrorCode()}")
                    Log.d(TAG, exception.message.toString())
                    Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "LOGIN ERROR = ${loginViewModel.getErrorCode()}")
                }
            }
        })

        loginViewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading == true) {
                // Show loading indicator
                binding.progressIndicator.visibility = View.VISIBLE
            } else {
                // Hide loading indicator
                binding.progressIndicator.visibility = View.GONE
            }
        })

    }

    private fun signIn() {
        val clientId = getString(R.string.client_id)
        loginViewModel.signIn(this, clientId)
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
            finish()
        }
    }




}