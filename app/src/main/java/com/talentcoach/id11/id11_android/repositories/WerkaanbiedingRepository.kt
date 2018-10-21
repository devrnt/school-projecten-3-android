package com.talentcoach.id11.id11_android.repositories

import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.models.Werkgever
import java.net.URL

class WerkaanbiedingRepository {
//    private val werkaanbiedingen: List<Werkaanbieding> = listOf(
//            Werkaanbieding(
//                    Werkgever("Dunder Mifflin"),
//                    "Boekhouding en administratie. Gevoel voor humor vereist.",
//                    "boekhouding administratie teamwork"),
//            Werkaanbieding(
//                    Werkgever("Synalco Medics"),
//                    "Klantenservice. Passie voor bellen.",
//                    "klantenservice support"),
//            Werkaanbieding(
//                    Werkgever("Kapsalon Alberto"),
//                    "Kapper voor kinderen. Ten eerste: Goed kunnen zingen is een pluspunt en ten tweede:...",
//                    "kapper kinderen teamwork")
//
//    )

    fun getWerkaanbiedingVoorLeerling(leerling: Leerling): Werkaanbieding {
        var werkaanbiedingen : List<Werkaanbieding>? = null

        try {
            val result = URL("http://projecten3studserver11.westeurope.cloudapp.azure.com/api/werkaanbiedingen").readText()
            werkaanbiedingen = Klaxon().parseArray(result)!!
        } catch (e: Exception){
            throw e
        }

        return werkaanbiedingen.firstOrNull { wa ->
            wa.tags.split(" ").any { t ->
                leerling.interesses.contains(t)
            }
        }!!
    }
}