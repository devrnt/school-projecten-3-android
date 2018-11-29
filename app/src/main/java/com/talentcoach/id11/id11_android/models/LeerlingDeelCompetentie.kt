package com.talentcoach.id11.id11_android.models

import java.util.*

class LeerlingDeelCompetentie(
        val id:Int,
        val deelCompetentie: DeelCompetentie,
        val behaald: Boolean,
        val datumGeslaagd: Date,
        val beoordelingen: MutableList<BeoordelingDeelCompetentie>)