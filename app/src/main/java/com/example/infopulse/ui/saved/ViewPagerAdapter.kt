package com.example.infopulse.ui.saved

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.infopulse.ui.saved.savedNews.SavedNewsFragment
import com.example.infopulse.ui.saved.savedSources.SavedSourcesFragment
import javax.inject.Inject

class ViewPagerAdapter @Inject constructor(private val activity: Fragment) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            SavedNewsFragment()
        } else {
            SavedSourcesFragment()
        }
    }
}