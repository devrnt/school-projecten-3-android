package com.talentcoach.id11.id11_android

import android.content.Intent
<<<<<<< HEAD
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
=======
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
>>>>>>> a1e05b016d73e42c3a1e1cb2a4d75f018cbfb4c3

class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

<<<<<<< HEAD
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
=======
        var talentBtn: Button = findViewById(R.id.talentBtn)

        talentBtn.setOnClickListener(){
            val intent = Intent(this, Comp_Tabbed::class.java)
            startActivity(intent)
        }

>>>>>>> a1e05b016d73e42c3a1e1cb2a4d75f018cbfb4c3
    }


}
