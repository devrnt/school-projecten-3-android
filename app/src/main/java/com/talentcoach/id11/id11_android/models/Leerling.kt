package com.talentcoach.id11.id11_android.models

import java.io.Serializable

class Leerling(val id: Int, var interesses: String, var bewaardeWerkaanbiedingen: MutableList<Werkaanbieding>,
               var verwijderdeWerkaanbiedingen: MutableList<Werkaanbieding>) : Serializable {
}