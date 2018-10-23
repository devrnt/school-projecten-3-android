package com.talentcoach.id11.id11_android

import java.util.*

class Competentie {
    val name: String
    val behaaldOp: String

    constructor(name:String){
        this.name = name
        this.behaaldOp = "${Calendar.getInstance().get(Calendar.DATE)}/${Calendar.getInstance().get(Calendar.MONTH)+1}/${Calendar.getInstance().get(Calendar.YEAR)}"
    }
}