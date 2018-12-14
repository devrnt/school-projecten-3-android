package com.talentcoach.id11.id11_android.werkaanbiedingen


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.talentcoach.id11.id11_android.R

/**
 * A simple [Fragment] subclass.
 *
 */
class WerkaanbiedingTabNavigatieFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_werkaanbieding_tab_navigatie, container, false)
        viewPager = view.findViewById(R.id.viewpager)
        tabs = view.findViewById(R.id.tabs)
//        tabs.bringToFront()

        val fragmentAdapter = TabPagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)

        viewPager.currentItem = 1
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Tab1Fragment.
         */
        @JvmStatic
        fun newInstance() = WerkaanbiedingTabNavigatieFragment()
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    // De PagerAdapter gaat de juiste fragment weergeven afhankelijk van welke
    // positie of tab geopend is
    inner class TabPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun  getItem(position: Int): Fragment {
            return when (position) {
                0  -> {
                    InteressesFragment()
                }
                1  -> {
                    AanbiedingenFragment()
                }
                else -> {
                    return BewaardeAanbiedingenFragment()
                }
            }
        }

        override  fun  getCount () :  Int {
            return  3
        }

        override fun getPageTitle (position: Int ): CharSequence {
            return when (position) {
                0 -> "Interesses"
                1 -> "Aanbieding"
                else -> {
                    return  "Goedgekeurd"
                }
            }
        }
    }
}
