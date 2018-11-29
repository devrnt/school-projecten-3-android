package com.example.bruno.recyclerviewdemo2

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
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.adapters.CustomViewHolder
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.LeerlingHoofdcompetentie
import kotlinx.android.synthetic.main.comp_tebehalen_item.view.*


class CompTeBehalenAdapter(
        val teBehalenLeerlingHoofdcompetenties: MutableList<LeerlingHoofdcompetentie>,
        var context: Context):RecyclerView.Adapter<CustomViewHolder>(){

    var opengeklapt:Boolean = false
    var leerlingHoofdcompetenties: MutableList<LeerlingHoofdcompetentie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.comp_tebehalen_item,parent,false)
        leerlingHoofdcompetenties.addAll(teBehalenLeerlingHoofdcompetenties)


        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return leerlingHoofdcompetenties.count()
    }

    //deze methode wordt voor elke competentie opgeroepen die gebind wordt aan de viewholder
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val hoofdcompetentie = leerlingHoofdcompetenties.get(position)

        holder.view.compName.text = hoofdcompetentie.hoofdcompetentie.omschrijving
        holder.view.arrowIcon.setImageResource(R.drawable.arrow_right_24dp)
        holder.view.graadTxt.text = hoofdcompetentie.hoofdcompetentie.graad

        if(holder.view.childItems.childCount == 0){
            for(deelcompetentie in hoofdcompetentie.leerlingDeelcompetenties){
                var text: TextView = TextView(context)
                text.text = "${'\u25CF'} ${deelcompetentie.deelcompetentie.omschrijving}" // ${'\u25CF'} zorgt voor Bullet icon
                text.setPadding(0,0,0,40)
                holder.view.childItems.addView(text)
            }

        }

        holder.view.itemCard.setOnClickListener{


            val transDelay:AutoTransition = AutoTransition()
            transDelay.setDuration(250)

            if(!opengeklapt){
                holder.view.arrowIcon.setImageResource(R.drawable.arrow_down_24dp)
                TransitionManager.beginDelayedTransition(holder.view.itemCard,transDelay)
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

}

class CustomViewHolder(val view:View):RecyclerView.ViewHolder(view){

}