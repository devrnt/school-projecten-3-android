package com.talentcoach.id11.id11_android.competenties


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

/**
 * This class gets the list of [LeerlingHoofdCompetentie] from the [Leerling] and shows them via RecyclerView.
 *
 * @property competentiesAdapter gets the details from each [LeerlingHoofdCompetentie] and shows them in the CardView
 * @property recycle handles the RecycleView in the layout file
 * @property hoofdcompetentieLijst stores the [LeerlingHoofdCompetentie] from a Leerling
 * @property copyList stores the same [hoofdcompetentieLijst] to apply the filter for a sorted list of [LeerlingHoofdCompetentie]
 * @property copyListSwitch stores the same [hoofdcompetentieLijst] for an unsorted list of [LeerlingHoofdCompetentie]
 * @constructor Creates a new CompetentieListFragment
 */
class CompetentieListFragment : Fragment() {

    lateinit var competentiesAdapter: CompetentiesAdapter
    lateinit var recycle: RecyclerView

    var hoofdcompetentieLijst: MutableList<LeerlingHoofdCompetentie> = mutableListOf()
    var copyList = mutableListOf<LeerlingHoofdCompetentie>()
    var copyListSwitch = mutableListOf<LeerlingHoofdCompetentie>()

    /**
     * The layout gets inflated and the recycle is made
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_competentie_list, container, false)

        getAndShowCompetentiesLeerling()
        recycle = view.findViewById(R.id.recyclerHoofdcompetentie) as RecyclerView
        recycle.layoutManager = LinearLayoutManager(container?.context)

        return view
    }


    /**
     * Gets the results of the sortingfilter from [CompetentiesNavigationFragment] and gives the sorted list to the [CompetentiesAdapter] and lets him know things have changed
     */
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

    /**
     * Gets the results of the sortingfilter from [CompetentiesNavigationFragment] and gives the unSorted list to the [CompetentiesAdapter] and lets him know things have changed
     */
    fun showUnsortedListView(){
        competentiesAdapter.leerlingHoofdcompetenties.clear()
        for(item in copyListSwitch){
            competentiesAdapter.leerlingHoofdcompetenties.add(competentiesAdapter.leerlingHoofdcompetenties.size , item)
        }

        competentiesAdapter.notifyDataSetChanged()
    }


    /**
     * Gets the results of the filter from [CompetentiesNavigationFragment] and gives the filtered list to the [CompetentiesAdapter] and lets him know things have changed
     *
     * @param Boolean to see if the user wants to see the behaalde competenties or not
     * @param String to know which graad of hoofdcompetenties the user wants to see
     */
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

    /**
     * The [Leerling] and his list of [LeerlingHoofdCompetentie] get fetched
     *
     * @property sharedPreferences get the id from the [Gebruiker] to fetch the [Leerling]
     */
    private fun getAndShowCompetentiesLeerling() {
        //Ophalen van de leerling en zijn competenties
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        LeerlingAPI.repository.getById(sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()).enqueue(object : Callback<Leerling> {
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
//                Toast.makeText(context, activity!!.getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
                val toast = Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
                val v = toast.getView().findViewById(android.R.id.message) as TextView
                if (v != null) v.gravity = Gravity.CENTER
                toast.show()
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
//                    Toast.makeText(context, activity!!.getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
                    val toast = Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
                    val v = toast.getView().findViewById(android.R.id.message) as TextView
                    if (v != null) v.gravity = Gravity.CENTER
                    toast.show()
                }

            }

        })
    }


}
