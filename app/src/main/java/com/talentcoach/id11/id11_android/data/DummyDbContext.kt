package com.talentcoach.id11.id11_android.data

import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Richting
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.models.Werkgever

object DummyDbContext {
    lateinit var werkgevers: MutableList<Werkgever>
    lateinit var werkaanbiedingen: MutableList<Werkaanbieding>
    lateinit var leerlingen: MutableList<Leerling>

    init {
        resetDbContext()
    }

    fun resetDbContext() {
        werkgevers = mutableListOf(
                Werkgever("Werkgever 1"),
                Werkgever("Werkgever 2")
        )
        werkaanbiedingen = mutableListOf(
                Werkaanbieding(1, werkgevers[0], "werkaanbieding 1", "teamwork"),
                Werkaanbieding(2, werkgevers[1], "werkaanbieding 2", "kapper"),
                Werkaanbieding(3, werkgevers[1], "werkaanbieding 3", "loodgieter")
        )

        leerlingen = mutableListOf(
                Leerling(1, Richting(), 0, "teamwork kapper", mutableListOf(), mutableListOf()),
                Leerling(2, Richting(), 0, "loodgieter", mutableListOf(), mutableListOf()),
                Leerling(3, Richting(), 0, "zelfstandig", mutableListOf(), mutableListOf())
        )
    }
}