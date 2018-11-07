package com.talentcoach.id11.id11_android.repositories

import com.talentcoach.id11.id11_android.models.Leerling
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LeerlingRepositoryRetrofit {
    /**
     * Open a HTTP GET Request to get a Leerling from the server
     * @param id The id of the Leerling to retrieve
     * @return Leerling with the specified id
     */
    @GET("leerlingen/{id}")
    fun getById(@Path("id") id: Int): Call<Leerling>
}