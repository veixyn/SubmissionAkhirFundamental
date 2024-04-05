package com.dicoding.submissionawal.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionawal.data.database.UserFavorite
import com.dicoding.submissionawal.data.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorites(): LiveData<List<UserFavorite>> = mFavoriteRepository.getAllFavorites()
}