package com.dicoding.abai.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth

    private val _signInSuccess = MutableLiveData<FirebaseUser?>()
    val signInSuccess: LiveData<FirebaseUser?> = _signInSuccess

    private val _signInFailure = MutableLiveData<Exception?>()
    val signInFailure: LiveData<Exception?> = _signInFailure

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        auth = FirebaseAuth.getInstance()
    }

    fun signIn(context: Context, clientId: String) {
        _loading.value = true
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(clientId)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            try {
                val result: GetCredentialResponse = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Log.d("Error", e.message.toString())
                _signInFailure.postValue(e)
                _loading.value = false
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                        _signInFailure.postValue(e)
                        _loading.value = false
                    }
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                    _signInFailure.postValue(Exception("Unexpected type of credential"))
                    _loading.value = false
                }
            }

            else -> {
                Log.e(TAG, "Unexpected type of credential")
                _signInFailure.postValue(Exception("Unexpected type of credential"))
                _loading.value = false
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user: FirebaseUser? = auth.currentUser
                    _signInSuccess.postValue(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    _signInFailure.postValue(task.exception)
                }
                _loading.value = false
            }
    }
}
