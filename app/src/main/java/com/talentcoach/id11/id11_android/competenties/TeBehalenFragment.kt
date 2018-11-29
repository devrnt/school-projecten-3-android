package com.talentcoach.id11.id11_android.competenties

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bruno.recyclerviewdemo2.CompTeBehalenAdapter
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.LeerlingHoofdcompetentie
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Richting
import com.talentcoach.id11.id11_android.repositories.LeerlingRepositoryRetrofit
import com.talentcoach.id11.id11_android.repositories.RichtingRepository
import kotlinx.android.synthetic.main.fragment_tebehalen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class TeBehalenFragment: Fragment() {

    lateinit var adapter: CompTeBehalenAdapter
    var teBehalenLeerlingHoofdcompetenties: MutableList<LeerlingHoofdcompetentie> = mutableListOf()
    lateinit var recycle: RecyclerView
    var copyListGraad = mutableListOf<LeerlingHoofdcompetentie>()
    var copyListSwitch = mutableListOf<LeerlingHoofdcompetentie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tebehalen, container, false)

        getAndShowCompetentiesRichting()
        recycle = view.findViewById(R.id.recyclerTeBehalen)
        recycle.layoutManager = LinearLayoutManager(container?.context)

        return view
    }

    fun showSortedListView() {
        sortListOnNames()
        adapter.notifyDataSetChanged()

    }

    fun showUnsortedListView() {
        unsortListOnNames()
        adapter.notifyDataSetChanged()
    }

    private fun unsortListOnNames() {
        adapter.leerlingHoofdcompetenties.clear()
        for(item in copyListSwitch){
            adapter.leerlingHoofdcompetenties.add(adapter.leerlingHoofdcompetenties.count(), item)
        }
    }

    private fun sortListOnNames() {
        copyListSwitch = teBehalenLeerlingHoofdcompetenties.toMutableList()
        var copyList = teBehalenLeerlingHoofdcompetenties.toMutableList()

        Collections.sort(copyList, object : Comparator<LeerlingHoofdcompetentie> {
            override fun compare(o1: LeerlingHoofdcompetentie?, o2: LeerlingHoofdcompetentie?): Int {
                return o1!!.hoofdcompetentie.omschrijving.compareTo(o2!!.hoofdcompetentie.omschrijving)
            }
        })
        adapter.leerlingHoofdcompetenties.clear()
        for(item in copyList){
            adapter.leerlingHoofdcompetenties.add(adapter.leerlingHoofdcompetenties.count(), item)
        }
    }

    fun filterOnGraad(graad: String) {
        var sortedList: List<LeerlingHoofdcompetentie>

        if (!copyListGraad.isEmpty()) {
            adapter.leerlingHoofdcompetenties.clear()
            for (item in copyListGraad) {
                adapter.leerlingHoofdcompetenties.add(adapter.leerlingHoofdcompetenties.count(), item)
            }
            copyListGraad = mutableListOf()

        }

        sortedList = teBehalenLeerlingHoofdcompetenties.filter { c -> c.hoofdcompetentie.graad.equals(graad, true) }

        if (!sortedList.isEmpty()) {
            copyListGraad = teBehalenLeerlingHoofdcompetenties.toMutableList()
            adapter.leerlingHoofdcompetenties.clear()
            for (item in sortedList) {
                adapter.leerlingHoofdcompetenties.add(adapter.leerlingHoofdcompetenties.count(), item)
            }

        }
        adapter.notifyDataSetChanged()
    }

    /**
     * GET Leerling with his RichtingId
     * Then GET Competenties and SubCompetenties from Richting
     */
    private fun getAndShowCompetentiesRichting() {
        val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/"
        var richtingId: Int?

        //Ophalen van de leerling en richtingId
        val retrofitLeerling = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()

        val leerlingRepository = retrofitLeerling.create(LeerlingRepositoryRetrofit::class.java)
        val callLeerling = leerlingRepository.getById(1)

        callLeerling.enqueue(object : Callback<Leerling> {
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
                println("WERKT NIET")
            }

            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {

                if (response.isSuccessful) {
                    var leerling = response.body()
                    richtingId = leerling?.richting?.id

                    teBehalenLeerlingHoofdcompetenties = leerling!!.hoofdCompetenties.filter { hc -> hc.behaald }.toMutableList()

                    //Progressbar doen verdwijnen en lijst weergeven
                    recycle = view!!.findViewById(R.id.recyclerTeBehalen)
                    recycle.visibility = View.VISIBLE
                    teBehalenProgress.visibility = View.GONE

                    adapter = CompTeBehalenAdapter(teBehalenLeerlingHoofdcompetenties, activity!!.applicationContext)
                    recycle.adapter = adapter
//                    //Ophalen van Hoofdcompetenties van de richitng
//                    val retrofitRichting = Retrofit
//                            .Builder()
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .baseUrl(url)
//                            .build()
//                    val richtingRepository = retrofitRichting.create(RichtingRepository::class.java)
//                    val callRichting = richtingRepository.getById(richtingId)
//
//                    callRichting.enqueue(object : Callback<Richting> {
//                        override fun onFailure(call: Call<Richting>, t: Throwable) {
//                            println("WERKT NIET")
//                        }
//
//                        override fun onResponse(call: Call<Richting>, response: Response<Richting>) {
//                            if(response.isSuccessful){
//                                var richting = response.body()
//
//                                teBehalenLeerlingHoofdcompetenties = richting!!.competenties
//
//                                //Progressbar doen verdwijnen en lijst weergeven
//                                recycle = view!!.findViewById(R.id.recyclerTeBehalen)
//                                recycle.visibility = View.VISIBLE
//                                teBehalenProgress.visibility = View.GONE
//
//                                adapter = CompTeBehalenAdapter(teBehalenLeerlingHoofdcompetenties, activity!!.applicationContext)
//                                recycle.adapter = adapter
//                            }
//                        }
//
//                    })
                } else {
                    Toast.makeText(context, "Hier ging iets fout", Toast.LENGTH_LONG).show()
                }
            }
        })

    }
}