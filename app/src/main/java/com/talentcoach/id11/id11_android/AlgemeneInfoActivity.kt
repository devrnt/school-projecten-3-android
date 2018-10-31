package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.talentcoach.id11.id11_android.adapters.ExpandableListAdapter
import com.talentcoach.id11.id11_android.models.AlgemeneInfo
import com.talentcoach.id11.id11_android.repositories.AlgemeneInfoRepository
import kotlinx.android.synthetic.main.activity_algemene_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AlgemeneInfoActivity : AppCompatActivity() {
    private val algemeneInfoRepository = AlgemeneInfoRepository()

    var algemeneInfo: List<AlgemeneInfo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algemene_info)
        spinner.visibility = View.VISIBLE

        doAsync {
            algemeneInfo = algemeneInfoRepository.getAlgemeneInfo()
            val header = algemeneInfo!!.map { ai -> ai.titel }.toMutableList()
            val body = algemeneInfo!!.map { ai -> ai.omschrijving }.toMutableList()

            uiThread {
                spinner.visibility = View.GONE
                createExpandableList(header, body)
            }
        }
    }

    private fun createExpandableList(header: MutableList<String>, body: MutableList<String>) {
        expandedListView.setAdapter(ExpandableListAdapter(this, header, body))
    }

}
