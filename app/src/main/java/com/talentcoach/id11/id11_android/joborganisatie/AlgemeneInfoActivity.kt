package com.talentcoach.id11.id11_android.joborganisatie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.repositories.AlgemeneInfoRepository
import kotlinx.android.synthetic.main.activity_algemene_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AlgemeneInfoActivity : AppCompatActivity() {
    private val algemeneInfoRepository = AlgemeneInfoRepository()
    private lateinit var algemeneInfoFragment: InfoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algemene_info)
        spinner.visibility = View.VISIBLE


        doAsync {
            val algemeneInfo = algemeneInfoRepository.getAlgemeneInfo()
            val header = algemeneInfo.map { ai -> ai.titel }.toTypedArray()
            val body = algemeneInfo.map { ai -> ai.omschrijving }.toTypedArray()

            uiThread {
                spinner.visibility = View.GONE
                algemeneInfoFragment = InfoFragment.newInstance(header, body)
                supportFragmentManager.beginTransaction()
                        .add(R.id.infoFragmentFrame, algemeneInfoFragment)
                        .commit()
            }
        }
    }



}
