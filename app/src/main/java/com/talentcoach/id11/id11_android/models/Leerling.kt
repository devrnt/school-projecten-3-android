package com.talentcoach.id11.id11_android.models

class Leerling(val id: Long, var interesses: String) {
    var huidigeWerkaanbieding: Werkaanbieding? = null
    val bewaardeWerkaanbieding: MutableList<Werkaanbieding> = mutableListOf()
    val verwijderdeWerkaanbiedingen: MutableList<Werkaanbieding> = mutableListOf()
}