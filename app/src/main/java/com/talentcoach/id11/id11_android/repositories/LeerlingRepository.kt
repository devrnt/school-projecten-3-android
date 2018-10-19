package com.talentcoach.id11.id11_android.repositories

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.talentcoach.id11.id11_android.models.Leerling

class LeerlingRepository {
    private var leerling: Leerling? = null

    fun getLeerlingById(id: Long): Leerling? {
        val queue = Volley.newRequestQueue(ApplicationContextProvider().getContext())
        val url = "https://localhost:5001/api/leerlingen"

// Request a string response from the provided URL.
        val req = StringRequest(Request.Method.GET, url + id.toString(),
                Response.Listener<String> { response ->
                    leerling = Klaxon().parse<Leerling>(response)
                },
                Response.ErrorListener { })

// Add the request to the RequestQueue.
        queue.add(req)
        // TODO: http GET
        return leerling
    }
}