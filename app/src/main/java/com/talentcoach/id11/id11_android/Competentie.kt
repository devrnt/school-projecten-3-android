package com.talentcoach.id11.id11_android

import java.util.*

class Competentie {

    val img: Int
    val name: String
    val datum: String

    constructor(img:Int, name:String){
        this.img = img
        this.name = name
        this.datum = "${Calendar.getInstance().get(Calendar.DATE)}/${Calendar.getInstance().get(Calendar.MONTH)+1}/${Calendar.getInstance().get(Calendar.YEAR)}"
    }
}