package com.talentcoach.id11.id11_android

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.talentcoach.id11.id11_android.communicatie.CommunicatieActivity
import com.talentcoach.id11.id11_android.profiel.ProfielActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    val werkspreukFragment = WerkspreukFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // madeby Jonas
        // dit is tijdelijk
        jobBtn.setOnClickListener {
            val intent = Intent(this, AlgemeneInfoActivity::class.java)
            startActivity(intent)
        }

        communicatieBtn.setOnClickListener {
            val intent = Intent(this, CommunicatieActivity::class.java)
            startActivity(intent)
        }

        var talentBtn: Button = findViewById(R.id.talentBtn)

        talentBtn.setOnClickListener {
            val intent = Intent(this, Comp_Tabbed::class.java)
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
                val intent = Intent(applicationContext, ProfielActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
