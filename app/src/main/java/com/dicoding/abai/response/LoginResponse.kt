package com.dicoding.abai.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("firstname")
	val firstname: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("lastname")
	val lastname: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
