package com.talentcoach.id11.id11_android

import android.content.Intent

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class HomeActivity : AppCompatActivity() {



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

        talentBtn.setOnClickListener(){
            val intent = Intent(this, Comp_Tabbed::class.java)
            startActivity(intent)
        }

    }


}
