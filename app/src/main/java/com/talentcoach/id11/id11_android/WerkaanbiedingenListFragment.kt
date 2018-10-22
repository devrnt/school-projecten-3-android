package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.talentcoach.id11.id11_android.models.Werkaanbieding


class WerkaanbiedingenListFragment : ListFragment() {
    var werkaanbiedingenList: MutableList<Werkaanbieding>? = null

    override fun onResume() {
        super.onResume()
        val werkgeverNames = werkaanbiedingenList?.map { wa -> wa.werkgever.naam }?.toMutableList()!!
        val adapter = ArrayAdapter(
                layoutInflater.context,
                android.R.layout.simple_list_item_1,
                werkgeverNames)
        listAdapter = adapter

    }


}
