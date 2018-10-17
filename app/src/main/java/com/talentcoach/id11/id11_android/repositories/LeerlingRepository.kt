package com.talentcoach.id11.id11_android.repositories

import com.talentcoach.id11.id11_android.models.Leerling

class LeerlingRepository {
    private val leerling: Leerling = Leerling(listOf("teamwork"))

    fun getLeerlingById(id: Long): Leerling{
        // TODO: http GET
        return leerling
    }
}