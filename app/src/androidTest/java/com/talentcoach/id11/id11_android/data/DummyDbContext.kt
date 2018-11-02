package com.talentcoach.id11.id11_android.data

import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.models.Werkgever

object DummyDbContext {
    val werkgevers
            = listOf(
                Werkgever("Werkgever 1"),
                Werkgever("Werkgever 2")
            )
    val werkaanbiedingen
            = listOf(
                Werkaanbieding(1, werkgevers[0], "werkaanbieding 1", "teamwork"),
                Werkaanbieding(2, werkgevers[1], "werkaanbieding 2", "kapper"),
                Werkaanbieding(3, werkgevers[1], "werkaanbieding 3", "loodgieter")
            )

    val leerlingen
            = listOf(
                Leerling(1, "teamwork kapper", mutableListOf(), mutableListOf()),
                Leerling(2, "loodgieter", mutableListOf(), mutableListOf())
            )
}