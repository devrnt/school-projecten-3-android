package com.talentcoach.id11.id11_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingRepository
import com.talentcoach.id11.id11_android.repositories.WerkaanbiedingRepository
import kotlinx.android.synthetic.main.activity_werkaanbieding.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.net.URL

class WerkaanbiedingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_werkaanbieding)

        try {
            doAsync {

                val leerling = LeerlingRepository().getLeerlingById(1L)
                if (leerling.huidigeWerkaanbieding == null){
                    leerling.huidigeWerkaanbieding = WerkaanbiedingRepository().getWerkaanbiedingVoorLeerling(leerling)
                }
                uiThread {
                    progress.visibility = View.GONE
                    val (werkgever, beschrijving) = leerling.huidigeWerkaanbieding!!

                    findViewById<TextView>(R.id.werkgever).text =
                            resources.getString(R.string.wa_werkgever, werkgever.naam)
                    findViewById<TextView>(R.id.beschrijving).text =
                            resources.getString(R.string.wa_beschrijving, beschrijving)
                }
            }


        } catch (e: Exception){
            val toast = Toast.makeText(this, e.message?.substring(0, 20), Toast.LENGTH_LONG)
            toast.show()
        }


    }

}
