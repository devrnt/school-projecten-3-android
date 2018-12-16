package com.talentcoach.id11.id11_android.managers

import com.talentcoach.id11.id11_android.models.*
import com.talentcoach.id11.id11_android.repositories.*
import java.io.Serializable
/**
 * Deel van *managers*.
 *
 * Manager van alle repositories (IRepository)
 *
 * @property leerlingRepository aanspreekpunt voor de api/leerlingen endpoint
 * @property werkaanbiedingRepository aanspreekpunt voor de api/werkaanbiedingen endpoint
 * @property specifiekeInfoRepository depricated aanspreekpunt, niet gebruikt in de app
 * @property werkgeverRepository aanspreekpunt voor de api/werkgevers endpoint
 * @property algemeneInfoRepository aanspreekpunt voor de api/algemene-info endpoint
 */
object DataManager : Serializable {
    var leerlingRepository: IRepository<Leerling> = LeerlingRepository()
    var werkaanbiedingRepository: IRepository<Werkaanbieding> = WerkaanbiedingRepository()
    var specifiekeInfoRepository: IRepository<SpecifiekeInfo> = SpecifiekeInfoRepository()
    var werkgeverRepository: IRepository<Werkgever> = WerkgeverRepository()
    var algemeneInfoRepository: IRepository<AlgemeneInfo> = AlgemeneInfoRepository()

    /**
     * Geeft een corresponderend Leerling object
     * @param id LeerlHet id van de gewenste leerling
     * @return Een Leerling object, of null als er geen met het id correspondeert
     * @throws Exception thrown door de Repository
     */
    fun getLeerlingById(id: Int): Leerling {
        return leerlingRepository.getById(id)
    }

    /**
     * Geeft een corresponderend Werkgever object
     * @param id Werkgever id van de gewenste Werkgever
     * @return Een Werkgever object, of null als er geen met het id correspondeert
     * @throws Exception thrown door de Repository
     */
    fun getWerkgeverById(id: Int): Werkgever {
        return werkgeverRepository.getById(id)
    }

    /**
     * Geeft een Interssante Werkaanbieding voor de meegegeven leerling, op basis van zijn interesses;
     * Deprecated => functionaliteit verplaatst naar de backend, zie getInteressantsteWerkaanbieding;
     * @param leerling het leerling object waarvoor een Werkaanbieding moet gevonden worden
     * @return Een Werkgever object, of null als er geen met het id correspondeert
     * @throws Exception thrown door de Repository
     */
    fun getWerkaanbiedingForLeerling(leerling: Leerling): Werkaanbieding? {
        if (werkaanbiedingRepository.getAll().isEmpty())
            return null

        return werkaanbiedingRepository.getAll().firstOrNull { wa ->
            !leerling.bewaardeWerkaanbiedingen.any { bw -> bw.id == wa.id } // werkaanbieding mag niet al in bewaarde zitten
                    && !leerling.verwijderdeWerkaanbiedingen.any { vw -> vw.id == wa.id } // werkaanbieding mag niet al in verwijderde zitten
        //          && wa.tags.split(" ").intersect(leerling.interesses.split(" ")).any()
        }
        // minstens 1 tag moet voorkomen in de interesses van de leerling
    }

    /**
     * Geeft een Interssante Werkaanbieding voor de meegegeven leerling, op basis van zijn interesses;
     * @param leerling het leerling object waarvoor een Werkaanbieding moet gevonden worden
     * @return Een Werkgever object, of null als er geen met het id correspondeert
     * @throws Exception thrown door de Repository
     */
    fun getInteressantsteWerkaanbieding(leerlingId: Int): Werkaanbieding? {
        return leerlingRepository.getInteressantsteWerkaanbieding(leerlingId)
    }

    /**
     *Geeft alle specifieke info voor een Werkgever;
     * depricated, wordt niet gebruikt in de app
     * @param werkgever de werkgever van de specifieke info
     * @returns Een lijst met SpecifiekeInfo objecten
     */



    fun getSpecifiekeInfoForWerkgever(werkgever: Werkgever): List<SpecifiekeInfo> {
        return specifiekeInfoRepository.getAll().filter {
            specifiekeInfo -> specifiekeInfo.werkgever.naam == werkgever.naam
        }
    }

    fun update(leerling: Leerling) {
        leerlingRepository.update(leerling)
    }

    fun getAllTags(): List<String> {
        return werkaanbiedingRepository.getAlleTags()
    }

    fun likeWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long) {
        leerlingRepository.likeWerkaanbieding(leerlingId, werkaanbiedingId)
    }

    fun dislikeWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long) {
        leerlingRepository.dislikeWerkaanbieding(leerlingId, werkaanbiedingId)
    }

    fun undoWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long) {
        leerlingRepository.undoWerkaanbieding(leerlingId, werkaanbiedingId)
    }
}