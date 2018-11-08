package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.talentcoach.id11.id11_android.adapters.CompBehaaldAdapter
import com.talentcoach.id11.id11_android.models.Competentie
import com.talentcoach.id11.id11_android.models.SubCompetentie
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class BehaaldFragment: Fragment(){

    lateinit var adapter: CompBehaaldAdapter
    lateinit var lijst: ArrayList<Competentie>
    var copyListJaar = mutableListOf<Competentie>()
    var copyListGraad = mutableListOf<Competentie>()

    lateinit var recycle: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var behaaldTxt: TextView
        var view = inflater.inflate(R.layout.fragment_behaald, container, false)
        behaaldTxt = view.findViewById(R.id.behaaldTxt2)


        lijst = arrayListOf(
                Competentie("Ruimt de werkpost op en maakt hem schoon",
                        arrayListOf(
                                SubCompetentie("Sorteert afval volgens richtlijnen"),
                                SubCompetentie("Reinigt gebruikte materiaal"),
                                SubCompetentie("Houdt zich aan richtlijnen voor hygiëne, veiligheid en ergonomie")), 2018, "1e graad"),
                Competentie("Neemt deel aan organisatie van het kapsalon"
                        ,arrayListOf(
                                SubCompetentie("Werkt samen in team"),
                                SubCompetentie("Voert opdrachten uit volgens werking van kapsalon"),
                                SubCompetentie("Houdt zich aan regels van kapsalon")) ,2018, "2e graad"),
                Competentie("Bereidt de werkpost voor",
                        arrayListOf(
                                SubCompetentie("Zorgt voor orde en netheid van de werkpost"),
                                SubCompetentie("Bereidt het materiaal en producten eigen aan activiteit voor"),
                                SubCompetentie("Controleert en zet materiaal klaar")),2016, "1e graad"),
                Competentie("Legt een afspraak vast met de klant",
                        arrayListOf(
                                SubCompetentie("Staat klant te woord aan telefoon of aan receptie"),
                                SubCompetentie("Schat tijdsduur van gewenste behandeling in"),
                                SubCompetentie("Gebruikt informatie- en communicatietechnologie")),2017, "1e graad"),
                Competentie("Volgt de klant op",
                        arrayListOf(
                                SubCompetentie("Houdt klantenfiche bij en vult gegevens van behandeling in"),
                                SubCompetentie("Verstrekt uitleg over de behandeling en gebruikte producten"),
                                SubCompetentie("Begeleidt klant naar de volgende stap en garandeert opvolging")),2018, "3e graad"),
                Competentie("Past shampoos en specifieke haarverzorging toe",
                        arrayListOf(
                                SubCompetentie("Borstelt en ontwart het haar"),
                                SubCompetentie("Voert elke stap van werkwijze uit volgens verdere behandeling"),
                                SubCompetentie("Brengt de gewenste haar- en huidverzorging aan")),2016, "2e graad"),
                Competentie("Vormt het haar blijvend om (krullen, ontkrullen)",
                        arrayListOf(
                                SubCompetentie("Voert indien nodig een voorverzorging uit"),
                                SubCompetentie("Stelt de juiste omvormingsdiagnose"),
                                SubCompetentie("Voert indien nodig een naverzorging uit")),2018, "1e graad"),
                Competentie("Kleurt het haar (volledig of haarlokken)",
                        arrayListOf(
                                SubCompetentie("Stelt de juiste kleurdiagnose"),
                                SubCompetentie("Berekent en past de juiste formule toe"),
                                SubCompetentie("Emulgeert en spoelt het haar uit")),2015, "3e graad"),
                Competentie("Ontkleurt het haar (volledig of haarlokken)",
                        arrayListOf(
                                SubCompetentie("Stelt de juiste diagnose"),
                                SubCompetentie("Spoelt het haar"),
                                SubCompetentie("Bereidt het mengsel")),2016, "2e graad"))

        recycle = view.findViewById(R.id.recyclerBehaald)
        recycle.layoutManager = LinearLayoutManager(container?.context)
        adapter = CompBehaaldAdapter(lijst,activity!!.applicationContext)
        recycle.adapter = adapter

        behaaldTxt.text = "Behaalde competenties: ${lijst.count()}/18"

        return view
    }


    fun showSortedListView(){

        Collections.sort(lijst, object: Comparator<Competentie> {
            override fun compare(o1: Competentie?, o2: Competentie?): Int {
                return o1!!.name.compareTo(o2!!.name)
            }
        })
        adapter.notifyDataSetChanged()

    }

    fun showUnsortedListView(){
        Collections.shuffle(lijst)
        adapter.notifyDataSetChanged()
    }


    fun filterOnYear(year:String) {
        var sortedList: List<Competentie>

        if (!copyListJaar.isEmpty()) {
            adapter.compList.clear()
            for (item in copyListJaar) {
                adapter.compList.add(adapter.compList.count(), item)
            }
            copyListJaar = mutableListOf()
        }


        sortedList = lijst.filter { c -> c.behaaldOp.contains(year,true)}


        if (!sortedList.isEmpty()) {
            copyListJaar = lijst.toMutableList()

            adapter.compList.clear()
            for (item in sortedList) {
                adapter.compList.add(adapter.compList.count(), item)
            }
        }



        adapter.notifyDataSetChanged()
    }

    fun filterOnGraad(graad:String){
        var sortedList: List<Competentie>


        if(!copyListGraad.isEmpty()){
            adapter.compList.clear()
            for(item in copyListGraad){
                adapter.compList.add(adapter.compList.count(), item)
            }
            copyListGraad = mutableListOf()

        }


        sortedList = lijst.filter { c -> c.graad.equals(graad,true) }


        if(!sortedList.isEmpty()){
            copyListGraad = lijst.toMutableList()
            adapter.compList.clear()
            for(item in sortedList){
                adapter.compList.add(adapter.compList.count(), item)
            }

        }

        adapter.notifyDataSetChanged()
    }

}