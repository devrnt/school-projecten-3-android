package com.talentcoach.id11.id11_android.repositories

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.Leerling
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.URL

class LeerlingRepository {

    fun getLeerlingById(id: Long): Leerling {
        var leerling: Leerling? = null

        try {
            val json = URL("http://projecten3studserver11.westeurope.cloudapp.azure.com/api/leerlingen/1").readText()
            leerling = Klaxon().parse<Leerling>(json)
        } catch (e: Exception){
            throw e
        }
        return leerling!!
    }


    }