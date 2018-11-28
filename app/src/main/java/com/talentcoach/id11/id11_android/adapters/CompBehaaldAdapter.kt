package com.talentcoach.id11.id11_android.adapters

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bruno.recyclerviewdemo2.CustomViewHolder
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.LeerlingHoofdcompetentie
import kotlinx.android.synthetic.main.comp_behaald_item.view.*

class CompBehaaldAdapter(var compList: MutableList<LeerlingHoofdcompetentie>, var context: Context): RecyclerView.Adapter<CustomViewHolder>() {

    var opengeklapt:Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.comp_behaald_item,parent,false)

        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {

        return compList.count()
    }

    //deze methode wordt voor elke competentie opgeroepen die gebind wordt aan de viewholder
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val hoofdcompetentie = compList.get(position)

        holder.view.compName2.text = hoofdcompetentie.hoofdcompetentie.omschrijving
        holder.view.checkMark.setImageResource(R.drawable.check)
        holder.view.arrowIcon2.setImageResource(R.drawable.arrow_right_24dp)
        holder.view.behaaldOp.text = hoofdcompetentie.datumBehaald.toString()
        holder.view.graadTxt2.text = hoofdcompetentie.hoofdcompetentie.graad

        if(holder.view.childItems2.childCount == 0){
            for(deelcompetentie in hoofdcompetentie.leerlingDeelcompetenties){
                var text: TextView = TextView(context)
                text.text = "${'\u25CF'} ${deelcompetentie.deelcompetentie.omschrijving}" // ${'\u25CF'} zorgt voor Bullet icon
                text.setPadding(0,0,0,40)
                holder.view.childItems2.addView(text)
            }
        }

        holder.view.itemCard2.setOnClickListener{
            val transDelay: AutoTransition = AutoTransition()
            transDelay.setDuration(250)

            if(!opengeklapt){
                holder.view.arrowIcon2.setImageResource(R.drawable.arrow_down_24dp)
                TransitionManager.beginDelayedTransition(holder.view.itemCard2,transDelay)
                holder.view.childItems2.visibility = View.VISIBLE
                opengeklapt = true
            }
            else{
                holder.view.arrowIcon2.setImageResource(R.drawable.arrow_right_24dp)
                holder.view.childItems2.visibility = View.GONE
                opengeklapt = false
            }
        }
    }
}

class CustomViewHolder(val view: View):RecyclerView.ViewHolder(view){

}