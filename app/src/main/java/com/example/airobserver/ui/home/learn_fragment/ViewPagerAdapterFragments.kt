package com.example.airobserver.ui.home.learn_fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapterFragments(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val fragments:List<Fragment>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList=ArrayList<Fragment>()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}