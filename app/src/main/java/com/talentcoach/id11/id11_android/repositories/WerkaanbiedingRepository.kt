package com.talentcoach.id11.id11_android.repositories

import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.models.Werkgever

class WerkaanbiedingRepository {
    private val werkaanbiedingen: List<Werkaanbieding> = listOf(
            Werkaanbieding(
                    Werkgever("Dunder Mifflin"),
                    "Boekhouding en administratie. Gevoel voor humor vereist.",
                    "boekhouding administratie teamwork"),
            Werkaanbieding(
                    Werkgever("Synalco Medics"),
                    "Klantenservice. Passie voor bellen.",
                    "klantenservice support"),
            Werkaanbieding(
                    Werkgever("Kapsalon Alberto"),
                    "Kapper voor kinderen. Ten eerste: Goed kunnen zingen is een pluspunt en ten tweede:...",
                    "kapper kinderen teamwork")

    )

    fun getAllWerkaanbiedingen(): List<Werkaanbieding> {
        // http GET request
        return werkaanbiedingen
    }

    fun getWerkaanbiedingVoorLeerling(leerling: Leerling): Werkaanbieding? {
        return werkaanbiedingen.firstOrNull { wa ->
            wa.tags.split(" ").any { t ->
                leerling.interesses.contains(t)
            }
        }
    }
}