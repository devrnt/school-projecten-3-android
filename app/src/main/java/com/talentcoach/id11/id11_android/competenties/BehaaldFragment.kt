package com.talentcoach.id11.id11_android.competenties

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.adapters.CompBehaaldAdapter
import com.talentcoach.id11.id11_android.models.Competentie
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingRepositoryRetrofit
import kotlinx.android.synthetic.main.fragment_behaald.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.Comparator

class BehaaldFragment: Fragment(){

    lateinit var adapter: CompBehaaldAdapter
    lateinit var recycle: RecyclerView

    var lijst: MutableList<Competentie> = mutableListOf()
    var copyList = mutableListOf<Competentie>()
    var copyListSwitch = mutableListOf<Competentie>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_behaald, container, false)

        getAndShowCompetentiesLeerling()
        recycle = view.findViewById(R.id.recyclerBehaald)
        recycle.layoutManager = LinearLayoutManager(container?.context)


        return view
    }


    fun showSortedListView(){

        copyListSwitch = lijst.toMutableList()
        var copyList = lijst.toMutableList()

        Collections.sort(copyList, object : java.util.Comparator<Competentie> {
            override fun compare(o1: Competentie?, o2: Competentie?): Int {
                return o1!!.omschrijving.compareTo(o2!!.omschrijving)
            }
        })
        adapter.compList.clear()
        for(item in copyList){
            adapter.compList.add(adapter.compList.count(), item)
        }

        adapter.notifyDataSetChanged()

    }

    fun showUnsortedListView(){
        adapter.compList.clear()
        for(item in copyListSwitch){
            adapter.compList.add(adapter.compList.count(), item)
        }

        adapter.notifyDataSetChanged()
    }


    fun applyFilter(year:String, graad:String){

        var sortedList: List<Competentie> = listOf()

        if (copyList.isNotEmpty()) {
            adapter.compList.clear()
            for (item in copyList) {
                adapter.compList.add(adapter.compList.count(), item)
            }
            copyList = mutableListOf()
        }

        sortedList = lijst.filter { c -> c.behaaldOp.contains(year,true) && c.graad.equals(graad,true) }

        if(year.contains("Alle",true) && !graad.contains("Alle",true)){
            sortedList = lijst.filter { c -> c.graad.equals(graad, true) }
        }
        if(graad.contains("Alle",true) && !year.contains("Alle",true)){
            sortedList = lijst.filter { c -> c.behaaldOp.contains(year,true) }
        }

        if(sortedList.isNotEmpty()){
            copyList = lijst.toMutableList()
            adapter.compList.clear()
            for(item in sortedList){
                adapter.compList.add(adapter.compList.count(), item)
            }
        }


        adapter.notifyDataSetChanged()

    }


    private fun getAndShowCompetentiesLeerling(){
        val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/"

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

                if(response.isSuccessful){
                    var leerling = response.body()

                    lijst = leerling!!.competenties
                    behaaldTxt2.text = "Behaalde competenties: ${lijst.count()}"

                    //Progressbar doen verdwijnen en lijst weergeven

                    recycle = view!!.findViewById(R.id.recyclerBehaald)
                    recycle.visibility = View.VISIBLE
                    behaaldTxt2.visibility = View.VISIBLE
                    behaaldProgress.visibility = View.GONE

                    adapter = CompBehaaldAdapter(lijst,activity!!.applicationContext)
                    recycle.adapter = adapter
                }
                else {
                    Toast.makeText(context, "Hier ging iets fout", Toast.LENGTH_LONG).show()
                }

            }

        })
    }

}