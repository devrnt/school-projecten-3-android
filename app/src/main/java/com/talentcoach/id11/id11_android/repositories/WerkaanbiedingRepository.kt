package com.talentcoach.id11.id11_android.repositories

import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.*
import java.io.Serializable
import java.net.URL

class WerkaanbiedingRepository: IRepository<Werkaanbieding>, Serializable {
    override fun addInteresseLeerling(leerlingId: Int, interesse: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeInteresseLeerling(leerlingId: Int, interesse: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun undoWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dislikeWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun likeWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInteressantsteWerkaanbieding(leerlingId: Int): Werkaanbieding? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCompetentiesById(id: Int): List<LeerlingHoofdCompetentie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/werkaanbiedingen/"

    override fun getById(id: Int): Werkaanbieding {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(toUpdate: Werkaanbieding) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): List<Werkaanbieding> {
        val werkaanbiedingen : List<Werkaanbieding>?

        try {
            val result = URL(url).readText()
            werkaanbiedingen = Klaxon().parseArray(result)!!
        } catch (e: Exception){
            throw e
        }

        return werkaanbiedingen
    }

    override fun getAlleTags(): List<String> {
        val alleTags : List<String>?

        try {
            val result = URL(url + "tags").readText()
            alleTags = Klaxon().parseArray(result)!!
        } catch (e: Exception) {
            throw e
        }

        return alleTags
    }

}