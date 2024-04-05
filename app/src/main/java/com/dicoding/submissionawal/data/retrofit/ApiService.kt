package com.dicoding.submissionawal.data.retrofit

import com.dicoding.submissionawal.data.response.DetailUserResponse
import com.dicoding.submissionawal.data.response.GitHubResponse
import com.dicoding.submissionawal.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?q=")
    fun getUser(@Query("q") query: String): Call<GitHubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String?): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}