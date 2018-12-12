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
        recycle = view.findViewById(R.id.recyclerHoofdcompetentie) as RecyclerView
        recycle.layoutManager = LinearLayoutManager(container?.context)

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
            competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.size , item)
        }

        competentiesAdapter.notifyDataSetChanged()

    }

    fun showUnsortedListView(){
        competentiesAdapter.leerlingHoofdcompetenties.clear()
        for(item in copyListSwitch){
            competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.size , item)
        }

        competentiesAdapter.notifyDataSetChanged()
    }


    fun applyFilter(behaald:Boolean, graad:String){

        var sortedList: List<LeerlingHoofdCompetentie> = listOf()

        if (copyList.isNotEmpty()) {
            competentiesAdapter.leerlingHoofdcompetenties.clear()
            for (item in copyList) {
                competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.size , item)
            }
            copyList = mutableListOf()
        }

        sortedList = hoofdcompetentieLijst.filter { c -> c.behaald == behaald && c.hoofdCompetentie.graad.equals(graad,true) }

        if (graad.contains("Alle", true)) {
            sortedList = hoofdcompetentieLijst.filter { hc -> hc.behaald == behaald }
        }
//        if(behaald && !graad.contains("Alle",true)){
//            sortedList = hoofdcompetentieLijst.filter { c -> c.behaald == behaald}
//        }
//        if(graad.contains("Alle",true) && !behaald){
//            sortedList = hoofdcompetentieLijst.filter { c -> c.behaald }
//        }

        if(sortedList.isNotEmpty()){
            copyList = hoofdcompetentieLijst.toMutableList()
            competentiesAdapter.leerlingHoofdcompetenties = mutableListOf()
            for(item in sortedList){
                competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.size, item)
            }
        }
        else
            competentiesAdapter.leerlingHoofdcompetenties = mutableListOf()


        competentiesAdapter.notifyDataSetChanged()

    }


    private fun getAndShowCompetentiesLeerling() {
        //Ophalen van de leerling en zijn competenties
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        LeerlingAPI.repository.getById(sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()).enqueue(object : Callback<Leerling> {
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
                println("Ophalen van leerling in CompetentieListFragment lukt niet")
            }

            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {

                if (response.isSuccessful) {
                    var leerling = response.body()

                    hoofdcompetentieLijst = leerling!!.hoofdCompetenties

                    //Progressbar doen verdwijnen en lijst weergeven
                    recycle.visibility = View.VISIBLE
                    behaaldProgress.visibility = View.GONE

                    competentiesAdapter = CompetentiesAdapter(hoofdcompetentieLijst, activity!!.applicationContext)
                    recycle.adapter = competentiesAdapter
                } else {
                    Toast.makeText(context, "Hier ging iets fout", Toast.LENGTH_LONG).show()
                }

            }

        })
    }


}
