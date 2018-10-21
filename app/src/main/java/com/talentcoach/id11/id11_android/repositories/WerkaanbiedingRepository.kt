package com.talentcoach.id11.id11_android.repositories

import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import java.net.URL

class WerkaanbiedingRepository {
    private val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/werkaanbiedingen/"


    fun getWerkaanbiedingVoorLeerling(leerling: Leerling): Werkaanbieding? {
        var werkaanbiedingen : List<Werkaanbieding>? = null

        try {
            val result = URL(url).readText()
            werkaanbiedingen = Klaxon().parseArray(result)!!
        } catch (e: Exception){
            throw e
        }

        return werkaanbiedingen.firstOrNull { wa ->
            !leerling.bewaardeWerkaanbiedingen.any { bw -> bw.id == wa.id } // werkaanbieding mag niet al in bewaarde zitten
                && !leerling.verwijderdeWerkaanbiedingen.any { vw -> vw.id == wa.id } // werkaanbieding mag niet al in verwijderde zitten
                && wa.tags.split(" ").intersect(leerling.interesses.split(" ")).any() }
                    // minstens 1 tag moet voorkomen in de interesses van de leerling

    }
}