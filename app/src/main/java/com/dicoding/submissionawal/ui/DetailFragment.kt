package com.dicoding.submissionawal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionawal.data.response.ItemsItem
import com.dicoding.submissionawal.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var position: Int? = null
        var username: String? = null
        val userFollowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserFollowViewModel::class.java)

        binding.rvFragmentDetail.layoutManager = LinearLayoutManager(requireActivity())

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            username?.let { userFollowViewModel.findFollower(it) }
            userFollowViewModel.follower.observe(viewLifecycleOwner) { follower ->
                setFollower(follower)
            }
            userFollowViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            username?.let { userFollowViewModel.findFollowing(it) }
            userFollowViewModel.following.observe(viewLifecycleOwner) { following ->
                setFollowing(following)
            }
            userFollowViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun setFollowing(items: List<ItemsItem>?) {
        val followAdapter = UserFollowAdapter()
        followAdapter.submitList(items)
        binding.rvFragmentDetail.adapter = followAdapter
    }

    private fun setFollower(items: List<ItemsItem>) {
        val followAdapter = UserFollowAdapter()
        followAdapter.submitList(items)
        binding.rvFragmentDetail.adapter = followAdapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFragment.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }
}