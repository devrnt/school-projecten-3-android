package com.talentcoach.id11.id11_android

import java.util.*

class Competentie {
    val name: String
    val behaaldOp: String
    var cal:Calendar=Calendar.getInstance()

    //Parameter jaar toegoevoegd om niet altijd dezelfde datum te hebben
    //Tijdelijk totdat we werkende backend hebben
    constructor(name:String,jaar:Int){
        this.name = name

        cal.set(jaar ,Calendar.MAY,23)
        this.behaaldOp = "${cal.get(Calendar.DATE)}/${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
    }
}