package com.talentcoach.id11.id11_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.models.Werkgever

class WerkaanbiedingActivity : AppCompatActivity() {
    val leerling = Leerling() // ingelogde leerling

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_werkaanbieding)
        leerling.huidigeWerkaanbieding = Werkaanbieding(// normaal uit db
                Werkgever("Dunder Mifflin"),
                "Boekhouding en adminstratie Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nec laoreet urna, condimentum euismod nisi. Ut eget mauris justo. Nulla imperdiet ultrices arcu eu dapibus. Maecenas vitae placerat tellus, ac placerat est. Quisque a tortor erat. Etiam lectus ipsum, pretium eu metus a, lobortis egestas sapien. Sed ipsum sem, commodo in arcu ac, varius tempor neque. Nunc volutpat metus sed lobortis interdum. Aenean vitae ligula elit. Suspendisse consectetur mauris eu arcu fringilla, sit amet accumsan justo volutpat. Aenean imperdiet dignissim mollis. Integer porttitor enim quis quam bibendumBoekhouding en adminstratie Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis nec laoreet urna, condimentum euismod nisi. Ut eget mauris justo. Nulla imperdiet ultrices arcu eu dapibus. Maecenas vitae placerat tellus, ac placerat est. Quisque a tortor erat. Etiam lectus ipsum, pretium eu metus a, lobortis egestas sapien. Sed ipsum sem, commodo in arcu ac, varius tempor neque. Nunc volutpat metus sed lobortis interdum. Aenean vitae ligula elit. Suspendisse consectetur mauris eu arcu fringilla, sit amet accumsan justo volutpat. Aenean imperdiet dignissim mollis. Integer porttitor enim quis quam bibendum, sit amet euismod arcu luctus. Suspendisse commodo est vel euismod ullamcorper. Curabitur vestibulum.",
                listOf(""))
        val wa = leerling.huidigeWerkaanbieding

        findViewById<TextView>(R.id.werkgever).text =
                resources.getString(R.string.wa_werkgever, wa?.werkgever?.naam)
        findViewById<TextView>(R.id.beschrijving).text =
                resources.getString(R.string.wa_beschrijving, wa?.beschrijving)
    }
}
