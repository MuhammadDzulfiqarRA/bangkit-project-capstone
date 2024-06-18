package com.dicoding.abai.retrofit

import com.dicoding.abai.helper.LoginRequest
import com.dicoding.abai.helper.RegisterRequest
import com.dicoding.abai.response.DummyResponseAPI
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.response.LoginResponse
import com.dicoding.abai.response.MissionResponse
import com.dicoding.abai.response.RegisterResponse
import com.dicoding.abai.response.StoriesResponse
import com.dicoding.abai.response.StoryDetailsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") query: String): Call<DummyResponseAPI>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DummyResponseAPI>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("stories")
    fun getStories(): Call<StoriesResponse>

    @GET("story-details/{story_id}/{user_id}")
    fun getStoriesData(
        @Path("story_id") storyId : Int?,
        @Path("user_id") userId: Int?
    ): Call<StoryDetailsResponse>

    @GET("missions/{user_id}")
    fun getMissionData(
        @Path("user_id") userId: Int?
    ): Call<MissionResponse>


//    @FormUrlEncoded
//    @POST("auth/register")
//    fun register(
//        @Field("firstname") firstname: String,
//        @Field("lastname") lastname: String,
//        @Field("username") username: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<RegisterResponse>
//
//
//    @FormUrlEncoded
//    @POST("auth/login-email")
//    fun login(
//        @Field("email") email: String
//    ): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    // Endpoint untuk login berdasarkan email
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>


}