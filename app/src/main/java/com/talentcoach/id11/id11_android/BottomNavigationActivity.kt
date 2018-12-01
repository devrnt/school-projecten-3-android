package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.talentcoach.id11.id11_android.communicatie.WerkaanbiedingNavigationFragment
import com.talentcoach.id11.id11_android.competenties.CompetentiesNavigationFragment
import com.talentcoach.id11.id11_android.joborganisatie.JobNavigationFragment
import com.talentcoach.id11.id11_android.profiel.ProfielActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_competenties -> {
                val fragment = CompetentiesNavigationFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_job -> {
                val fragment = JobNavigationFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_aanbiedingen -> {
                val fragment = WerkaanbiedingNavigationFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_coach -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profiel -> {
                val fragment = ProfielActivity.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = CompetentiesNavigationFragment.newInstance()
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_Container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
