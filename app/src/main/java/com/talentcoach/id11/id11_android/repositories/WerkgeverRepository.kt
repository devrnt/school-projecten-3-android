package com.talentcoach.id11.id11_android.repositories

import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.IRepository
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkgever
import java.net.URL

class WerkgeverRepository: IRepository<Werkgever> {
    val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/werkgevers/"

    override fun getById(id: Int): Werkgever {
        val werkgever: Werkgever?

        try {
            val json = URL(url + id).readText()

            werkgever = Klaxon().parse<Werkgever>(json)

        } catch (e: Exception) {
            throw e
        }

        return werkgever!!
    }

    override fun update(toUpdate: Werkgever) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): List<Werkgever> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}