package com.talentcoach.id11.id11_android.models

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class LeerlingHoofdCompetentie(
        val id:Int,
        val behaald: Boolean,
        val datumBehaald: Calendar,
        val hoofdCompetentie: HoofdCompetentie,
        val leerlingDeelCompetenties: MutableList<LeerlingDeelCompetentie>
        )