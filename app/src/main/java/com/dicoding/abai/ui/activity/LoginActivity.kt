package com.dicoding.abai.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.abai.R
import com.dicoding.abai.databinding.ActivityDashboardBinding
import com.dicoding.abai.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //binding
        binding.btnLogin.setOnClickListener {
            if (binding.edLoginUsername.text != null && binding.edLoginPassword.text != null) {

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
    }
}