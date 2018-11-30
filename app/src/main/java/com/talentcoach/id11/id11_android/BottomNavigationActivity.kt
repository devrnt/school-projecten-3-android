package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.talentcoach.id11.id11_android.competenties.CompetentiesNavigationFragment
import com.talentcoach.id11.id11_android.joborganisatie.JobNavigationFragment
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_competenties -> {
                message.setText(R.string.title_competenties)
                val fragment = CompetentiesNavigationFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_job -> {
                message.setText(R.string.title_job)
                val fragment = JobNavigationFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_aanbiedingen -> {
                message.setText(R.string.title_aanbiedingen)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_coach -> {
                message.setText(R.string.title_coach)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profiel -> {
                message.setText(R.string.title_profiel)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
