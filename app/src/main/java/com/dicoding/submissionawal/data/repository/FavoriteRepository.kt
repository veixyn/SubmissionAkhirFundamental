package com.dicoding.submissionawal.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.submissionawal.data.database.FavoriteDao
import com.dicoding.submissionawal.data.database.FavoriteRoomDatabase
import com.dicoding.submissionawal.data.database.UserFavorite
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorites(): LiveData<List<UserFavorite>> = mFavoriteDao.getAllUserFavorites()

    fun getFavoriteUserByUsername(username: String): LiveData<UserFavorite> =
        mFavoriteDao.getFavoriteUserByUsername(username)

    fun insert(favorite: UserFavorite) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: UserFavorite) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }

    fun update(favorite: UserFavorite) {
        executorService.execute { mFavoriteDao.update(favorite) }
    }
}