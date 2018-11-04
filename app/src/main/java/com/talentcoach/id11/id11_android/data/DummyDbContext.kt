package com.talentcoach.id11.id11_android.data

import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.models.Werkgever

object DummyDbContext {
    lateinit var werkgevers: MutableList<Werkgever>
    lateinit var werkaanbiedingen: MutableList<Werkaanbieding>
    lateinit var leerlingen: MutableList<Leerling>

    init {
        resetDbContext()
    }

    fun resetDbContext(){
        werkgevers = mutableListOf(
                Werkgever("Werkgever 1"),
                Werkgever("Werkgever 2")
        )
        werkaanbiedingen = mutableListOf(
                Werkaanbieding(1, werkgevers[0], "werkaanbieding 1", "teamwork"),
                Werkaanbieding(2, werkgevers[1], "werkaanbieding 2", "kapper"),
                Werkaanbieding(3, werkgevers[1], "werkaanbieding 3", "loodgieter"),
                Werkaanbieding(4, werkgevers[0], "werkaanbieding 4", "teamwork"),
                Werkaanbieding(5, werkgevers[1], "werkaanbieding 5", "zelfstandig kapper"),
                Werkaanbieding(6, werkgevers[1], "werkaanbieding 6", "teamwork loodgieter")
        )

        leerlingen = mutableListOf(
                Leerling(1, "teamwork kapper", mutableListOf(), mutableListOf()),
                Leerling(2, "loodgieter", mutableListOf(), mutableListOf()),
                Leerling(3, "zelfstandig", mutableListOf(), mutableListOf())
        )
    }
}