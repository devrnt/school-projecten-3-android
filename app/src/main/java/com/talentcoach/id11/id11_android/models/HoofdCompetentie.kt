package com.talentcoach.id11.id11_android.models

class HoofdCompetentie(
        val id: Long,
        val omschrijving: String,
        val graad: String,
        val deelCompetenties: MutableList<DeelCompetentie>)