//package com.talentcoach.id11.id11_android.data
//
//import com.talentcoach.id11.id11_android.models.*
//
//object DummyDbContext {
//    val werkgevers: MutableList<Werkgever> = mutableListOf()
//    val werkaanbiedingen: MutableList<Werkaanbieding> = mutableListOf()
//    val leerlingen: MutableList<Leerling> = mutableListOf()
//    val specifiekeInfos: MutableList<SpecifiekeInfo> = mutableListOf()
//    val algemeneInfos: MutableList<AlgemeneInfo> = mutableListOf()
//
//    init {
//        resetDbContext()
//    }
//
//    fun resetDbContext() {
//        werkgevers.clear()
//        werkgevers.addAll(mutableListOf(
//                Werkgever(1, "Werkgever 1"),
//                Werkgever(2, "Werkgever 2")
//        ))
//
//        werkaanbiedingen.clear()
//        werkaanbiedingen.addAll(mutableListOf(
//                Werkaanbieding(1, werkgevers[0], "werkaanbieding 1", "teamwork"),
//                Werkaanbieding(2, werkgevers[1], "werkaanbieding 2", "kapper"),
//                Werkaanbieding(3, werkgevers[1], "werkaanbieding 3", "loodgieter"),
//                Werkaanbieding(4, werkgevers[0], "werkaanbieding 4", "teamwork"),
//                Werkaanbieding(5, werkgevers[1], "werkaanbieding 5", "zelfstandig kapper"),
//                Werkaanbieding(6, werkgevers[1], "werkaanbieding 6", "teamwork loodgieter")
//        ))
//
//        leerlingen.clear()
//        leerlingen.addAll(mutableListOf(
//                Leerling(1, Richting(1, "", mutableListOf()), 0, "1993-07-05T00:00:00", "leerling@school.be", "teamwork kapper",
//                        mutableListOf(), mutableListOf(), "Stroobants", "Bruno", mutableListOf()),
//                Leerling(2, Richting(1, "", mutableListOf()), 0, "1993-07-05T00:00:00", "leerling@school.be", "loodgieter",
//                        mutableListOf(werkaanbiedingen[2]), mutableListOf(), "Stroobants", "Bruno", mutableListOf()),
//                Leerling(3, Richting(1, "", mutableListOf()), 0, "1993-07-05T00:00:00", "leerling@school.be", "informatica",
//                        mutableListOf(), mutableListOf(), "Stroobants", "bruno", mutableListOf())
//        ))
//
//        specifiekeInfos.clear()
//        specifiekeInfos.addAll(mutableListOf(
//                SpecifiekeInfo(1, "Titel 1", "Omschrijving 1", werkgevers[0])
//        ))
//
//        algemeneInfos.clear()
//        algemeneInfos.addAll(mutableListOf(
//                AlgemeneInfo(1, "Titel 1", "Omschrijving 1"),
//                AlgemeneInfo(2, "Titel 2", "Omschrijving 2"),
//                AlgemeneInfo(3, "Titel 3", "Omschrijving 3")
//        ))
//    }
//}