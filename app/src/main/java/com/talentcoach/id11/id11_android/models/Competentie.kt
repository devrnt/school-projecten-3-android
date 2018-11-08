package com.talentcoach.id11.id11_android.models

import java.util.*
import kotlin.collections.ArrayList

class Competentie(val name:String) {

    var behaaldOp: String = ""
    var cal:Calendar=Calendar.getInstance()
    var graad: String = ""
    lateinit var subCompetenties: ArrayList<SubCompetentie>

    //Parameter jaar toegoevoegd om niet altijd dezelfde datum te hebben
    //Tijdelijk totdat we werkende backend hebben
    constructor(name:String,subCompetenties: ArrayList<SubCompetentie> ,jaar:Int?, graad: String):this(name)
    {
        if(jaar != null){
            cal.set(jaar ,Calendar.MAY,23)
            this.behaaldOp = "${cal.get(Calendar.DATE)}/${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
        }

        this.graad = graad

        this.subCompetenties = subCompetenties
    }

}