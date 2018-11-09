package com.talentcoach.id11.id11_android.repositories

import com.talentcoach.id11.id11_android.models.IRepository
import com.talentcoach.id11.id11_android.models.SpecifiekeInfo
import com.talentcoach.id11.id11_android.models.Werkgever

class SpecifiekeInfoRepository: IRepository<SpecifiekeInfo> {
    val specifiekeInfos = mutableListOf(
            SpecifiekeInfo(1, "Contactgegevens", "Ergensstraat 14 \n 3450 Ergens", Werkgever("Werkgever")),
            SpecifiekeInfo(2, "Verdere info", "Lorem ipsum blablablabla tekst tekst derp", Werkgever("Werkgever")),
            SpecifiekeInfo(3, "Taakomschrijving", "Je zal moeten werken en werken en werken en geen geld verdienen, joepie", Werkgever("Werkgever"))
    )

    override fun getById(id: Int): SpecifiekeInfo {
        return specifiekeInfos.first { specifiekeInfo -> specifiekeInfo.id == id }
    }

    override fun update(toUpdate: SpecifiekeInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): List<SpecifiekeInfo> {
        return specifiekeInfos
    }
}