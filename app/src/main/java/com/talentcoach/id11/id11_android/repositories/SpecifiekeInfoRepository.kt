package com.talentcoach.id11.id11_android.repositories

import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.IRepository
import com.talentcoach.id11.id11_android.models.LeerlingHoofdCompetentie
import com.talentcoach.id11.id11_android.models.SpecifiekeInfo
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import java.net.URL

class SpecifiekeInfoRepository: IRepository<SpecifiekeInfo> {
    override fun dislikeWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun likeWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInteressantsteWerkaanbieding(leerlingId: Int): Werkaanbieding? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAlleTags(): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCompetentiesById(id: Int): List<LeerlingHoofdCompetentie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/specifieke-info"

    override fun getById(id: Int): SpecifiekeInfo {
        TODO("not implemented")
    }

    override fun update(toUpdate: SpecifiekeInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): List<SpecifiekeInfo> {
        val specifiekeInfo: List<SpecifiekeInfo>?
        try {
            val json = URL(url).readText()
            specifiekeInfo = Klaxon().parseArray(json)
        } catch (e: Exception) {
            throw e
        }
        return specifiekeInfo!!
    }
}