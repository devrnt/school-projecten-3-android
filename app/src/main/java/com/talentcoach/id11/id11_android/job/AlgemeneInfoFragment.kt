package com.talentcoach.id11.id11_android.job

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talentcoach.id11.id11_android.R

class AlgemeneInfoFragment : Fragment() {
    lateinit var headerList: List<String>
    lateinit var bodyList: List<String>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_algemene_info, container, false)



        return view
    }
}