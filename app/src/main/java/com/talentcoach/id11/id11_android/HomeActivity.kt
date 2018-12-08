package com.talentcoach.id11.id11_android

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.talentcoach.id11.id11_android.communicatie.WerkaanbiedingNavigationFragment
import com.talentcoach.id11.id11_android.competenties.CompetentiesNavigationFragment
import com.talentcoach.id11.id11_android.joborganisatie.JobNavigationFragment
import com.talentcoach.id11.id11_android.profiel.ProfielFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    val werkspreukFragment = WerkspreukFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // madeby Jonas
        // dit is tijdelijk
        jobBtn.setOnClickListener {
            val intent = Intent(this, JobNavigationFragment::class.java)
            startActivity(intent)
        }

        communicatieBtn.setOnClickListener {
            val intent = Intent(this, WerkaanbiedingNavigationFragment::class.java)
            startActivity(intent)
        }

        var talentBtn: Button = findViewById(R.id.talentBtn)

        talentBtn.setOnClickListener {
            val intent = Intent(this, CompetentiesNavigationFragment::class.java)
            startActivity(intent)
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.cardFragment, werkspreukFragment)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.profile_btn -> {
                val intent = Intent(applicationContext, ProfielFragment::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
