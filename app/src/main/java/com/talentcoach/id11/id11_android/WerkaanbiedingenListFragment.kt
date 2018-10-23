package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.Werkaanbieding


class WerkaanbiedingenListFragment : Fragment() {
    var werkaanbiedingenList: MutableList<Werkaanbieding>? = null
    var iClickListener: IClickListener? = null // will be set to WerkaanbiedingActivity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (werkaanbiedingenList!!.isEmpty()){ // adds a TextView with default text when there are no bewaardeWerkaanbiedingen
            val textView = TextView(context)
            textView.textSize = 18f
            textView.text = getString(R.string.geen_bew_werkaanb)
            return textView
        }
        // Inflates the RecyclerView with specified layout file and sets adapter and layoutmanager
        val werkaanbiedingenRecView = inflater.inflate(R.layout.fragment_werkaanbiedingen_list, container, false) as RecyclerView
        val adapter = WerkaanbiedingenListAdapter(iClickListener!!, werkaanbiedingenList!!)
        werkaanbiedingenRecView.adapter = adapter
        werkaanbiedingenRecView.layoutManager = LinearLayoutManager(activity)
        return werkaanbiedingenRecView
    }

    class WerkaanbiedingenListAdapter(
            val iClickListener: IClickListener,
            val werkaanbiedingen: MutableList<Werkaanbieding>
    ) : RecyclerView.Adapter<WerkaanbiedingenListAdapter.WerkaanbiedingenViewHolder>() {

        override fun onBindViewHolder(holder: WerkaanbiedingenViewHolder, pos: Int) { // sets the data for an item in the RecyclerView
            // data consists out of Werkgever.name and a remove Imagebutton
            val werkaanbieding = werkaanbiedingen[pos]
            holder.constraintLayout.findViewById<TextView>(R.id.werkgever).text = werkaanbieding.werkgever.naam
            holder.constraintLayout.findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
                // when clicked remove the corresponding item and notify adapter of change
                iClickListener.removeClicked(pos)
                this.notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WerkaanbiedingenViewHolder {
            // gets the ViewHolder, in this case a ConstraintLayout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.werkaanbiedingen_list_item, parent, false) as ConstraintLayout
            return WerkaanbiedingenViewHolder(view)
        }

        override fun getItemCount(): Int {
            return werkaanbiedingen.size
        }

        // Change parameter and parameterType in case ViewGroup holding each item changes
        class WerkaanbiedingenViewHolder(val constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout)

    }


}
