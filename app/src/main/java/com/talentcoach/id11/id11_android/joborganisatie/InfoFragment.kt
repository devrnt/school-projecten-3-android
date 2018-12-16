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
 * Deel van *joborganisatie*.
 *
 * Fragment dat een ExpandableListView kan tonen
 * @property headerList ArrayList van Strings die de headers voor de lijst bevat
 * @property bodyList ArrayList van Strings die de body texten voor de lijst bevat
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
         *  Factoy Method die gebruikt wordt om een nieuwe instantie van deze fragment te verkrijgen;
         *  gebruikmakende van de parameters;
         *
         * @param headerList ArrayList van Strings die de headers voor de lijst bevat
         * @param bodyList ArrayList van Strings die de body texten voor de lijst bevat
         * @return een nieuwe instantie van fragment InfoFragment.
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