package com.talentcoach.id11.id11_android.competenties

import android.app.Dialog
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.*
import android.widget.*
import com.talentcoach.id11.id11_android.R

import kotlinx.android.synthetic.main.fragment_competenties_navigation.*

class CompetentiesNavigationFragment : Fragment() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    lateinit var myDialog:Dialog
    var tagFirstTab:String? = null
    var tagSecondTab:String? = null
    var namesSortedState:Boolean = false
    var yearSelected:Int = 0
    var graadSelected:Int = 0



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_competenties_navigation, container, false)

//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(fragmentManager as FragmentManager)

        // Set up the ViewPager with the sections adapter.
        val container = view.findViewById(R.id.container) as ViewPager
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        val tabs = view.findViewById(R.id.tabs) as TabLayout
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        return view
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_comp__tabbed, menu)
//        return true
//    }

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
                var fragmentTeBehalen = getFragmentTeBehalen() as TeBehalenFragment
                fragmentTeBehalen.showSortedListView()

                var fragmentBehaald = getFragmentBehaald() as BehaaldFragment
                fragmentBehaald.showSortedListView()
                namesSortedState = true
            }
            else{
                var fragmentTeBehalen = getFragmentTeBehalen() as TeBehalenFragment
                fragmentTeBehalen.showUnsortedListView()

                var fragmentBehaald = getFragmentBehaald() as BehaaldFragment
                fragmentBehaald.showUnsortedListView()
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
            var fragmentBehaald = fragmentManager!!.findFragmentByTag(tagSecondTab) as BehaaldFragment
            fragmentBehaald.applyFilter(jaarSpinner.selectedItem.toString(), graadSpinner.selectedItem.toString())

            var fragmentTeBehalen = fragmentManager!!.findFragmentByTag(tagFirstTab) as TeBehalenFragment
            fragmentTeBehalen.filterOnGraad(graadSpinner.selectedItem.toString())

            myDialog.cancel()
        }

        myDialog.show()

    }

    //Met behulp van de fragmentmanager haal ik in deze functies de fragments op
    // en kan ik functies uit de fragemnts oproepen
    private fun getFragmentTeBehalen():Fragment{
        return fragmentManager!!.findFragmentByTag(tagFirstTab) as TeBehalenFragment
    }

    private fun getFragmentBehaald():Fragment{
        return fragmentManager!!.findFragmentByTag(tagSecondTab) as BehaaldFragment
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    //De PagerAdapter gaat de juiste fragment weergeven afhankelijk van welke
    //positie of tab geopend is
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when(position){
                0 -> {
                    return TeBehalenFragment()
                }
                1 -> {
                    return BehaaldFragment()
                }
                else -> return null
            }
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when(position){
                0 -> return "TE BEHALEN"
                1 -> return "BEHAALD"
            }
            return null
        }

        //Bij het instantiëren van elke fragment wordt deze methode aangeroepen.
        //Deze methode heb ik nodig om de tag van de fragment te bepalen.
        //Deze tag zal ik nodig hebben voor de fragmentmanager in bovenstaande methodes
        //getFragmentBehaald() en getFragmentTeBehalen()
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var createdFragment:Fragment = super.instantiateItem(container, position) as Fragment

            when(position){
                0 -> {var firstTag = createdFragment.tag
                    tagFirstTab = firstTag
                }
                1 -> {var secondTag = createdFragment.tag
                    tagSecondTab = secondTag

                }
            }
            return createdFragment
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
