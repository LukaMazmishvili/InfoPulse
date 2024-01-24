package com.example.infopulse.ui.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.infopulse.R
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentSavedBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SavedFragment : BaseFragment<FragmentSavedBinding>(FragmentSavedBinding::inflate) {

    override fun started() {

        setUpViews()

    }

    override fun listeners() {

    }

    private fun setUpViews() {

        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tlTabLayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Saved News"
            } else {
                tab.text = "Favourite Sources"
            }

        }.attach()
    }

}