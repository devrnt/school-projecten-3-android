package com.talentcoach.id11.id11_android

import android.app.Dialog
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.*
import android.widget.*

import kotlinx.android.synthetic.main.activity_comp__tabbed.*

class Comp_Tabbed : AppCompatActivity() {

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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comp__tabbed)


        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_comp__tabbed, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_tune) {
            ShowDialogWindow(yearSelected)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //Toont Dialog venster waar gebruiker competenties kan sorteren/filteren
    private fun ShowDialogWindow(yearSelected:Int) {

        var sorteerToggle:Switch
        var cancelBtn: Button
        var jaarSpinner:Spinner

        myDialog = Dialog(this)
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
                getFragmentTeBehalen(tagFirstTab,true)
                getFragmentBehaald(tagSecondTab,true)
                namesSortedState = true
            }
            else{
                getFragmentTeBehalen(tagFirstTab,false)
                getFragmentBehaald(tagSecondTab,false)
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
        val options = arrayOf("All","2015","2016","2017","2018")
        jaarSpinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,options)
        jaarSpinner.setSelection(yearSelected)

        jaarSpinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("Nothing selected...")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var fragment1 = supportFragmentManager.findFragmentByTag(tagFirstTab) as TeBehalenFragment
                var fragment2 = supportFragmentManager.findFragmentByTag(tagSecondTab) as BehaaldFragment


                fragment1.filterOnYear(options.get(position))
                fragment2.filterOnYear(options.get(position))
                this@Comp_Tabbed.yearSelected = position

            }

        }

        myDialog.show()

    }

    //Met behulp van de fragmentmanager haal ik in deze functies de fragments op
    // en kan ik functies uit de fragemnts oproepen
    private fun getFragmentTeBehalen(tab:String?, toggleNaam:Boolean){

        var fragment = supportFragmentManager.findFragmentByTag(tab) as TeBehalenFragment

        if(toggleNaam)
            fragment.showSortedListView()
        else if(!toggleNaam)
            fragment.showUnsortedListView()
    }

    private fun getFragmentBehaald(tab:String?, toggleNaam:Boolean){
        var fragment = supportFragmentManager.findFragmentByTag(tab) as BehaaldFragment

        if(toggleNaam)
            fragment.showSortedListView()
        else if(!toggleNaam)
            fragment.showUnsortedListView()
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
}
