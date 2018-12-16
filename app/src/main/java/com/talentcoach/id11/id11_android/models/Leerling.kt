package com.talentcoach.id11.id11_android.models

import java.io.Serializable

class Leerling(
        val id: Int,
        var richting: Richting,
        val geslacht: Int,
        val geboorteDatum: String,
        var email: String,
        var interesses: List<String>,
        val bewaardeWerkaanbiedingen: MutableList<Werkaanbieding>,
        val verwijderdeWerkaanbiedingen: MutableList<Werkaanbieding>,
        val naam: String,
        val voornaam: String,
        val hoofdCompetenties: MutableList<LeerlingHoofdCompetentie>,
        val werkgever: Werkgever?) : Serializable