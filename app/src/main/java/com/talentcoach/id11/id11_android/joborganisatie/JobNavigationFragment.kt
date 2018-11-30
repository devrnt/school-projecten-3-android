package com.talentcoach.id11.id11_android.joborganisatie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Werkgever
import com.talentcoach.id11.id11_android.repositories.AlgemeneInfoRepository
import kotlinx.android.synthetic.main.fragment_job_navigation.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Shows two fragments which can be toggled with tabs (Button views)
 * @property algemeneInfoFragment InfoFragment with AlgemeneInfo in its ExpandableListView
 * @property algemeneInfoFragment InfoFragment with SpecifiekeInfo in its ExpandableListView
 */
class JobNavigationFragment : Fragment() {
    // id should be supplied by the parent activity
    private var WERKGEVER_ID = 1
    lateinit var algemeneInfoFragment: InfoFragment
        private set
    lateinit var specifiekeInfoFragment: InfoFragment
        private set
    private val algemeneInfoRepository = AlgemeneInfoRepository()
    private lateinit var werkgever : Werkgever


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_job_navigation, container, false)

        val infoProgBar = view.findViewById(R.id.infoProgBar) as ProgressBar
        infoProgBar.visibility = View.VISIBLE
        doAsync {
            werkgever = DataManager.getWerkgeverById(WERKGEVER_ID)
            val algemeneInfo = DataManager.algemeneInfoRepository.getAll()
            val algHeader = algemeneInfo.map { ai -> ai.titel }.toTypedArray()
            val algBody = algemeneInfo.map { ai -> ai.omschrijving }.toTypedArray()

            val specifiekeInfo = DataManager.getSpecifiekeInfoForWerkgever(werkgever)
            val specHeader = specifiekeInfo.map { si -> si.titel }.toTypedArray()
            val specBody = specifiekeInfo.map { si -> si.omschrijving }.toTypedArray()

            uiThread {
                infoProgBar.visibility = View.GONE
                algemeneInfoFragment = InfoFragment.newInstance(algHeader, algBody)
                specifiekeInfoFragment = InfoFragment.newInstance(specHeader, specBody)

                fragmentManager!!.beginTransaction()
                        .add(R.id.infoFragmentFrame, algemeneInfoFragment)
                        .add(R.id.infoFragmentFrame, specifiekeInfoFragment)
                        .hide(specifiekeInfoFragment)
                        .commit()
                addListeners()
            }
        }

        return view
    }

    private fun addListeners() {
        algInfoBtn.setOnClickListener {
            algInfoBtn.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorPrimaryDark))
            specInfoBtn.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorPrimary))
            fragmentManager!!.beginTransaction()
                    .hide(specifiekeInfoFragment)
                    .show(algemeneInfoFragment)
                    .commit()
        }

        specInfoBtn.setOnClickListener {
            if (WERKGEVER_ID > -1){ // check if there is an id supplied by parent activity
                algInfoBtn.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorPrimary))
                specInfoBtn.setBackgroundColor(ContextCompat.getColor(activity!!.applicationContext, R.color.colorPrimaryDark))
                fragmentManager!!.beginTransaction()
                        .hide(algemeneInfoFragment)
                        .show(specifiekeInfoFragment)
                        .commit()
            } else {
                Toast.makeText(activity, "Je hebt momenteel geen werkgever om specifieke info over te bekijken", Toast.LENGTH_SHORT).show()
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
        fun newInstance() = JobNavigationFragment()
    }
}
