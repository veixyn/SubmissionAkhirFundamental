package com.dicoding.submissionawal.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: UserFavorite)

    @Update
    fun update(favorite: UserFavorite)

    @Delete
    fun delete(favorite: UserFavorite)

    @Query("SELECT * from userfavorite ORDER BY username")
    fun getAllUserFavorites(): LiveData<List<UserFavorite>>

    @Query("SELECT * FROM userfavorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<UserFavorite>
}