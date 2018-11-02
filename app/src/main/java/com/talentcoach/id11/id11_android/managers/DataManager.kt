package com.talentcoach.id11.id11_android.managers

import com.talentcoach.id11.id11_android.models.IRepository
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import java.io.Serializable

class DataManager(private val leerlingRepository: IRepository<Leerling>, private val werkaanbiedingRepository: IRepository<Werkaanbieding>) : Serializable{

    fun getLeerlingById(id: Int): Leerling {
        return leerlingRepository.getById(id)
    }

    /**
     * Gets a corresponding Werkaanbieding for a given Leerling
     * @param leerling Leerling for whom the Werkaanbieding is intended
     * @return A corresponding Werkaanbieding, or null if none is found
     * @throws Exception thrown by the Repository
     */
    fun getWerkaanbiedingVoorLeerling(leerling: Leerling): Werkaanbieding? {
        return werkaanbiedingRepository.getAll().firstOrNull { wa ->
            !leerling.bewaardeWerkaanbiedingen.any { bw -> bw.id == wa.id } // werkaanbieding mag niet al in bewaarde zitten
                    && !leerling.verwijderdeWerkaanbiedingen.any { vw -> vw.id == wa.id } // werkaanbieding mag niet al in verwijderde zitten
                    && wa.tags.split(" ").intersect(leerling.interesses.split(" ")).any() }
        // minstens 1 tag moet voorkomen in de interesses van de leerling
    }
}