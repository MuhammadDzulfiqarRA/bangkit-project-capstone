package com.dicoding.abai.helper

data class RegisterRequest(
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val password: String
)
