package com.talentcoach.id11.id11_android.models

class Werkaanbieding(val id: Long, val werkgever: Werkgever, val omschrijving: String, val tags: String) {

    constructor(id: Long, werkgever: Werkgever, omschrijving: String): this(id, werkgever, omschrijving, "teamwork")

    override fun toString(): String {
        return "id = $id, werkgever = $werkgever, tags = $tags"
    }
}