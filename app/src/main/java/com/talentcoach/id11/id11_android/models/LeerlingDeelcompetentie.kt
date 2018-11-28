package com.talentcoach.id11.id11_android.models

import java.util.*

class LeerlingDeelcompetentie(
        val id:Int,
        val deelcompetentie: Deelcompetentie,
        val behaald: Boolean,
        val datumGeslaagd: Calendar,
        val beoordelingen: MutableList<BeoordelingDeelcompetentie>)