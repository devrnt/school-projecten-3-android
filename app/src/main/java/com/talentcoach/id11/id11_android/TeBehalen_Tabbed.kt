package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class TeBehalen_Tabbed: Fragment(){

    lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_tebehalen, container, false)

        listView = view.findViewById(R.id.teBehalenLv)

        var lijst = mutableListOf<Competentie>()

        lijst.add(Competentie(R.drawable.learning,"Kan Snippets gebruiken in Android Studio"))
        lijst.add(Competentie(R.drawable.learning,"Kan zijn programma testen adhv zelfgemaakte unit testen"))
        lijst.add(Competentie(R.drawable.learning,"Kan een backend opzetten"))
        lijst.add(Competentie(R.drawable.learning,"Kan een shell script schrijven in Linux"))
        lijst.add(Competentie(R.drawable.learning,"Kan gebruik maken van reguliere expressies"))
        lijst.add(Competentie(R.drawable.learning,"Kan scrum-wise te werk gaan"))
        lijst.add(Competentie(R.drawable.learning,"Kan design patterns toepassen in zijn project"))
        lijst.add(Competentie(R.drawable.learning,"Kan use cases op de juiste manier opstellen"))
        lijst.add(Competentie(R.drawable.learning,"Kan gebruik maken van Linux"))

        val adapter:CompLijst_Adapter = CompLijst_Adapter(view.context,R.layout.comp_lijst_item,lijst)
        listView.adapter = adapter

        return view

    }
}