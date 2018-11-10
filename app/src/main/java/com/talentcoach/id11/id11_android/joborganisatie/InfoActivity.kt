package com.talentcoach.id11.id11_android.joborganisatie

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Werkgever
import com.talentcoach.id11.id11_android.repositories.AlgemeneInfoRepository
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Shows two fragments which can be toggled with tabs (Button views)
 * @property algemeneInfoFragment InfoFragment with AlgemeneInfo in its ExpandableListView
 * @property algemeneInfoFragment InfoFragment with SpecifiekeInfo in its ExpandableListView
 */
class InfoActivity : AppCompatActivity() {
    lateinit var algemeneInfoFragment: InfoFragment
        private set
    lateinit var specifiekeInfoFragment: InfoFragment // TODO: Show default text when Leerling has no Werkgever yet
        private set
    private val algemeneInfoRepository = AlgemeneInfoRepository()
    private lateinit var werkgever : Werkgever


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        infoProgBar.visibility = View.VISIBLE
        doAsync {
            werkgever = DataManager.getWerkgeverById(1)
            val algemeneInfo = algemeneInfoRepository.getAlgemeneInfo()
            val algHeader = algemeneInfo.map { ai -> ai.titel }.toTypedArray()
            val algBody = algemeneInfo.map { ai -> ai.omschrijving }.toTypedArray()

            val specifiekeInfo = DataManager.getSpecifiekeInfoForWerkgever(werkgever)
            val specHeader = specifiekeInfo.map { si -> si.titel }.toTypedArray()
            val specBody = specifiekeInfo.map { si -> si.omschrijving }.toTypedArray()

            uiThread {
                infoProgBar.visibility = View.GONE
                algemeneInfoFragment = InfoFragment.newInstance(algHeader, algBody)
                specifiekeInfoFragment = InfoFragment.newInstance(specHeader, specBody)

                supportFragmentManager.beginTransaction()
                        .add(R.id.infoFragmentFrame, algemeneInfoFragment)
                        .add(R.id.infoFragmentFrame, specifiekeInfoFragment)
                        .hide(specifiekeInfoFragment)
                        .commit()
                addListeners()
            }
        }

    }

    private fun addListeners() {
        algInfoBtn.setOnClickListener {
            algInfoBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            specInfoBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            supportFragmentManager.beginTransaction()
                    .hide(specifiekeInfoFragment)
                    .show(algemeneInfoFragment)
                    .commit()
        }

        specInfoBtn.setOnClickListener {
            algInfoBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            specInfoBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            supportFragmentManager.beginTransaction()
                    .hide(algemeneInfoFragment)
                    .show(specifiekeInfoFragment)
                    .commit()
        }
    }
}
