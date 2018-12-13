package com.talentcoach.id11.id11_android.competenties

import android.app.Dialog
import android.content.SharedPreferences
import android.support.v4.app.Fragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.*
import android.widget.*
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.LeerlingHoofdCompetentie
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import kotlinx.android.synthetic.main.fragment_competentie_list.*
import kotlinx.android.synthetic.main.fragment_competenties_navigation.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompetentiesNavigationFragment : Fragment() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    lateinit var myDialog:Dialog
    var namesSortedState:Boolean = false
    var behaaldSortedState: Boolean = false
    var graadSelected:Int = 0
    var hoofdcompetentieLijst: MutableList<LeerlingHoofdCompetentie> = mutableListOf()
    var behaaldeHoofdCompetentieLijst: MutableList<LeerlingHoofdCompetentie> = mutableListOf()
    var teBehalenHoofdcompetentieLijst: MutableList<LeerlingHoofdCompetentie> = mutableListOf()
    val competentieListFragment = CompetentieListFragment()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_competenties_navigation, container, false)

        view.findViewById<ImageButton>(R.id.filterbutton).setOnClickListener {
            // when clicked remove the corresponding item and notify adapter of change
            ShowDialogWindow(graadSelected)
        }

        doAsync {
            uiThread {
                fragmentManager!!.beginTransaction()
                    .add(R.id.fragmentFrameCompetenties, competentieListFragment)
                    .show(competentieListFragment)
                    .commit()

                getCompetentiesLeerling()
                getAndShowHoofdcompetenties()

            }
        }

        return view
    }

    //Toont Dialog venster waar gebruiker competenties kan sorteren/filteren
    private fun ShowDialogWindow(graadSelected:Int) {

        var sorteerOpNaamToggle:Switch
        var sorteerOpBehaaldToggle: Switch
        var cancelBtn: Button
        var graadSpinner:Spinner

        myDialog = Dialog(activity)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.dialog_filter)
        sorteerOpNaamToggle = myDialog.findViewById(R.id.naamToggle) as Switch
        if(namesSortedState){
            sorteerOpNaamToggle.isChecked = true
        }
        else if (!namesSortedState){
            sorteerOpNaamToggle.isChecked = false
        }

        //Switch button om te sorteren op naam van competentie
        sorteerOpNaamToggle.setOnCheckedChangeListener{sorteerOpNaamToggle,isChecked ->

            if(isChecked){
                competentieListFragment.showSortedListView()
                namesSortedState = true
            }
            else{
                competentieListFragment.showUnsortedListView()
                namesSortedState = false
            }
        }

        sorteerOpBehaaldToggle = myDialog.findViewById(R.id.behaald_toggle) as Switch
        if (behaaldSortedState) {
            sorteerOpBehaaldToggle.isChecked = true
        }
        else if (!behaaldSortedState) {
            sorteerOpBehaaldToggle.isChecked = false
        }

        sorteerOpBehaaldToggle.setOnCheckedChangeListener { sorteerOpBehaaldToggle, isChecked ->
            if (isChecked) {
                behaaldSortedState = true
            } else {
                behaaldSortedState = false
            }
        }

        //Cancel Button om dialog venster te sluiten
        cancelBtn = myDialog.findViewById(R.id.cancelBtn)
        cancelBtn.setOnClickListener(){
         myDialog.cancel()
        }

        //Spinner = dropdown list om te filteren op graad
        graadSpinner = myDialog.findViewById<Spinner>(R.id.graadSpinner)
        val graadOptions: MutableList<String> = hoofdcompetentieLijst.map { hc -> hc.hoofdCompetentie.graad }.distinct().sorted().toMutableList()
        graadOptions.add(0, "Alle")
        graadSpinner.adapter = ArrayAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line,graadOptions)
        graadSpinner.setSelection(graadSelected)

        graadSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println(activity!!.getString(R.string.niets_geselecteerd))
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                this@CompetentiesNavigationFragment.graadSelected = position
            }

        }

        //bevestig Button om filter in te stellen
        var toepassenBtn = myDialog.findViewById<Button>(R.id.toepassenBtn)
        toepassenBtn.setOnClickListener(){
            competentieListFragment.applyFilter(behaaldSortedState, graadSpinner.selectedItem.toString())

            myDialog.cancel()
        }

        myDialog.show()

    }

    private fun getAndShowHoofdcompetenties() {
        doAsync {
            uiThread {
                fragmentManager!!.beginTransaction()
                        .show(competentieListFragment)
                        .commit()
            }
        }
    }

    private fun getCompetentiesLeerling() {
        //Ophalen van de leerling en zijn competenties
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        LeerlingAPI.repository.getById(sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()).enqueue(object : Callback<Leerling> {
        //LeerlingAPI.repository.getById(2).enqueue(object : Callback<Leerling> {
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
            Toast.makeText(context, activity!!.getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {

                if (response.isSuccessful) {
                    var leerling = response.body()

                    behaaldeHoofdCompetentieLijst = leerling!!.hoofdCompetenties.filter { hc -> hc.behaald == true}.toMutableList()
                    teBehalenHoofdcompetentieLijst = leerling!!.hoofdCompetenties.filter { hc -> hc.behaald.not() }.toMutableList()

                    hoofdcompetentieLijst.addAll(0, teBehalenHoofdcompetentieLijst)
                    hoofdcompetentieLijst.addAll(hoofdcompetentieLijst.size, behaaldeHoofdCompetentieLijst)

                    view!!.findViewById<TextView>(R.id.aantalBehaaldeHoofdcompetenties).text = "${behaaldeHoofdCompetentieLijst.size}/${hoofdcompetentieLijst.size} ${activity!!.getString(R.string.behaald)}"
                } else {
                    Toast.makeText(context, activity!!.getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
                }
            }

        })
    }

                    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Tab1Fragment.
         */
        @JvmStatic
        fun newInstance() = CompetentiesNavigationFragment()
    }
}
