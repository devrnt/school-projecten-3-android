package com.talentcoach.id11.id11_android.models

import com.google.gson.annotations.SerializedName

// TODO("Need more attributes, like competenties...)
class Richting(val id: Int, val naam: String, @SerializedName("activiteiten")var competenties:MutableList<Competentie>) {

       constructor() : this(-1, "default", mutableListOf())

}