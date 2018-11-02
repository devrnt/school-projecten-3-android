package com.talentcoach.id11.id11_android

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.repositories.LeerlingRepository
import com.talentcoach.id11.id11_android.repositories.WerkaanbiedingRepository
import com.talentcoach.id11.id11_android.repositories.WerkspreukRepository
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var dataManager = DataManager(
            LeerlingRepository(),
            WerkaanbiedingRepository(),
            WerkspreukRepository())

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
            intent.putExtra("dataManager", dataManager)
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


}
