package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView

class Behaald_Tabbed: Fragment(){

    lateinit var listView:ListView
    lateinit var behaaldTxt: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_behaald, container, false)
        var lijst = mutableListOf<Competentie>()

        listView = view.findViewById(R.id.compLv)
        behaaldTxt = view.findViewById(R.id.behaaldTxt2)


        lijst.add(Competentie(R.drawable.learning,"Kan HTML toepassen in projecten"))
        lijst.add(Competentie(R.drawable.learning,"Kan een website opmaken met CSS"))
        lijst.add(Competentie(R.drawable.learning,"Kan een backend opzetten"))
        lijst.add(Competentie(R.drawable.learning,"Kan een dynamische website maken met JavaScript"))
        lijst.add(Competentie(R.drawable.learning,"Kan een programma schrijven in Java"))
        lijst.add(Competentie(R.drawable.learning,"Kent de fundamenten van OO Programmeren"))
        lijst.add(Competentie(R.drawable.learning,"Kan design patterns toepassen in zijn project"))
        lijst.add(Competentie(R.drawable.learning,"Kan use cases op de juiste manier opstellen"))
        lijst.add(Competentie(R.drawable.learning,"Kan gebruik maken van Linux"))

        val adapter: CompLijst_Adapter = CompLijst_Adapter(view.context,R.layout.comp_lijst_item,lijst)
        listView.adapter = adapter

        behaaldTxt.text = "Totaal behaalde competenties: ${lijst.count()}"

        return view
    }
}