package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import java.util.*

class BehaaldFragment: Fragment(){

    lateinit var adapter:CompLijst_Adapter
    var lijst = mutableListOf<Competentie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var behaaldTxt: TextView
        var listView:ListView
        var view = inflater.inflate(R.layout.fragment_behaald, container, false)

        listView = view.findViewById(R.id.compLv)
        behaaldTxt = view.findViewById(R.id.behaaldTxt2)


        lijst.add(Competentie("Kan html toepassen in projecten"))
        lijst.add(Competentie("Kan een website opmaken met CSS"))
        lijst.add(Competentie("Kan een backend opzetten"))
        lijst.add(Competentie("Kan een dynamische website maken met JavaScript"))
        lijst.add(Competentie("Kan een programma schrijven in Java"))
        lijst.add(Competentie("Kent de fundamenten van OO Programmeren"))
        lijst.add(Competentie("Kan design patterns toepassen in zijn project"))
        lijst.add(Competentie("Kan use cases op de juiste manier opstellen"))
        lijst.add(Competentie("Kan gebruik maken van Linux"))

        adapter= CompLijst_Adapter(view.context,R.layout.comp_lijst_item,lijst)
        listView.adapter = adapter

        behaaldTxt.text = "Totaal behaalde competenties: ${lijst.count()}"

        return view
    }

    public fun showSortedListView(){
        sortListOnNames()
        adapter.notifyDataSetChanged()

    }

    public fun showUnsortedListView(){
        unsortListOnNames()
        adapter.notifyDataSetChanged()
    }

    private fun unsortListOnNames(){
        Collections.shuffle(lijst)
        adapter.notifyDataSetChanged()
    }

    private fun sortListOnNames(){
        Collections.sort(lijst, object: Comparator<Competentie> {
            override fun compare(o1: Competentie?, o2: Competentie?): Int {
                return o1!!.name.compareTo(o2!!.name)
            }
        })
    }
}