package com.talentcoach.id11.id11_android.repositories

import com.talentcoach.id11.id11_android.models.Gebruiker
import com.talentcoach.id11.id11_android.repositories.responses.LoginResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface GebruikerRepository {

    @POST("gebruikers/authenticeer")
    fun login(
            @Body gebruiker: Gebruiker
    ): Call<LoginResponse>
}


object GebruikersAPI {
    private var API_BASE_URL = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/"
    var repository = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_BASE_URL)
            .build()
            .create(GebruikerRepository::class.java)
}