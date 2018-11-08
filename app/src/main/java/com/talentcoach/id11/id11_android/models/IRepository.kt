package com.talentcoach.id11.id11_android.models

interface IRepository<T> {

    fun getById(id: Int): T
    fun update(toUpdate: T)
    fun getAll(): List<T>
}