package com.talentcoach.id11.id11_android.repositories

import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.Werkspreuk
import java.net.URL

class WerkspreukRepository {
    val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/werkspreuken/"

    fun getWerkspreuk(week: Long): Werkspreuk {
        val werkspreuk: Werkspreuk?
        val fetchUrl = "$url/week/$week"

        try {
            val json = URL(fetchUrl).readText()
            werkspreuk = Klaxon().parse<Werkspreuk?>(json)
        } catch (e: Exception) {
            throw e
        }
        return werkspreuk!!
    }
}