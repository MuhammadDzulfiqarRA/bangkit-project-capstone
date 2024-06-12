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
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //binding
        binding.btnLogin.setOnClickListener {
            if (binding.edLoginUsername.text != null && binding.edLoginPassword.text != null) {
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
            } else{
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.form_login_error),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }


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

        loginViewModel.signInSuccess.observe(this, Observer { user ->
            if (user != null) {
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finish()
            }
        })

        loginViewModel.signInFailure.observe(this, Observer { exception ->
            if (exception != null) {
                Log.d(TAG, exception.message.toString())
                Toast.makeText(this, "Sign-in failed", Toast.LENGTH_SHORT).show()
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

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
            finish()
        }
    }

//    private fun signIn() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        val googleSignInClient = GoogleSignIn.getClient(this, gso)
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
//                Log.d("LOGIN ACTIVITY", "onActivityResult: ${e.message}")
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val user = auth.currentUser
//                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this, DashboardActivity::class.java))
//                    finish()
//                } else {
//                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//

        // SIGN IN BARU
//    private fun signIn() {
//        val credentialManager = CredentialManager.create(this) //import from androidx.CredentialManager
//
//        val googleIdOption = GetGoogleIdOption.Builder()
//            .setFilterByAuthorizedAccounts(false)
//            .setServerClientId(getString(R.string.client_id)) //from https://console.firebase.google.com/project/firebaseProjectName/authentication/providers
//            .build()
//
//        val request = GetCredentialRequest.Builder() //import from androidx.CredentialManager
//            .addCredentialOption(googleIdOption)
//            .build()
//
//        lifecycleScope.launch {
//            try {
//                val result: GetCredentialResponse = credentialManager.getCredential( //import from androidx.CredentialManager
//                    request = request,
//                    context = this@LoginActivity,
//                )
//                handleSignIn(result)
//            } catch (e: GetCredentialException) { //import from androidx.CredentialManager
//                Log.d("Error", e.message.toString())
//            }
//        }
//    }
//
//    private fun handleSignIn(result: GetCredentialResponse) {
//        // Handle the successfully returned credential.
//        when (val credential = result.credential) {
//            is CustomCredential -> {
//                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//                    try {
//                        // Use googleIdTokenCredential and extract id to validate and authenticate on your server.
//                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
//                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
//                    } catch (e: GoogleIdTokenParsingException) {
//                        Log.e(TAG, "Received an invalid google id token response", e)
//                    }
//                } else {
//                    // Catch any unrecognized custom credential type here.
//                    Log.e(TAG, "Unexpected type of credential")
//                }
//            }
//
//            else -> {
//                // Catch any unrecognized credential type here.
//                Log.e(TAG, "Unexpected type of credential")
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithCredential:success")
//                    val user: FirebaseUser? = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
//                }
//            }
//    }
//
//    private fun updateUI(currentUser: FirebaseUser?) {
//        if (currentUser != null) {
//            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
//            finish()
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }



}