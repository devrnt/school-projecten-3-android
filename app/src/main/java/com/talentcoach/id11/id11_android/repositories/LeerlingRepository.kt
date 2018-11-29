package com.talentcoach.id11.id11_android.repositories

import android.util.Log
import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.IRepository
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.LeerlingHoofdcompetentie
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL

class LeerlingRepository: IRepository<Leerling>, Serializable {

    val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/leerlingen/"

    /**
     * Open a HTTP GET Request to get a Leerling from the server
     * @param id The id of the Leerling to retrieve
     * @throws Exception Thrown by the server or by the HTTP Request
     * @return Leerling with the specified id
     */
    override fun getById(id: Int): Leerling {
        val leerling: Leerling?

        try {
            val json = URL(url + id).readText()

            leerling = Klaxon().parse<Leerling>(json);
            Log.d(Log.DEBUG.toString(),leerling.toString());
            //debugging purposes
            println("Hier moet hij dan zogezegd de json printen die hij terugkrijgt van de url")
            println(json)
        } catch (e: Exception) {
            throw e
        }

        return leerling!!
    }

    override fun getCompetentiesById(id: Int): MutableList<LeerlingHoofdcompetentie> {
        var hoofdcompetenties: MutableList<LeerlingHoofdcompetentie>?

        try {
            val json = URL(url + id + "/competenties").readText()

            hoofdcompetenties = Klaxon().parse<MutableList<LeerlingHoofdcompetentie>>(json)
            Log.d(Log.DEBUG.toString(), hoofdcompetenties.toString())
        } catch (ex: java.lang.Exception) {
            throw ex
        }

        return hoofdcompetenties!!
    }

    /**
     * Persists a Leerling via a PUT Request to the server
     * @param toUpdate The Leerling to persist
     * @throws Exception Thrown by the server or HTTP request
     */
    override fun update(toUpdate: Leerling) {
        try {
            val json = Klaxon().toJsonString(toUpdate)
            val url = URL(url + toUpdate.id)
            with(url.openConnection() as HttpURLConnection) {
                connectTimeout = 5000
                requestMethod = "PUT"
                addRequestProperty("Content-Type", "application/json")
                outputStream.write(json.toByteArray())
                outputStream.flush()

                // debugging purposes
                println(json)
                println("$responseCode $responseMessage")

            }

        } catch (e: Exception) {
            throw e
        }
    }

    override fun getAll(): List<Leerling> {
        TODO("not implemented: functionality is not yet required")
    }

}