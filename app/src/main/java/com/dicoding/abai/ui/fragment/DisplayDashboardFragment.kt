package com.dicoding.abai.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.abai.adapter.StoryAdapter
import com.dicoding.abai.databinding.FragmentDisplayDashboardBinding
import com.dicoding.abai.response.DataItem
import com.dicoding.abai.response.ItemsItem
import com.dicoding.abai.viewmodel.DashboardViewModel


class DisplayDashboardFragment : Fragment() {

    private lateinit var binding: FragmentDisplayDashboardBinding
    private val dashboardViewModel: DashboardViewModel by activityViewModels()
    private val adapter: StoryAdapter by lazy { StoryAdapter() }

    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvFragmentDisplay.layoutManager = layoutManager

        // Set adapter here
        binding.rvFragmentDisplay.adapter = adapter

        dashboardViewModel.isLoading.observe(viewLifecycleOwner) { isLoading: Boolean ->
            showLoading(isLoading)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME, username)
        }

//        dashboardViewModel.user.observe(viewLifecycleOwner, Observer { userList ->
//            setUserData(userList)
//        })

        dashboardViewModel.stories.observe(viewLifecycleOwner, Observer { userList ->
            setStoriesData(userList)
        })
    }

//    private fun setUserData(userList: List<ItemsItem?>?) {
//        if (userList.isNullOrEmpty()) {
//            showEmptyListMessage()
//        } else {
//            adapter.submitList(userList)
//            binding.rvFragmentDisplay.visibility = View.VISIBLE
//            binding.textUsername.visibility = View.GONE
//        }
//    }

    private fun setStoriesData(storiesList: List<DataItem?>?) {
        if (storiesList.isNullOrEmpty()) {
            showEmptyListMessage()
        } else {
            adapter.submitList(storiesList)
            binding.rvFragmentDisplay.visibility = View.VISIBLE
            binding.textUsername.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showEmptyListMessage() {
        binding.rvFragmentDisplay.visibility = View.GONE
        binding.textUsername.visibility = View.VISIBLE
        binding.textUsername.text = if (position == 3) {
            "Anda belum membaca cerita"
        } else {
            ""
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}
