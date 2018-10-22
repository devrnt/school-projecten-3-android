package com.talentcoach.id11.id11_android

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.talentcoach.id11.id11_android.models.Werkaanbieding


class WerkaanbiedingenListFragment : Fragment() {
    var werkaanbiedingenList: MutableList<Werkaanbieding>? = null
    var reactInterface: WerkaanbiedingFragment.ReactInterface? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val werkaanbiedingenRecView = inflater.inflate(R.layout.fragment_werkaanbiedingen_list, container, false) as RecyclerView
        val adapter = WerkaanbiedingenListAdapter(werkaanbiedingenList?.toList()!!)
        werkaanbiedingenRecView.adapter = adapter
        werkaanbiedingenRecView.layoutManager = LinearLayoutManager(activity)
        return werkaanbiedingenRecView
    }

    class WerkaanbiedingenListAdapter(
            val werkaanbiedingen: List<Werkaanbieding>
    ) : RecyclerView.Adapter<WerkaanbiedingenListAdapter.WerkaanbiedingenViewHolder>() {

        override fun onBindViewHolder(holder: WerkaanbiedingenViewHolder, pos: Int) {
            val werkaanbieding = werkaanbiedingen[pos]
            holder.constraintLayout.findViewById<TextView>(R.id.werkgever).text = werkaanbieding.werkgever.naam
            holder.constraintLayout.findViewById<ImageButton>(R.id.imageButton).setOnClickListener {
                println("Clicked item $pos")
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WerkaanbiedingenViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.werkaanbiedingen_list_item, parent, false) as ConstraintLayout
            return WerkaanbiedingenViewHolder(view)
        }

        override fun getItemCount(): Int {
            return werkaanbiedingen.size
        }

//        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//            val werkaanbieding = getItem(position)
//            val view: View? = convertView
//                    ?: LayoutInflater.from(context).inflate(R.layout.werkaanbiedingen_list_item, parent, false)
//
//            view?.findViewById<TextView>(R.id.werkgever)?.text = werkaanbieding?.werkgever?.naam ?: ""
//            view?.findViewById<ImageButton>(R.id.imageButton)?.setOnClickListener {
//                reactInterface?.noLikeClicked()
//            }
//            return view!!
//        }

        class WerkaanbiedingenViewHolder(val constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout)

    }


}
