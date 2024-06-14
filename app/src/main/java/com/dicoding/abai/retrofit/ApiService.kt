package com.dicoding.abai.retrofit

import com.dicoding.abai.response.DummyResponseAPI
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.response.StoriesResponse
import com.dicoding.abai.response.StoryDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
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

    @GET("thumbnails/display/{filename}")
    fun getStoriesThumbnail(
        @Path("filename") filename: String?
    )

}