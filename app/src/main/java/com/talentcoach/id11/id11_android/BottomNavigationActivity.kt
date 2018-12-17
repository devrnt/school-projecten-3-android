package com.talentcoach.id11.id11_android

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.talentcoach.id11.id11_android.werkaanbiedingen.WerkaanbiedingTabNavigatieFragment
import com.talentcoach.id11.id11_android.competenties.CompetentiesNavigationFragment
import com.talentcoach.id11.id11_android.job.JobTabNavigatieFragment
import com.talentcoach.id11.id11_android.joborganisatie.JobNavigationFragment
import com.talentcoach.id11.id11_android.profiel.ProfielFragment
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {

    lateinit var closeDialog: Dialog

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_competenties -> {
                val fragment = CompetentiesNavigationFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_job -> {
                val fragment = JobTabNavigatieFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_aanbiedingen -> {
                val fragment = WerkaanbiedingTabNavigatieFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_coach -> {
                val fragment = CoachFragment.newInstance()
                openFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profiel -> {
                val fragment = ProfielFragment.newInstance()
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

    override fun onBackPressed() {
        super.onBackPressed()
        showDialogForClosing()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_Container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showDialogForClosing() {
        closeDialog = Dialog(this)
        closeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        closeDialog.setContentView(R.layout.dialog_close_app)

        closeDialog.findViewById<TextView>(R.id.tv_title_close_dialog).setText(R.string.sluiten_van_app)
        closeDialog.findViewById<Button>(R.id.cancel_button_close_dialog).setText(R.string.cancel)
        closeDialog.findViewById<Button>(R.id.sluit_button_close_dialog).setText(R.string.close)

        closeDialog.findViewById<Button>(R.id.cancel_button_close_dialog).setOnClickListener {
            openFragment(CompetentiesNavigationFragment.newInstance())
            closeDialog.cancel()
        }

        closeDialog.findViewById<Button>(R.id.sluit_button_close_dialog).setOnClickListener {
            finishAffinity()
            closeDialog.cancel()
        }

        closeDialog.show()
    }
}
