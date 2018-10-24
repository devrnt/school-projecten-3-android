package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import java.util.*

class TeBehalenFragment: Fragment(){

    lateinit var adapter:CompLijstAdapter
    var lijst = mutableListOf<Competentie>()
    var copyList = mutableListOf<Competentie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tebehalen, container, false)
        val listView: ListView

        listView = view.findViewById(R.id.teBehalenLv)

        lijst.add(Competentie("Kan snippets gebruiken in Android Studio",2018))
        lijst.add(Competentie("Kan zijn programma testen adhv zelfgemaakte unit testen",2018))
        lijst.add(Competentie("Kan een backend opzetten",2016))
        lijst.add(Competentie("Kan een shell script schrijven in Linux",2017))
        lijst.add(Competentie("Kan gebruik maken van reguliere expressies",2018))
        lijst.add(Competentie("Kan scrum-wise te werk gaan",2016))
        lijst.add(Competentie("Kan design patterns toepassen in zijn project",2018))
        lijst.add(Competentie("Kan use cases op de juiste manier opstellen",2015))
        lijst.add(Competentie("Kan gebruik maken van Linux",2016))

        adapter = CompLijstAdapter(view.context,R.layout.comp_lijst_item,lijst)
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
}