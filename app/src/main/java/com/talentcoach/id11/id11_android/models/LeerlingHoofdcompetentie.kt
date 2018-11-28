package com.talentcoach.id11.id11_android.models

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class LeerlingHoofdcompetentie(
        val id:Int,
        val behaald: Boolean,
        val datumBehaald: Calendar,
        val hoofdcompetentie: Hoofdcompetentie,
        val leerlingDeelcompetenties: MutableList<LeerlingDeelcompetentie>
        )