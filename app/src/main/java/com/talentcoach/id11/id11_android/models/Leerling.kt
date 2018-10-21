package com.talentcoach.id11.id11_android.models

import java.io.Serializable

class Leerling(val id: Long, var interesses: String) : Serializable {
    var huidigeWerkaanbieding: Werkaanbieding? = null
    val bewaardeWerkaanbiedingen: MutableList<Werkaanbieding> = mutableListOf()
    val verwijderdeWerkaanbiedingen: MutableList<Werkaanbieding> = mutableListOf()
}