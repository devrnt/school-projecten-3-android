package com.talentcoach.id11.id11_android.repositories

import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.Fuel
import com.talentcoach.id11.id11_android.models.Leerling
import java.net.URL

class LeerlingRepository {
    val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/leerlingen/"

    fun getLeerlingById(id: Long): Leerling {
        val leerling: Leerling?

        try {
            val json = URL(url + id).readText()
            leerling = Klaxon().parse<Leerling>(json)
        } catch (e: Exception){
            throw e
        }
        return leerling!!
    }

    fun saveLeerling(leerling: Leerling) {
        try {
            val json = Klaxon().toJsonString(leerling)
//            val url = URL(url + leerling.id)
//            with(url.openConnection() as HttpURLConnection){
//                requestMethod = "PUT"
//                addRequestProperty("Content-Type", "application/json")
//                outputStream.write(json.toByteArray())
//                outputStream.flush()
//
//            }
            Fuel.put(url + leerling.id).body(json).response {
                request, response, result ->
                println(request)
                println(response)
                println(result)
            }
        } catch (e: Exception){
            throw e
        }
    }


    }