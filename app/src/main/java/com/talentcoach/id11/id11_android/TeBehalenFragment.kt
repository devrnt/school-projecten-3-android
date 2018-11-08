package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bruno.recyclerviewdemo2.CompTeBehalenAdapter
import com.talentcoach.id11.id11_android.models.Competentie
import com.talentcoach.id11.id11_android.models.SubCompetentie
import kotlinx.android.synthetic.main.fragment_tebehalen.*
import java.util.*

class TeBehalenFragment: Fragment(){

    lateinit var adapter: CompTeBehalenAdapter
    lateinit var lijst :ArrayList<Competentie>
    lateinit var recycle: RecyclerView
    var copyListGraad = mutableListOf<Competentie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tebehalen, container, false)

        lijst = arrayListOf(
                Competentie("Scheert en/of knipt baard, bakkerbaard en snor",
                        arrayListOf(
                                SubCompetentie("Voert indien nodig voorverzorging uit"),
                                SubCompetentie("Zeept de huid indien nodig in"),
                                SubCompetentie("Spoelt de huid indien nodig")), 2018, "1e graad"),
                Competentie("Voert een gelegenheidskapsel uit"
                        ,arrayListOf(
                                SubCompetentie("Stemt techniek en materiaal af op gelegenheidskapsel"),
                                SubCompetentie("Realiseert opsteekkaspels of vlechten"),
                                SubCompetentie("Werkt het kaspel af")) ,2018, "3e graad"),
                Competentie("Voert make-up en/of manicure uit",
                        arrayListOf(
                                SubCompetentie("Reinigt de huid in functie van de verdere behandeling"),
                                SubCompetentie("Stemt de techniek en het materiaal af op de opdracht"),
                                SubCompetentie("Past de massagetechniek toe")),2016, "2e graad"),
                Competentie("Vormt het haar tijdelijk om (brushen, föhnen)",
                        arrayListOf(
                                SubCompetentie("Stemt de techniek en materiaal af op de omvorming"),
                                SubCompetentie("Werkt het kapsel af"),
                                SubCompetentie("Voert indien nodig naverzorging uit")),2017, "2e graad"),
                Competentie("Voert gecombineerde snitten uit",
                        arrayListOf(
                                SubCompetentie("Stemt de techniek en materiaal af op de snit"),
                                SubCompetentie("Maakt verdelingen"),
                                SubCompetentie("Analyseert en verbetert het resultaat indien nodig")),2018, "1e graad"),
                Competentie("Voert basissnitten uit",
                        arrayListOf(
                                SubCompetentie("Stemt de techniek en materiaal af op de snit"),
                                SubCompetentie("Maakt verdelingen"),
                                SubCompetentie("Analyseert en verbetert het resultaat indien nodig")),2016, "1e graad"),
                Competentie("Neemt afscheid van de klant",
                        arrayListOf(
                                SubCompetentie("Gaat na of de klant tevreden is"),
                                SubCompetentie("Geeft de klant advies in functie van het volgend bezoek"),
                                SubCompetentie("Geeft de jas aan de klant")),2018, "3e graad"),
                Competentie("Verwelkomt de klant en spoort de verwachtingen op",
                        arrayListOf(
                                SubCompetentie("Ontvangt de klant beleefd"),
                                SubCompetentie("Informeert zich over de verwachtingen van de klant"),
                                SubCompetentie("Neemt de jas van de klant aan en hangt deze weg")),2015, "2e graad"),
                Competentie("Adviseert de klant",
                        arrayListOf(
                                SubCompetentie("Volgt evoluties/trends in het vakgebied op"),
                                SubCompetentie("Raadt de juiste behandeling aan in overleg met de klant"),
                                SubCompetentie("Toont voorbeelden indien gewenst")),2016, "1e graad"))

       recycle = view.findViewById(R.id.recyclerTeBehalen)
       recycle.layoutManager = LinearLayoutManager(container?.context)
       adapter = CompTeBehalenAdapter(lijst,activity!!.applicationContext)
       recycle.adapter = adapter

        return view
    }

    fun showSortedListView(){
        sortListOnNames()
        adapter.notifyDataSetChanged()

    }

    fun showUnsortedListView(){
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