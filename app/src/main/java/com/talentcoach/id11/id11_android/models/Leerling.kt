package com.talentcoach.id11.id11_android.models

import java.io.Serializable

class Leerling(
        val id: Int,
        var richting: Richting,
        val geslacht: Int,
        val geboorteDatum: String,
        var email: String,
        var interesses: String,
        val bewaardeWerkaanbiedingen: MutableList<Werkaanbieding>,
        val verwijderdeWerkaanbiedingen: MutableList<Werkaanbieding>,
        val naam: String,
        val voornaam: String,
        val leerlingHoofdcompetenties: MutableList<LeerlingHoofdcompetentie>) : Serializable