package com.talentcoach.id11.id11_android.managers

import com.talentcoach.id11.id11_android.models.*
import com.talentcoach.id11.id11_android.repositories.*
import java.io.Serializable

object DataManager : Serializable {
    var leerlingRepository: IRepository<Leerling> = LeerlingRepository()
    var werkaanbiedingRepository: IRepository<Werkaanbieding> = WerkaanbiedingRepository()
    var specifiekeInfoRepository: IRepository<SpecifiekeInfo> = SpecifiekeInfoRepository()
    var werkgeverRepository: IRepository<Werkgever> = WerkgeverRepository()
    var algemeneInfoRepository: IRepository<AlgemeneInfo> = AlgemeneInfoRepository()

    fun getLeerlingById(id: Int): Leerling {
        return leerlingRepository.getById(id)
    }

    fun getWerkgeverById(id: Int): Werkgever {
        return werkgeverRepository.getById(id)
    }

    /**
     * Gets a corresponding Werkaanbieding for a given Leerling
     * @param leerling Leerling for whom the Werkaanbieding is intended
     * @return A corresponding Werkaanbieding, or null if none is found
     * @throws Exception thrown by the Repository
     */
    fun getWerkaanbiedingForLeerling(leerling: Leerling): Werkaanbieding? {
        if (werkaanbiedingRepository.getAll().isEmpty())
            return null

        return werkaanbiedingRepository.getAll().firstOrNull { wa ->
            !leerling.bewaardeWerkaanbiedingen.any { bw -> bw.id == wa.id } // werkaanbieding mag niet al in bewaarde zitten
                    && !leerling.verwijderdeWerkaanbiedingen.any { vw -> vw.id == wa.id } // werkaanbieding mag niet al in verwijderde zitten
                    && wa.tags.split(" ").intersect(leerling.interesses.split(" ")).any() }
        // minstens 1 tag moet voorkomen in de interesses van de leerling
    }

    /**
     * Gets all the SpecifiekeInfos for a corresponding Werkgever
     * @param werkgever The Werkgever to whom the SpecifiekeInfo is realted
     * @returns A list of SpecifiekeInfo
     */
    fun getSpecifiekeInfoForWerkgever(werkgever: Werkgever): List<SpecifiekeInfo> {
        return specifiekeInfoRepository.getAll().filter {
            specifiekeInfo -> specifiekeInfo.werkgever.naam == werkgever.naam
        }
    }

    fun update(leerling: Leerling) {
        leerlingRepository.update(leerling)
    }
}