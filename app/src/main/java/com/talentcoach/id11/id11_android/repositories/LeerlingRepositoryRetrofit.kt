package com.talentcoach.id11.id11_android.repositories

import com.google.gson.GsonBuilder
import com.talentcoach.id11.id11_android.models.Leerling
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface LeerlingRepositoryRetrofit {
    /**
     * Open a HTTP GET Request to get a Leerling from the server
     * @param id The id of the Leerling to retrieve
     * @return Leerling with the specified id
     */
    @GET("leerlingen/{id}")
    fun getById(@Path("id") id: Int): Call<Leerling>

    @PUT("leerlingen/{id}")
    fun update(@Path("id") id: Int, @Body leerling: Leerling): Call<Leerling>
}

object LeerlingAPI {
    private var API_BASE_URL = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/"
    private val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()

    var repository = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(API_BASE_URL)
            .build()
            .create(LeerlingRepositoryRetrofit::class.java)
}