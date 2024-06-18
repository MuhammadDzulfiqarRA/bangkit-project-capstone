package com.dicoding.abai.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.abai.R
import com.dicoding.abai.adapter.SectionsPagerAdapter
import com.dicoding.abai.databinding.ActivityDashboardBinding
import com.dicoding.abai.viewmodel.DashboardViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("title")
        var sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username ?: ""
        binding.viewPager.adapter = sectionsPagerAdapter

        mAuth = com.google.firebase.ktx.Firebase.auth
        val firebaseUser = mAuth.currentUser

        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            val userName = firebaseUser.displayName
            binding.textViewUsername.text = "${userName}"
            if (firebaseUser.photoUrl != null) {
                Glide.with(binding.imgUserProfile)
                    .load(firebaseUser.photoUrl)
                    .into(binding.imgUserProfile)
            } else {
                Glide.with(binding.imgUserProfile)
                    .load(R.drawable.user_icon)
                    .into(binding.imgUserProfile)
            }

        }


        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                // Memanggil searchUser di DashboardViewModel
                dashboardViewModel.searchUser(searchView.text.toString())
                Toast.makeText(this@DashboardActivity, searchView.text, Toast.LENGTH_SHORT).show()
                false
            }
        }

        binding.imgUserProfile.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, SettingActivity::class.java))
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Semua"
                1 -> "Rekomendasi"
                2 -> "Histori"
                3 -> "Tema"
                else -> ""
            }
        }.attach()




    }

    private fun signOut() {
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(this@DashboardActivity)

            mAuth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
            finish()
        }
    }

//    // google signin
//    mAuth = FirebaseAuth.getInstance()
//
//
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(getString(R.string.client_id))
//        .requestEmail()
//        .build()
//
//    mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//
//    val auth = Firebase.auth
//    val user = auth.currentUser
//
//    if (user != null) {
//        val userName = user.displayName
//        binding.textViewUsername.text = "${userName}"
//    } else {
//        // Handle the case where the user is not signed in
//    }



}