package com.talentcoach.id11.id11_android.competenties

import android.app.Dialog
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.*
import android.widget.*
import com.talentcoach.id11.id11_android.R
import kotlinx.android.synthetic.main.fragment_competenties_navigation.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
    var yearSelected:Int = 0
    var graadSelected:Int = 0

    val competentieListFragment = CompetentieListFragment()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_competenties_navigation, container, false)

        doAsync {
            uiThread {
                fragmentManager!!.beginTransaction()
                    .add(R.id.fragmentFrameCompetenties, competentieListFragment)
                    .show(competentieListFragment)
                    .commit()

                getAndShowHoofdcompetenties()
            }
        }

        return view
    }


    //Handles action bar item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_tune) {
            ShowDialogWindow(yearSelected, graadSelected)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //Toont Dialog venster waar gebruiker competenties kan sorteren/filteren
    private fun ShowDialogWindow(yearSelected:Int, graadSelected:Int) {

        var sorteerToggle:Switch
        var cancelBtn: Button
        var jaarSpinner:Spinner
        var graadSpinner:Spinner

        myDialog = Dialog(activity)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.dialog_filter)
        sorteerToggle = myDialog.findViewById(R.id.naamToggle) as Switch
        if(namesSortedState){
            sorteerToggle.isChecked = true
        }
        else if (!namesSortedState){
            sorteerToggle.isChecked = false
        }

        //Switch button om te sorteren op naam van competentie
        sorteerToggle.setOnCheckedChangeListener{sorteerToggle,isChecked ->

            if(isChecked){
                competentieListFragment.showSortedListView()
                namesSortedState = true
            }
            else{
                competentieListFragment.showUnsortedListView()
                namesSortedState = false
            }
        }

        //Cancel Button om dialog venster te sluiten
        cancelBtn = myDialog.findViewById(R.id.cancelBtn)
        cancelBtn.setOnClickListener(){
         myDialog.cancel()
        }

        //Spinner = dropdown list om te filteren op jaartal
        jaarSpinner = myDialog.findViewById(R.id.jaarSpinner) as Spinner
        val jaarOptions = arrayOf("Alle","2015","2016","2017","2018")
        jaarSpinner.adapter = ArrayAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line,jaarOptions)
        jaarSpinner.setSelection(yearSelected)

        jaarSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("Nothing selected...")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                this@CompetentiesNavigationFragment.yearSelected = position
            }

        }

        //Spinner = dropdown list om te filteren op graad
        graadSpinner = myDialog.findViewById(R.id.graadSpinner) as Spinner
        val graadOptions = arrayOf("Alle","1e graad","2e graad","3e graad")
        graadSpinner.adapter = ArrayAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line,graadOptions)
        graadSpinner.setSelection(graadSelected)

        graadSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("Nothing selected...")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                this@CompetentiesNavigationFragment.graadSelected = position
            }

        }

        //bevestig Button om filter in te stellen
        var toepassenBtn = myDialog.findViewById<Button>(R.id.toepassenBtn)
        toepassenBtn.setOnClickListener(){
            competentieListFragment.applyFilter(jaarSpinner.selectedItem.toString(), graadSpinner.selectedItem.toString())

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
