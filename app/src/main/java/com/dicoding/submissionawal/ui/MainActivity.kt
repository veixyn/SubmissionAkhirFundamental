package com.dicoding.submissionawal.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionawal.R
import com.dicoding.submissionawal.data.SettingPreferences
import com.dicoding.submissionawal.data.dataStore
import com.dicoding.submissionawal.data.response.ItemsItem
import com.dicoding.submissionawal.databinding.ActivityMainBinding
import com.dicoding.submissionawal.ui.MainViewModel.Companion.QUERY

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.username.observe(this) { username ->
            setUserData(username)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    QUERY = searchView.text.toString()
                    mainViewModel.findUser(QUERY)
                    false
                }
            searchBar.inflateMenu(R.menu.menu_search)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.modePage -> {
                        val modeIntent =
                            Intent(this@MainActivity, ThemeModeSwitchActivity::class.java)
                        startActivity(modeIntent)
                        true
                    }

                    else -> false
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            val intentFavorite = Intent(this, FavoriteActivity::class.java)
            startActivity(intentFavorite)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeModeSwitchViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(
            ThemeModeSwitchViewModel::class.java
        )

        themeModeSwitchViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
    }

    private fun setUserData(items: List<ItemsItem>) {
        val adapter = UserAdapter(items)
        adapter.submitList(items)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}