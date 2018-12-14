package com.talentcoach.id11.id11_android.repositories

import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.*
import java.net.URL

class WerkgeverRepository: IRepository<Werkgever> {
    override fun getAlleTags(): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCompetentiesById(id: Int): List<LeerlingHoofdCompetentie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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