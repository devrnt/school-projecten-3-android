package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class BehaaldFragment: Fragment(){

    lateinit var adapter:CompLijstAdapter
    lateinit var listView:ListView
    var lijst = mutableListOf<Competentie>()
    var copyList = mutableListOf<Competentie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var behaaldTxt: TextView

        var view = inflater.inflate(R.layout.fragment_behaald, container, false)

        listView = view.findViewById(R.id.compLv)
        behaaldTxt = view.findViewById(R.id.behaaldTxt2)


        lijst.add(Competentie("Kan html toepassen in projecten",2018))
        lijst.add(Competentie("Kan een website opmaken met CSS",2017))
        lijst.add(Competentie("Kan een backend opzetten",2018))
        lijst.add(Competentie("Kan een dynamische website maken met JavaScript",2018))
        lijst.add(Competentie("Kan een programma schrijven in Java",2017))
        lijst.add(Competentie("Kent de fundamenten van OO Programmeren",2016))
        lijst.add(Competentie("Kan design patterns toepassen in zijn project",2016))
        lijst.add(Competentie("Kan use cases op de juiste manier opstellen",2015))
        lijst.add(Competentie("Kan gebruik maken van Linux",2016))

        adapter= CompLijstAdapter(view.context,R.layout.comp_lijst_item,lijst)
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

    //Hier maak ik gebruik van een kopie van de originele lijst omdat bij het filteren de gefilterde sublist aan de originele lijst
    //toegekend wordt. Om dus aan de originele lijst terug te komen --> de kopie
    //Wss niet de meest efficiënte oplossing..
    public fun filterOnYear(year:String){
        var sortedList:List<Competentie>

        if(!copyList.isEmpty()){
            adapter.clear()
            for(item in copyList){
                adapter.insert(item,adapter.count)
            }
        }

        sortedList=lijst.filter { s -> s.behaaldOp.contains(year,true)}

        if(!sortedList.isEmpty()){
            copyList =  lijst.toMutableList()
            adapter.clear()
            for(item in sortedList){
                adapter.insert(item,adapter.count)
            }
        }

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