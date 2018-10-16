package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.talentcoach.id11.id11_android.adapters.ExpandableListAdapter
import com.talentcoach.id11.id11_android.models.AlgemeneInfoSupplier
import kotlinx.android.synthetic.main.activity_algemene_info.*

class AlgemeneInfoActivity : AppCompatActivity() {
    val header: MutableList<String> = AlgemeneInfoSupplier.algemeneInfo.map { ai -> ai.title }.toMutableList()
    val body: MutableList<String> = AlgemeneInfoSupplier.algemeneInfo.map { ai -> ai.description }.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_algemene_info)

        expandedListView.setAdapter(ExpandableListAdapter(this, header, body))
    }

}
