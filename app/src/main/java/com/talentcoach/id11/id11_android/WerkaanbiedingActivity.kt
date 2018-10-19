package com.talentcoach.id11.id11_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.talentcoach.id11.id11_android.repositories.LeerlingRepository
import com.talentcoach.id11.id11_android.repositories.WerkaanbiedingRepository

class WerkaanbiedingActivity : AppCompatActivity() {
    private val leerling = LeerlingRepository().getLeerlingById(1L)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_werkaanbieding)
        if (leerling.huidigeWerkaanbieding == null){
            leerling.huidigeWerkaanbieding = WerkaanbiedingRepository().getWerkaanbiedingVoorLeerling(leerling)
        }

        val (werkgever, beschrijving) = leerling.huidigeWerkaanbieding!!

        findViewById<TextView>(R.id.werkgever).text =
                resources.getString(R.string.wa_werkgever, werkgever.naam)
        findViewById<TextView>(R.id.beschrijving).text =
                resources.getString(R.string.wa_beschrijving, beschrijving)
    }
}
