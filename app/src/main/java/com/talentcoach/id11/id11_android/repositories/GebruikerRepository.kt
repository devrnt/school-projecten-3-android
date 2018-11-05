package com.talentcoach.id11.id11_android.repositories

import com.talentcoach.id11.id11_android.models.Gebruiker
import com.talentcoach.id11.id11_android.repositories.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GebruikerRepository {

    @POST("authenticeer")
    fun login(
            @Body gebruiker: Gebruiker
    ): Call<LoginResponse>
}