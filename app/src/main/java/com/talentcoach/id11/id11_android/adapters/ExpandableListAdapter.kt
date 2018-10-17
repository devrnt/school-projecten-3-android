package com.talentcoach.id11.id11_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.talentcoach.id11.id11_android.R
import kotlinx.android.synthetic.main.list_child.view.*


class ExpandableListAdapter(
        var context: Context,
        var header: MutableList<String>,
        var body: MutableList<String>
) : BaseExpandableListAdapter() {
    override fun getGroup(p0: Int): String {
        return header[p0]
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var convertView = p2
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_group, null)
        }

        val title = convertView!!.findViewById<TextView>(R.id.list_title)
        title.text = getGroup(p0)
        return convertView
    }

    override fun getChildrenCount(p0: Int): Int {
        // every list item body has 1 text
        return 1
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return body[p0]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var convertView = p3
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.list_child, null)
        }

        val title = convertView!!.list_child
        title.text = getChild(p0, p1) as String
        return convertView
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun getGroupCount(): Int {
        return header.size
    }


}