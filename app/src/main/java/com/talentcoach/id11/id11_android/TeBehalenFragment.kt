package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bruno.recyclerviewdemo2.CompTeBehalenAdapter
import com.talentcoach.id11.id11_android.models.Competentie
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Richting
import com.talentcoach.id11.id11_android.models.SubCompetentie
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
    var lijst: MutableList<Competentie> = mutableListOf()
    lateinit var recycle: RecyclerView
    var copyListGraad = mutableListOf<Competentie>()

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
        Collections.shuffle(lijst)
        adapter.notifyDataSetChanged()
    }

    private fun sortListOnNames() {
        Collections.sort(lijst, object : Comparator<Competentie> {
            override fun compare(o1: Competentie?, o2: Competentie?): Int {
                return o1!!.omschrijving.compareTo(o2!!.omschrijving)
            }
        })
    }

    fun filterOnGraad(graad: String) {
        var sortedList: List<Competentie>

        if (!copyListGraad.isEmpty()) {
            adapter.compList.clear()
            for (item in copyListGraad) {
                adapter.compList.add(adapter.compList.count(), item)
            }
            copyListGraad = mutableListOf()

        }

        sortedList = lijst.filter { c -> c.graad.equals(graad, true) }

        if (!sortedList.isEmpty()) {
            copyListGraad = lijst.toMutableList()
            adapter.compList.clear()
            for (item in sortedList) {
                adapter.compList.add(adapter.compList.count(), item)
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

                    //Ophalen van Hoofdcompetenties van de richitng
                    val retrofitRichting = Retrofit
                            .Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(url)
                            .build()
                    val richtingRepository = retrofitRichting.create(RichtingRepository::class.java)
                    val callRichting = richtingRepository.getById(richtingId)

                    callRichting.enqueue(object : Callback<Richting> {
                        override fun onFailure(call: Call<Richting>, t: Throwable) {
                            println("WERKT NIET")
                        }

                        override fun onResponse(call: Call<Richting>, response: Response<Richting>) {
                            if(response.isSuccessful){
                                var richting = response.body()

                                lijst = richting!!.competenties

                                //Progressbar doen verdwijnen en lijst weergeven
                                recycle = view!!.findViewById(R.id.recyclerTeBehalen)
                                recycle.visibility = View.VISIBLE
                                teBehalenProgress.visibility = View.GONE

                                adapter = CompTeBehalenAdapter(lijst, activity!!.applicationContext)
                                recycle.adapter = adapter
                            }
                        }

                    })
                } else {
                    Toast.makeText(context, "Hier ging iets fout", Toast.LENGTH_LONG).show()
                }
            }
        })

    }
}