package com.talentcoach.id11.id11_android.joborganisatie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView

import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.adapters.ExpandableListAdapter
import kotlinx.android.synthetic.main.fragment_info.*
import java.util.ArrayList

private const val HEADER_LIST = "INFO_LIST"
private const val BODY_LIST = "BODY_LIST"

/**
 * Fragment which can display a ExpandableListView
 * @property headerList ArrayList of Strings containing the headers for the list
 * @property bodyList ArrayList of Strings containing the body texts for the list
 */
class InfoFragment : Fragment() {
    var headerList: List<String>? = null
        private set
    var bodyList: List<String>? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            headerList = it.getStringArray(HEADER_LIST)?.toList()
            bodyList = it.getStringArray(BODY_LIST)?.toList()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val expandableListView = inflater.inflate(R.layout.fragment_info, container, false) as ExpandableListView

        expandableListView.setAdapter(ExpandableListAdapter(activity!!.applicationContext, headerList!!, bodyList!!))
        return expandableListView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param headerList ArrayList of Strings containing the headers for the list
         * @param bodyList ArrayList of Strings containing the body texts for the list
         * @return A new instance of fragment InfoFragment.
         */
        @JvmStatic
        fun newInstance(headerList: Array<String>, bodyList: Array<String>) =
                InfoFragment().apply {
                    arguments = Bundle().apply {
                        putStringArray(HEADER_LIST, headerList)
                        putStringArray(BODY_LIST, bodyList)
                    }
                }
    }
}