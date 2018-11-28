package com.talentcoach.id11.id11_android.models

class Hoofdcompetentie(
        val id: Long,
        val omschrijving: String,
        val graad: String,
        val deelcompetenties: MutableList<Deelcompetentie>)