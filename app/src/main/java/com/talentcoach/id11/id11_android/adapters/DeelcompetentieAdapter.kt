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
import java.text.SimpleDateFormat

/**
 * Deel van *adapters*.
 *
 * DeelcompetentiesAdapter is verantwoordelijk voor het omzetten van deelcompetenties objecten in view elementen (dialogs);
 *
 * Op basis van de [LeerlingDeelCompetentie] wordt de bijhorende view [beoordeling_deelcompetentie_card.xml] opgebouwd
 *
 * @property beoordelingen lijst die alle [BeoordelingDeelCompetentie] objecten bevat van een [LeerlingDeelCompetentie]
 * @property context de viewcontext
 * @property dateFormatter formateerd en houdt een datum bij in dd/MM/yyyy formaat
 * @constructor maakt een nieuwe instantie van [DeelcompetentieAdapter]
 */
class DeelcompetentieAdapter(
        var beoordelingen: MutableList<BeoordelingDeelCompetentie>,
        var context: Context):RecyclerView.Adapter<BeoordelingViewHolder>(){

    val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

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
        holder.view.datumBeoordeling.text = dateFormatter.format(beoordeling.datum)
        holder.view.score.text = BeoordelingScore.values().get(beoordeling.score).toString()
    }
}

class BeoordelingViewHolder(val view:View):RecyclerView.ViewHolder(view){

}