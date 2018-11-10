package com.talentcoach.id11.id11_android.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Leerling(val id: Int, val richting: Richting, val geslacht: Int, var interesses: String, var bewaardeWerkaanbiedingen: MutableList<Werkaanbieding>,
               var verwijderdeWerkaanbiedingen: MutableList<Werkaanbieding>, var naam:String, var voornaam:String,
               @SerializedName("competenties") var competenties: MutableList<Competentie>) : Serializable {

}