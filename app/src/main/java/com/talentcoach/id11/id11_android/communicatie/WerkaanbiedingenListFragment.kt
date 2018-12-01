package com.talentcoach.id11.id11_android.communicatie

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
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Werkaanbieding


/**
 * Fragment that contains a RecyclerView to display Bewaarde Werkaanbiedingen
 * @property werkaanbiedingenList The list of Bewaarde Werkaanbiedingen to be shown
 * @property iClickListener The activity listening to this fragment
 * @property adapter The adapter of the RecyclerView
 * @property werkaanbiedingenRecView The RecyclerView which displays the werkaanbiedingenList
 */
class WerkaanbiedingenListFragment : Fragment() {
    var werkaanbiedingenList: MutableList<Werkaanbieding> = mutableListOf()
    var iClickListener: IClickListener? = null // will be set to WerkaanbiedingNavigationFragment
    var adapter: WerkaanbiedingenListAdapter? = null
    var werkaanbiedingenRecView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflates the RecyclerView with specified layout file
        werkaanbiedingenRecView = inflater.inflate(R.layout.fragment_werkaanbiedingen_list, container, false) as RecyclerView
        adapter = WerkaanbiedingenListAdapter(iClickListener!!, werkaanbiedingenList)

        // sets adapter and layoutmanager
        werkaanbiedingenRecView?.adapter = adapter
        werkaanbiedingenRecView?.layoutManager = LinearLayoutManager(activity) // items get displayed in a vertical list
        return werkaanbiedingenRecView
    }

    /**
     * Adapter for a RecyclerView that wants to show Werkaanbiedingen
     * Shows the name of a Werkgever of a Werkaanbieding and a remove button for each item
     * @property The activity that listens to the adapter
     * @property werkaanbiedingen A list containg the Werkaanbiedingen that need to be shown
     */
    class WerkaanbiedingenListAdapter(
            val iClickListener: IClickListener,
            val werkaanbiedingen: MutableList<Werkaanbieding>
    ) : RecyclerView.Adapter<WerkaanbiedingenListAdapter.WerkaanbiedingenViewHolder>() {

        override fun onBindViewHolder(holder: WerkaanbiedingenViewHolder, pos: Int) { // sets the data for an item in the RecyclerView
            // data consists out of Werkgever.name and a remove Imagebutton
            val werkaanbieding = werkaanbiedingen[pos]
            val textView = holder.constraintLayout.findViewById<TextView>(R.id.werkgever)
            textView.text = werkaanbieding.werkgever.naam
            textView.isClickable = true
            textView.setOnClickListener {
                iClickListener.itemClicked(pos)
            }

            holder.constraintLayout.findViewById<ImageButton>(R.id.removeImgBtn).setOnClickListener {
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

        /**
         * Updates the list contained within the adapter
         * @param newList The changed list
         */
        fun updateList(newList: MutableList<Werkaanbieding>){
            werkaanbiedingen.clear()
            werkaanbiedingen.addAll(newList)
            this.notifyDataSetChanged()
        }

        // Change parameter and parameterType in case ViewGroup holding each item changes
        class WerkaanbiedingenViewHolder(val constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout)

    }


}
