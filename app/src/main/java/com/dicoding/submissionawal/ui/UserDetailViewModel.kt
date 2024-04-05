package com.dicoding.submissionawal.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionawal.data.database.UserFavorite
import com.dicoding.submissionawal.data.repository.FavoriteRepository
import com.dicoding.submissionawal.data.response.DetailUserResponse
import com.dicoding.submissionawal.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _username = MutableLiveData<DetailUserResponse>()
    val username: LiveData<DetailUserResponse> = _username

    init {
        findUser(QUERY)
    }

    fun findUser(users: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(users)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _username.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getFavoriteUserByUsername(username: String): LiveData<UserFavorite> =
        mFavoriteRepository.getFavoriteUserByUsername(username)

    fun insert(favorite: UserFavorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: UserFavorite) {
        mFavoriteRepository.delete(favorite)
    }

    companion object {
        private const val TAG = "UserDetailViewModel"
        var QUERY = ""
    }
}