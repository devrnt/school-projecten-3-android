package com.talentcoach.id11.id11_android

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var talentBtn: Button = findViewById(R.id.talentBtn)

        talentBtn.setOnClickListener(){
            val intent = Intent(this, Comp_Tabbed::class.java)
            startActivity(intent)
        }

    }


}
