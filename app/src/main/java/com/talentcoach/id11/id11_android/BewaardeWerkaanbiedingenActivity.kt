package com.talentcoach.id11.id11_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.Leerling
import kotlinx.android.synthetic.main.activity_bewaarde_werkaanbiedingen.*

class BewaardeWerkaanbiedingenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bewaarde_werkaanbiedingen)
        val leerling = Klaxon().parse<Leerling>(intent?.extras?.get("leerling") as String)
        textView.text = leerling?.id.toString()
    }
}
