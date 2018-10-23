package com.talentcoach.id11.id11_android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CompLijst_Adapter(var cntxt: Context, var resource:Int, var items: List<Competentie>): ArrayAdapter<Competentie>(cntxt,resource,items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(cntxt)
        val view: View = layoutInflater.inflate(resource,null)

        val imageView: ImageView = view.findViewById(R.id.img)
        val textView: TextView = view.findViewById(R.id.beschrijvingTxt)
        val datum: TextView = view.findViewById(R.id.datumTxt)

        val comp: Competentie = items[position]

        imageView.setImageResource(R.drawable.learning)
        textView.text = comp.name
        datum.text = comp.behaaldOp

        return view
    }
}