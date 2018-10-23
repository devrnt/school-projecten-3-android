package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import java.util.*

class TeBehalenFragment: Fragment(){

    lateinit var adapter:CompLijst_Adapter
    var lijst = mutableListOf<Competentie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tebehalen, container, false)
        val listView: ListView

        listView = view.findViewById(R.id.teBehalenLv)

        lijst.add(Competentie("Kan snippets gebruiken in Android Studio"))
        lijst.add(Competentie("Kan zijn programma testen adhv zelfgemaakte unit testen"))
        lijst.add(Competentie("Kan een backend opzetten"))
        lijst.add(Competentie("Kan een shell script schrijven in Linux"))
        lijst.add(Competentie("Kan gebruik maken van reguliere expressies"))
        lijst.add(Competentie("Kan scrum-wise te werk gaan"))
        lijst.add(Competentie("Kan design patterns toepassen in zijn project"))
        lijst.add(Competentie("Kan use cases op de juiste manier opstellen"))
        lijst.add(Competentie("Kan gebruik maken van Linux"))

        adapter = CompLijst_Adapter(view.context,R.layout.comp_lijst_item,lijst)
        listView.adapter = adapter

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
        Collections.sort(lijst, object:Comparator<Competentie>{
            override fun compare(o1: Competentie?, o2: Competentie?): Int {
                return o1!!.name.compareTo(o2!!.name)
            }
        })
    }
}