package com.talentcoach.id11.id11_android

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import kotlinx.android.synthetic.main.fragment_werkaanbiedingen_list.*


class WerkaanbiedingenListFragment : Fragment() {
    var werkaanbiedingenList: MutableList<Werkaanbieding>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_werkaanbiedingen_list, container, false)
    }

    override fun onResume() {
        super.onResume()

        if (werkaanbiedingenList != null)
            textView.text = "Got list!"
    }


}
