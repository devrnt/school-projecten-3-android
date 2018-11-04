package com.talentcoach.id11.id11_android.managers

import com.talentcoach.id11.id11_android.models.IRepository
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.repositories.LeerlingRepository
import com.talentcoach.id11.id11_android.repositories.WerkaanbiedingRepository
import java.io.Serializable

object DataManager : Serializable {
    var leerlingRepository: IRepository<Leerling> = LeerlingRepository()
    var werkaanbiedingRepository: IRepository<Werkaanbieding> = WerkaanbiedingRepository()

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
        if (werkaanbiedingRepository.getAll().isEmpty())
            return null

        return werkaanbiedingRepository.getAll().firstOrNull { wa ->
            !leerling.bewaardeWerkaanbiedingen.any { bw -> bw.id == wa.id } // werkaanbieding mag niet al in bewaarde zitten
                    && !leerling.verwijderdeWerkaanbiedingen.any { vw -> vw.id == wa.id } // werkaanbieding mag niet al in verwijderde zitten
                    && wa.tags.split(" ").intersect(leerling.interesses.split(" ")).any() }
        // minstens 1 tag moet voorkomen in de interesses van de leerling
    }
}