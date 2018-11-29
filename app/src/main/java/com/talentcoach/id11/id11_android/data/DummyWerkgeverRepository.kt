//package com.talentcoach.id11.id11_android.data
//
//import com.talentcoach.id11.id11_android.models.IRepository
//import com.talentcoach.id11.id11_android.models.LeerlingHoofdcompetentie
//import com.talentcoach.id11.id11_android.models.Werkgever
//
//class DummyWerkgeverRepository: IRepository<Werkgever> {
//    override fun getCompetentiesById(id: Int): List<LeerlingHoofdcompetentie> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getById(id: Int): Werkgever {
//        return DummyDbContext.werkgevers.first { werkgever -> werkgever.id == id }
//    }
//
//    override fun update(toUpdate: Werkgever) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getAll(): List<Werkgever> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}