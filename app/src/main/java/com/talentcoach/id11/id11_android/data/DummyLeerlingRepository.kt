//package com.talentcoach.id11.id11_android.data
//
//import com.talentcoach.id11.id11_android.models.IRepository
//import com.talentcoach.id11.id11_android.models.Leerling
//import com.talentcoach.id11.id11_android.models.LeerlingHoofdcompetentie
//
//class DummyLeerlingRepository: IRepository<Leerling> {
//    override fun getCompetentiesById(id: Int): List<LeerlingHoofdcompetentie> {
//        return DummyDbContext.leerlingen.first {
//            lln -> lln.id == id
//        }.hoofdCompetenties
//    }
//
//    override fun getById(id: Int): Leerling {
//        return DummyDbContext.leerlingen.first {
//            leerling -> leerling.id == id
//        }
//    }
//
//    override fun update(toUpdate: Leerling) {
//        val index = DummyDbContext.leerlingen.indexOfFirst {
//            leerling -> leerling.id == toUpdate.id
//        }
//
//        DummyDbContext.leerlingen[index] = toUpdate
//    }
//
//    override fun getAll(): List<Leerling> {
//        return DummyDbContext.leerlingen
//    }
//}