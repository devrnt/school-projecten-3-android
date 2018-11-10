package com.talentcoach.id11.id11_android.repositories

import com.talentcoach.id11.id11_android.models.Richting
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RichtingRepository {
    /**
     * Open a HTTP GET Request to get a Richting from the server
     * @param id The id of the richting to retrieve
     * @return Richting with the specified id
     */

    @GET("richtingen/{id}")
    fun getById(@Path("id") id:Int?): Call<Richting>
}