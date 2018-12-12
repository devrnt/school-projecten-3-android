package com.talentcoach.id11.id11_android.adapters

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.BeoordelingDeelCompetentie
import com.talentcoach.id11.id11_android.models.BeoordelingScore
import kotlinx.android.synthetic.main.beoordeling_deelcompetentie_card.view.*


class DeelcompetentieAdapter(
        var beoordelingen: MutableList<BeoordelingDeelCompetentie>,
        var context: Context):RecyclerView.Adapter<BeoordelingViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeoordelingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.beoordeling_deelcompetentie_card, parent,false)

        return BeoordelingViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return beoordelingen.size
    }

    //deze methode wordt voor elke competentie opgeroepen die gebind wordt aan de viewholder
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: BeoordelingViewHolder, position: Int) {
        val beoordeling: BeoordelingDeelCompetentie = beoordelingen.get(position)

        holder.view.testBeoordelingDeelcompetentie.text = beoordeling.test
        holder.view.datumBeoordeling.text = beoordeling.datum.toString().substring(0, 10)
        holder.view.score.text = BeoordelingScore.values().get(beoordeling.score).toString()
    }
}

class BeoordelingViewHolder(val view:View):RecyclerView.ViewHolder(view){

}