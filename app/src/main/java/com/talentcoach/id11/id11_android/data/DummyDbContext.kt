package com.talentcoach.id11.id11_android.data

import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Richting
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.models.Werkgever
import java.util.*

object DummyDbContext {
    val werkgevers: MutableList<Werkgever> = mutableListOf()
    val werkaanbiedingen: MutableList<Werkaanbieding> = mutableListOf()
    val leerlingen: MutableList<Leerling> = mutableListOf()

    init {
        resetDbContext()
    }

    fun resetDbContext() {
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
                Leerling(1, Richting(1, "", mutableListOf()), 0, "1993-07-05T00:00:00", "leerling@school.be", "teamwork kapper",
                        mutableListOf(), mutableListOf(), "Stroobants", "Bruno", mutableListOf()),
                Leerling(2, Richting(1, "", mutableListOf()), 0, "1993-07-05T00:00:00", "leerling@school.be", "loodgieter",
                        mutableListOf(werkaanbiedingen[2]), mutableListOf(), "Stroobants", "Bruno", mutableListOf()),
                Leerling(3, Richting(1, "", mutableListOf()), 0, "1993-07-05T00:00:00", "leerling@school.be", "informatica",
                        mutableListOf(), mutableListOf(), "Stroobants", "bruno", mutableListOf())
        ))
    }
}