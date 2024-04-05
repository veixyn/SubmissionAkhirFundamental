package com.dicoding.submissionawal.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DetailUserResponse(
    @field:SerializedName("following_url")
    val followingUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("html_url")
    val html_url: String? = null

) : Parcelable
