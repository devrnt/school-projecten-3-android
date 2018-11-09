package com.talentcoach.id11.id11_android.joborganisatie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Werkgever
import com.talentcoach.id11.id11_android.repositories.AlgemeneInfoRepository
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class InfoActivity : AppCompatActivity() {
    private lateinit var algemeneInfoFragment: InfoFragment
    private lateinit var specifiekeInfoFragment: InfoFragment
    private val algemeneInfoRepository = AlgemeneInfoRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        infoProgBar.visibility = View.VISIBLE
        doAsync {
            val algemeneInfo = algemeneInfoRepository.getAlgemeneInfo()
            val algHeader = algemeneInfo.map { ai -> ai.titel }.toTypedArray()
            val algBody = algemeneInfo.map { ai -> ai.omschrijving }.toTypedArray()

            val specifiekeInfo = DataManager.getSpecifiekeInfoForWerkgever(Werkgever("Jan De Nul"))
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
            }
        }

        addListeners()
    }

    private fun addListeners() {
        algInfoBtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                    .hide(specifiekeInfoFragment)
                    .show(algemeneInfoFragment)
                    .commit()
        }

        specInfoBtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                    .hide(algemeneInfoFragment)
                    .show(specifiekeInfoFragment)
                    .commit()
        }
    }
}
