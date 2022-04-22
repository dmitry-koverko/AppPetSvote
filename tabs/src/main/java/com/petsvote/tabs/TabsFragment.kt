package com.petsvote.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.petsvote.rating.RatingFragment
import com.petsvote.tabs.databinding.FragmentTabsBinding
import com.petsvote.ui.maintabs.TopTabLayout

class TabsFragment: Fragment(R.layout.fragment_tabs), ViewPager.OnPageChangeListener,
    TopTabLayout.TopTabLayoutListener {

    private var binding: FragmentTabsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTabsBinding.bind(view)
        setUpTabs()
    }

    private fun setUpTabs() {
        binding?.viewPager?.adapter = ViewPagerAdapter(childFragmentManager)
        binding?.viewPager?.addOnPageChangeListener(this)
        binding?.viewPager?.setCurrentItem(1, true)
        binding?.tabs?.mTopTabLayoutListener = this
    }

    class ViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

        private val COUNT = 3

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> RatingFragment()
                1 -> BlankFragment()
                2 -> BlankFragment()
                else -> BlankFragment()
            }
        }

        override fun getCount(): Int {
            return COUNT
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "Tab " + (position + 1)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if(position == 0 && positionOffset != 0f) binding?.tabs?.scrollRatingToCenter(positionOffset)
        else if(position == 1 && positionOffset != 0f) binding?.tabs?.scrollVoteToLeft(positionOffset)
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun selectTabPosition(position: Int) {
        binding?.viewPager?.setCurrentItem(position, true)
    }


}