package com.dicoding.submissionawal.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserFavorite (
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = "",
)