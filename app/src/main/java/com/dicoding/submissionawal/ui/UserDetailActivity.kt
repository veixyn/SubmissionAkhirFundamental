package com.dicoding.submissionawal.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submissionawal.R
import com.dicoding.submissionawal.data.database.UserFavorite
import com.dicoding.submissionawal.data.response.DetailUserResponse
import com.dicoding.submissionawal.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private var favorite: UserFavorite? = null
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@UserDetailActivity)

        val dataUser = if (Build.VERSION.SDK_INT >= 33) {
            intent.getStringExtra(key_user)
        } else {
            @Suppress("DEPRECATION")
            intent.getStringExtra(key_user)
        }
        viewModel.findUser(dataUser)

        val sectionsPagerAdapter = SectionsPageAdapter(this)
        sectionsPagerAdapter.username = dataUser
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        viewModel.username.observe(this) { username ->
            setUserDetailData(username)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favorite = UserFavorite()

        viewModel.getFavoriteUserByUsername(dataUser.toString()).observe(this) { userFavorite ->
            val btnFav = binding.btnFavorite
            if (userFavorite == null) {
                btnFav.setBackgroundResource(R.drawable.ic_favorite_border)
                btnFav.setOnClickListener { viewModel.insert(favorite as UserFavorite) }
            } else {
                btnFav.setBackgroundResource(R.drawable.ic_favorite)
                btnFav.setOnClickListener { viewModel.delete(favorite as UserFavorite) }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserDetailViewModel::class.java)
    }

    private fun setUserDetailData(user: DetailUserResponse) {
        binding.tvName.text = user.name
        binding.tvLogin.text = user.login
        Glide.with(binding.root)
            .load(user.avatarUrl)
            .into(binding.imgUser)
        binding.tvFollowers.text = getString(R.string.follower_count, user.followers.toString())
        binding.tvFollowing.text = getString(R.string.following_count, user.following.toString())

        favorite.let { favorite ->
            favorite?.username = user.login.toString()
            favorite?.avatarUrl = user.avatarUrl
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Github Users")
                putExtra(
                    Intent.EXTRA_TEXT, "Check out this GitHub user, ${user.login}. They have " +
                            getString(
                                R.string.follower_count,
                                user.followers.toString()
                            ) + " and " +
                            getString(
                                R.string.following_count,
                                user.following.toString()
                            ) + ".\n${user.html_url}"
                )
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val key_user = "key_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}