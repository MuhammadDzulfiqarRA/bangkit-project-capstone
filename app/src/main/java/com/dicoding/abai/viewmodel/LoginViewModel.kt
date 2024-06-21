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
import com.dicoding.abai.database.User
import com.dicoding.abai.database.UserRepository
import com.dicoding.abai.helper.LoginRequest
import com.dicoding.abai.helper.RegisterRequest
import com.dicoding.abai.helper.UserModel
import com.dicoding.abai.response.LoginResponse
import com.dicoding.abai.response.RegisterResponse
import com.dicoding.abai.retrofit.ApiConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class LoginViewModel (private val repository: UserRepository) : ViewModel() {

    private var auth: FirebaseAuth

    private lateinit var userName: String
    private  lateinit var userEmail: String
    private  lateinit var userPassword: String
    private  lateinit var userPhotoUrl: String
    private  lateinit var errorCode: String

    private lateinit var userId: String

    private val _signInSuccess = MutableLiveData<FirebaseUser?>()
    val signInSuccess: LiveData<FirebaseUser?> = _signInSuccess

    private val _signInFailure = MutableLiveData<Exception?>()
    val signInFailure: LiveData<Exception?> = _signInFailure

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _registerSuccess = MutableLiveData<RegisterResponse?>()
    val registerSuccess: LiveData<RegisterResponse?> = _registerSuccess

    private val _registerError = MutableLiveData<Throwable?>()
    val registerError: LiveData<Throwable?> = _registerError

    private val _isLoginSuccess: MutableLiveData<LoginResponse> = MutableLiveData()
    val isLoginSuccess: LiveData<LoginResponse> = _isLoginSuccess

    private val _isLoginError: MutableLiveData<Throwable?> = MutableLiveData()
    val isLoginError: LiveData<Throwable?> = _isLoginError

    init {
        auth = FirebaseAuth.getInstance()
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getErrorCode(): String {
        return errorCode
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
//                    _signInSuccess.postValue(user)
                    userName = user?.displayName.toString().replace("\\s+".toRegex(), "")
                    userEmail = user?.email.toString()
                    userPassword = userName.toLowerCase() + "@User1"
                    userPhotoUrl = user?.photoUrl.toString()
                    Log.d(TAG, "firebaseAuthWithGoogle: EMAIL = ${userEmail}")
                    Log.d(TAG, "firebaseAuthWithGoogle: USERNAME = ${userName}")
                    Log.d(TAG, "firebaseAuthWithGoogle: PASSWORD = ${userPassword}")
                    Log.d(TAG, "firebaseAuthWithGoogle: PHOTO = ${userPhotoUrl}")
                    login(userName, userPassword)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    _signInFailure.postValue(task.exception)
                }
                _loading.value = false
            }
    }

    private fun register(firstname: String, lastname: String, username: String, email: String, password: String) {
        _loading.value = true
        Log.d("REGISTER", "Registering with email: $email")

        val requestBody = RegisterRequest(firstname, lastname, username, email, password)

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().register(requestBody)
                response.enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        _loading.value = false
                        if (response.isSuccessful) {
                            Log.d("REGISTER", "onResponse: REGISTER SUCCESS")
                            viewModelScope.launch {
                                saveUserToDatabase(userName, userEmail, userPhotoUrl, 0, 0, 0)
                                Log.d(TAG, " SAVE USER: ${userName} ${userEmail} ${userPhotoUrl} ")
                            }
                            login(username, password)
                            // Handle successful registration
                        } else {
                            _registerError.postValue(Exception("Registration failed with code: ${response.code()}"))
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        _loading.value = false
                        _registerError.postValue(t)
                    }
                })
            } catch (t: Throwable) {
                _loading.value = false
                _registerError.postValue(t)
            }
        }
    }


    private fun login(username: String, password: String) {
        _loading.value = true
        Log.d("LOGIN", "Login: $username, $password")

        val requestBody = LoginRequest(username, password)

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().login(requestBody)
                response.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        _loading.value = false
                        if (response.isSuccessful) {
                            Log.d("LOGIN", "onResponse: LOGIN SUCCESS")
                            _isLoginSuccess.value = response.body() as LoginResponse
//                            viewModelScope.launch {
//                                repository.deleteAllUsers()
//                            }
                            viewModelScope.launch {
                                val idUser = repository.getUserIdByUsername(username)
                                userId = idUser.toString()
                                saveSession(UserModel(username))
                            }
                        } else {
                            register(username, username, username, userEmail, password)
                            errorCode = response.code().toString()
                            _isLoginError.postValue(Exception("Login failed with code: ${response.code()}"))
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        _loading.value = false
                        _isLoginError.postValue(t)
                    }
                })
            } catch (t: Throwable) {
                _loading.value = false
                _isLoginError.postValue(t)
            }
        }
    }

    private fun saveUserToDatabase(username: String, email: String, photoUrl: String?, rank: Int, storyCount: Int, missionCompleted: Int) {
        val newUser = User(username = username, email = email, photoUrl = photoUrl, rank = rank, storyCount = storyCount, missionCompleted = missionCompleted )
        viewModelScope.launch {
            try {
                repository.saveUser(newUser)
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error saving user to database: ${e.message}")
            }
        }
    }

    fun getUserId() : String {
        return userId
    }



}
