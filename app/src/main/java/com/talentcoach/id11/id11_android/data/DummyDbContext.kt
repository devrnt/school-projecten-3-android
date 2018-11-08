package com.talentcoach.id11.id11_android.data

import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Richting
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.models.Werkgever

object DummyDbContext {
    val werkgevers: MutableList<Werkgever> = mutableListOf()
    val werkaanbiedingen: MutableList<Werkaanbieding> = mutableListOf()
    val leerlingen: MutableList<Leerling> = mutableListOf()

    init {
        resetDbContext()
    }

    fun resetDbContext(){
        werkgevers.clear()
        werkgevers.addAll(mutableListOf(
                Werkgever("Werkgever 1"),
                Werkgever("Werkgever 2")
        ))

        werkaanbiedingen.clear()
        werkaanbiedingen.addAll(mutableListOf(
                Werkaanbieding(1, werkgevers[0], "werkaanbieding 1", "teamwork"),
                Werkaanbieding(2, werkgevers[1], "werkaanbieding 2", "kapper"),
                Werkaanbieding(3, werkgevers[1], "werkaanbieding 3", "loodgieter"),
                Werkaanbieding(4, werkgevers[0], "werkaanbieding 4", "teamwork"),
                Werkaanbieding(5, werkgevers[1], "werkaanbieding 5", "zelfstandig kapper"),
                Werkaanbieding(6, werkgevers[1], "werkaanbieding 6", "teamwork loodgieter")
        ))

        leerlingen.clear()
        leerlingen.addAll(mutableListOf(
                Leerling(1, "teamwork kapper", mutableListOf(), mutableListOf()),
                Leerling(2, "loodgieter", mutableListOf(werkaanbiedingen[2]), mutableListOf()),
                Leerling(3, "zelfstandig", mutableListOf(), mutableListOf())
        ))
    }
}