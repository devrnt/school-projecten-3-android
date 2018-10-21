package com.talentcoach.id11.id11_android.models

class Werkaanbieding(val werkgever: Werkgever, val omschrijving: String, val tags: String) {
    operator fun component1(): Werkgever {
        return werkgever
    }

    operator fun component2(): String {
        return omschrijving
    }

    constructor(werkgever: Werkgever, omschrijving: String): this(werkgever, omschrijving, "")
}