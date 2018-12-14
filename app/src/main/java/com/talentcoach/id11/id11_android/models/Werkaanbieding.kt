package com.talentcoach.id11.id11_android.models

import java.io.Serializable

class Werkaanbieding(val id: Long,
                     val werkgever: Werkgever,
                     val omschrijving: String,
                     val tags: List<String>) : Serializable