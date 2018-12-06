package com.talentcoach.id11.id11_android.competenties


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.adapters.CompetentiesAdapter
import com.talentcoach.id11.id11_android.models.LeerlingHoofdCompetentie
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import com.talentcoach.id11.id11_android.repositories.LeerlingRepositoryRetrofit
import kotlinx.android.synthetic.main.fragment_competentie_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CompetentieListFragment : Fragment() {

    lateinit var competentiesAdapter: CompetentiesAdapter
    lateinit var recycle: RecyclerView

    var hoofdcompetentieLijst: MutableList<LeerlingHoofdCompetentie> = mutableListOf()
    var behaaldeHoofdCompetentieLijst: MutableList<LeerlingHoofdCompetentie> = mutableListOf()
    var teBehalenHoofdcompetentieLijst: MutableList<LeerlingHoofdCompetentie> = mutableListOf()
    var copyList = mutableListOf<LeerlingHoofdCompetentie>()
    var copyListSwitch = mutableListOf<LeerlingHoofdCompetentie>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_competentie_list, container, false)

        getAndShowCompetentiesLeerling()
        //recycle = view.findViewById(R.id.recyclerHoofdcompetentie) as RecyclerView
        //recycle.layoutManager = LinearLayoutManager(container?.context)


        return view
    }


    fun showSortedListView(){

        copyListSwitch = hoofdcompetentieLijst.toMutableList()
        var copyList = hoofdcompetentieLijst.toMutableList()

        Collections.sort(copyList, object : java.util.Comparator<LeerlingHoofdCompetentie> {
            override fun compare(o1: LeerlingHoofdCompetentie?, o2: LeerlingHoofdCompetentie?): Int {
                return o1!!.hoofdCompetentie.omschrijving.compareTo(o2!!.hoofdCompetentie.omschrijving)
            }
        })
        competentiesAdapter.leerlingHoofdcompetenties.clear()
        for(item in copyList){
            competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.count(), item)
        }

        competentiesAdapter.notifyDataSetChanged()

    }

    fun showUnsortedListView(){
        competentiesAdapter.leerlingHoofdcompetenties.clear()
        for(item in copyListSwitch){
            competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.count(), item)
        }

        competentiesAdapter.notifyDataSetChanged()
    }


    fun applyFilter(year:String, graad:String){

        var sortedList: List<LeerlingHoofdCompetentie> = listOf()

        if (copyList.isNotEmpty()) {
            competentiesAdapter.leerlingHoofdcompetenties.clear()
            for (item in copyList) {
                competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.count(), item)
            }
            copyList = mutableListOf()
        }

        sortedList = hoofdcompetentieLijst.filter { c -> c.datumBehaald.toString().contains(year,true) && c.hoofdCompetentie.graad.equals(graad,true) }

        if(year.contains("Alle",true) && !graad.contains("Alle",true)){
            sortedList = hoofdcompetentieLijst.filter { c -> c.hoofdCompetentie.graad.equals(graad, true) }
        }
        if(graad.contains("Alle",true) && !year.contains("Alle",true)){
            sortedList = hoofdcompetentieLijst.filter { c -> c.datumBehaald.toString().contains(year,true) }
        }

        if(sortedList.isNotEmpty()){
            copyList = hoofdcompetentieLijst.toMutableList()
            competentiesAdapter.leerlingHoofdcompetenties.clear()
            for(item in sortedList){
                competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.count(), item)
            }
        }


        competentiesAdapter.notifyDataSetChanged()

    }


    private fun getAndShowCompetentiesLeerling() {
        //val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/"

        //Ophalen van de leerling en richtingId
//        Dit is fout!
//        val retrofitLeerling = Retrofit
//                .Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(url)
//                .build()
//
//        val leerlingRepository = retrofitLeerling.create(LeerlingRepositoryRetrofit::class.java)
//        val callLeerling = leerlingRepository.getById(1)

        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        LeerlingAPI.repository.getById(sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()).enqueue(object : Callback<Leerling> {
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
                println("Ophalen van leerling in CompetentieListFragment lukt niet")
            }

            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {

                if (response.isSuccessful) {
                    var leerling = response.body()

                    behaaldeHoofdCompetentieLijst = leerling!!.hoofdCompetenties.filter { hc -> hc.behaald }.toMutableList()
                    if (behaaldeHoofdCompetentieLijst.isEmpty()) {
                        aantalBehaaldeHoofdcompetenties.text = "Er zijn nog geen competenties behaald!"
                    }
                    else {
                        aantalBehaaldeHoofdcompetenties.text = "Behaalde competenties: ${behaaldeHoofdCompetentieLijst.count()}"
                    }
                    teBehalenHoofdcompetentieLijst = leerling!!.hoofdCompetenties.filter { hc -> hc.behaald.not() }.toMutableList()

                    hoofdcompetentieLijst.addAll(0, teBehalenHoofdcompetentieLijst)
                    hoofdcompetentieLijst.addAll(hoofdcompetentieLijst.count(), behaaldeHoofdCompetentieLijst)

                    //Progressbar doen verdwijnen en lijst weergeven
                    recycle = view!!.findViewById(R.id.recyclerHoofdcompetentie)
                    recycle.visibility = View.VISIBLE
                    aantalBehaaldeHoofdcompetenties.visibility = View.VISIBLE
                    behaaldProgress.visibility = View.GONE

                    competentiesAdapter = CompetentiesAdapter(teBehalenHoofdcompetentieLijst, behaaldeHoofdCompetentieLijst, activity!!.applicationContext)
                    recycle.adapter = competentiesAdapter
                    recycle.layoutManager = LinearLayoutManager(activity)
                } else {
                    Toast.makeText(context, "Hier ging iets fout", Toast.LENGTH_LONG).show()
                }

            }

        })
    }


}
