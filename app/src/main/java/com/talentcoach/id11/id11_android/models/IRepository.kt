package com.talentcoach.id11.id11_android.models

interface IRepository<T> {

    fun getById(id: Int): T
    fun update(toUpdate: T)
    fun getAll(): List<T>
    fun getCompetentiesById(id: Int): List<LeerlingHoofdCompetentie>
    fun getAlleTags(): List<String>
    fun getInteressantsteWerkaanbieding(leerlingId: Int): Werkaanbieding?
    fun likeWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long)
    fun dislikeWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long)
    fun undoWerkaanbieding(leerlingId: Int, werkaanbiedingId: Long)
    fun removeInteresseLeerling(leerlingId: Int, interesse: String)
    fun addInteresseLeerling(leerlingId: Int, interesse: String)
}