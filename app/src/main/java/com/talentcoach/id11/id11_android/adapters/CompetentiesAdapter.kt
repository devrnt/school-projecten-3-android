package com.talentcoach.id11.id11_android.adapters

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
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
import com.talentcoach.id11.id11_android.models.AlgemeneInfo
import com.talentcoach.id11.id11_android.models.LeerlingDeelCompetentie
import com.talentcoach.id11.id11_android.models.LeerlingHoofdCompetentie
import kotlinx.android.synthetic.main.hoofdcompetentie_list_item.view.*
import java.text.SimpleDateFormat
/**
 * Deel van *adapters*.
 *
 * CompetentiesAdapter is verantwoordelijk voor het omzetten van hoofdcompetentie objecten in view elementen;
 * Op basis van de [leerlingHoofdcompetenties] wordt de bijhorende view [hoofdcompetentie_list_item.xml] opgebouwd
 *
 * @property leerlingHoofdcompetenties de lijst van hoofdcompetenties van de ingelogde leerling
 * @property context de viewcontext
 * @property opengeklapt houdt de staat bij van iedere hoofdcompetentie
 * @property dateFormatter formateerd en houdt een datum bij in dd/MM/yyyy formaat
 * @constructor maakt een nieuwe instantie van CompetentieAdapter
 */
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


    /**
     * Geeft het aanntal [leerlingHoofdcompetenties]
     * @return het aantal [leerlingHoofdcompetenties]
     */
    override fun getItemCount(): Int {
        return leerlingHoofdcompetenties.size
    }

    //deze methode wordt voor elke competentie opgeroepen die gebind wordt aan de viewholder
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val hoofdcompetentie: LeerlingHoofdCompetentie = leerlingHoofdcompetenties.get(position)
        holder.view
        holder.view.hoofdcompetentieOmschrijving.text = hoofdcompetentie.hoofdCompetentie.omschrijving
        holder.view.arrowIcon.setImageResource(R.drawable.arrow_right_24dp)
        holder.view.hoopfdcompetentieBehaaldOp.visibility = View.GONE
        if (hoofdcompetentie.behaald) {
            holder.view.hoopfdcompetentieBehaaldOp.text = "${context.getString(R.string.behaald_op)}: ${dateFormatter.format(hoofdcompetentie.datumBehaald)}"
            holder.view.hoopfdcompetentieBehaaldOp.visibility = View.VISIBLE
        }
        holder.view.hoopfdcompetentieGraad.text = hoofdcompetentie.hoofdCompetentie.graad

        if(holder.view.childItems.childCount == 0){
            for(deelcompetentie in hoofdcompetentie.deelCompetenties){
                var text: TextView = TextView(context)
                var icon: Char;
                // ${'\u25CF'} zorgt voor Bullet icon
                // ${'\u2713'} zorgt voor Checkmark icon
                if(deelcompetentie.behaald) icon = '\u2713' else icon ='\u25CF'
                text.text = "${icon}  ${deelcompetentie.deelCompetentie.omschrijving}"
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
                TransitionManager.beginDelayedTransition(holder.view.hoofdcompetentieCard as ViewGroup?,transDelay)
                holder.view.childItems.visibility = View.VISIBLE
                opengeklapt = true
            }
            else{
                holder.view.arrowIcon.setImageResource(R.drawable.arrow_right_24dp)
                holder.view.childItems.visibility = View.GONE
                opengeklapt = false
            }
        }
        setCompetentieImageAndColor(context,holder,hoofdcompetentie);

    }


    /**
     * Hulpmethode voegt het passende icon en kleur toe aan de view van een [LeerlingHoofdCompetentie] op basis van de attributen icon en kleur;
     * @param context de context, wordt gebruikt om de kleur op te halen uit de app res
     * @param holder wordt gebruitk om de kleur en het icon op in te stellen
     * @param hoofdCompetentie wordt gebruikt om de kleur en icon velden uit te lezen
     *
     * @return het aantal [leerlingHoofdcompetenties]
     */
    private fun setCompetentieImageAndColor(context: Context,holder: CustomViewHolder, hoofdCompetentie: LeerlingHoofdCompetentie)
    {
        val icon: Int;
        var kleur: Int = 0;
        when (hoofdCompetentie.hoofdCompetentie.icon) {
            "scissors" -> icon = R.drawable.scissors
            "laptop" -> icon = R.drawable.laptop
            "computer" -> icon = R.drawable.desktop
            "sales" -> icon = R.drawable.shopping_cart
            "wrench" -> icon = R.drawable.wrench
            "tree" -> icon = R.drawable.tree
            "weegschaal" -> icon = R.drawable.balancescale
            "bliksem" -> icon = R.drawable.bolt
            "cogs" -> icon = R.drawable.cogs
            "car" -> icon = R.drawable.car
            "flask" -> icon = R.drawable.flask
            "medkit" -> icon = R.drawable.medkit
            "child" -> icon = R.drawable.child
            "doctor" -> icon = R.drawable.user_md
            "sport" -> icon = R.drawable.pingpong
            "plant" -> icon = R.drawable.leaf
            "food" -> icon = R.drawable.cutlery
            "building" -> icon = R.drawable.building
            "paint" -> icon = R.drawable.paintroller
            "plug" -> icon = R.drawable.plug
            "retail" -> icon = R.drawable.shopping_bag
            else -> {
                icon = 0;
            }
        }
        if (hoofdCompetentie.behaald) {
            when(hoofdCompetentie.hoofdCompetentie.kleur){
                "rood" -> kleur = R.color.competentieRed
                "oranje" -> kleur = R.color.competentieOrange
                "geel" -> kleur = R.color.competentieYellow
                "groen" -> kleur = R.color.colorGreen
                "blauw" -> kleur = R.color.competentieBlue
                "paars" -> kleur = R.color.competentiePurple
                "zwart" -> kleur = R.color.competentieZwart
                else -> {
                    kleur = 0;
                }
            }
        } else {
            kleur = R.color.competentieNietBehaald
        }

        holder.view.imageView.setImageResource(icon);
        kleur = ContextCompat.getColor(context,kleur)
        holder.view.imageView.setBackgroundColor(kleur);
    }

    /**
     * Hulpmethode opent een [Dialog] voor een gekozen [LeerlingDeelCompetentie];
     * De [LeerlingDeelCompetentie.beoordelingen] worden weergegeven;
     * @param view de context waar de teksten op ingesteld worden
     * @param deelcomp de [LeerlingHoofdCompetentie] waaruit de data wordt gelezen
     *
     */
    private fun showDialogWindow(view: View, deelcomp: LeerlingDeelCompetentie) {

        beoordelingsDialog = Dialog(view.context)
        beoordelingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        beoordelingsDialog.setContentView(R.layout.dialog_deelcompetentie_beoordelingen)

        beoordelingsDialog.findViewById<TextView>(R.id.tv_deelcompetentie_omschrijving).text= deelcomp.deelCompetentie.omschrijving

        if (deelcomp.beoordelingen.isEmpty()) {
            beoordelingsDialog.findViewById<CardView>(R.id.geenBeoordelingenCard).visibility = View.VISIBLE
            beoordelingsDialog.findViewById<TextView>(R.id.tv_boolean_beoordelingen).text = context.getString(R.string.geen_beoordelingen)
        } else {
            beoordelingsDialog.findViewById<CardView>(R.id.geenBeoordelingenCard).visibility = View.GONE
            var recycle = beoordelingsDialog.findViewById(R.id.recyclerBeoordelingenDeelcompetentie) as RecyclerView
            recycle.layoutManager = LinearLayoutManager(view.context)
            recycle.adapter = DeelcompetentieAdapter(deelcomp.beoordelingen, view.context)
        }

        beoordelingsDialog.show()
    }
}

class CustomViewHolder(val view:View):RecyclerView.ViewHolder(view){

}