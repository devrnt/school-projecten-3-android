package com.talentcoach.id11.id11_android.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.LeerlingDeelCompetentie
import com.talentcoach.id11.id11_android.models.LeerlingHoofdCompetentie
import kotlinx.android.synthetic.main.hoofdcompetentie_list_item.view.*
import java.text.SimpleDateFormat

class CompetentiesAdapter(
    var leerlingHoofdcompetenties: MutableList<LeerlingHoofdCompetentie>,
    var context: Context):RecyclerView.Adapter<CustomViewHolder>(){

    lateinit var beoordelingsDialog: Dialog
    var opengeklapt:Boolean = false
    val dateFormatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.hoofdcompetentie_list_item,parent,false)

        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return leerlingHoofdcompetenties.size
    }

    //deze methode wordt voor elke competentie opgeroepen die gebind wordt aan de viewholder
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val hoofdcompetentie: LeerlingHoofdCompetentie = leerlingHoofdcompetenties.get(position)

        holder.view.hoofdcompetentieOmschrijving.text = hoofdcompetentie.hoofdCompetentie.omschrijving
        holder.view.arrowIcon.setImageResource(R.drawable.arrow_right_24dp)
        holder.view.hoopfdcompetentieBehaaldOp.visibility = View.GONE
        if (hoofdcompetentie.behaald) {
            holder.view.hoofdcompetentieCard.setCardBackgroundColor(Color.parseColor("#00C853"))
            holder.view.hoopfdcompetentieBehaaldOp.text = "Behaald op: ${dateFormatter.format(hoofdcompetentie.datumBehaald)}"
            holder.view.hoopfdcompetentieBehaaldOp.visibility = View.VISIBLE
        }
        holder.view.hoopfdcompetentieGraad.text = hoofdcompetentie.hoofdCompetentie.graad

        if(holder.view.childItems.childCount == 0){
            for(deelcompetentie in hoofdcompetentie.deelCompetenties){
                var text: TextView = TextView(context)
                text.text = "${'\u25CF'} ${deelcompetentie.deelCompetentie.omschrijving}" // ${'\u25CF'} zorgt voor Bullet icon
                text.setPadding(0,0,0,40)
                text.setOnClickListener { showDialogWindow(holder.view, deelcompetentie) }
                holder.view.childItems.addView(text)
            }
        }

        holder.view.hoofdcompetentieCard.setOnClickListener{
            val transDelay:AutoTransition = AutoTransition()
            transDelay.setDuration(250)

            if(!opengeklapt){
                holder.view.arrowIcon.setImageResource(R.drawable.arrow_down_24dp)
                TransitionManager.beginDelayedTransition(holder.view.hoofdcompetentieCard,transDelay)
                holder.view.childItems.visibility = View.VISIBLE
                opengeklapt = true
            }
            else{
                holder.view.arrowIcon.setImageResource(R.drawable.arrow_right_24dp)
                holder.view.childItems.visibility = View.GONE
                opengeklapt = false
            }
        }
    }

    private fun showDialogWindow(view: View, deelcomp: LeerlingDeelCompetentie) {

        beoordelingsDialog = Dialog(view.context)
        beoordelingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        beoordelingsDialog.setContentView(R.layout.dialog_deelcompetentie_beoordelingen)

        beoordelingsDialog.findViewById<TextView>(R.id.tv_deelcompetentie_omschrijving).text= deelcomp.deelCompetentie.omschrijving

        if (deelcomp.beoordelingen.isEmpty()) {
            beoordelingsDialog.findViewById<TextView>(R.id.tv_boolean_beoordelingen).text = "heeft geen beoordelingen."
        } else {
            var recycle = beoordelingsDialog.findViewById(R.id.recyclerBeoordelingenDeelcompetentie) as RecyclerView
            recycle.layoutManager = LinearLayoutManager(view.context)
            recycle.adapter = DeelcompetentieAdapter(deelcomp.beoordelingen, view.context)
        }

        beoordelingsDialog.show()
    }
}

class CustomViewHolder(val view:View):RecyclerView.ViewHolder(view){

}