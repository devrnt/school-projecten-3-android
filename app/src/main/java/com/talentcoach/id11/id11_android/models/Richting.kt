package com.talentcoach.id11.id11_android.models

import com.google.gson.annotations.SerializedName
/**
 * Deel van *models*.
 *
 * Richting een bij elkaar horende set van hoofdcompetenties en hun deelcompetenties.
 * Definieert ook het icon en de kleur van de competenties
 * gekent door de leerling, bevat de score, beoordelingen,
 * @property id het id van de Richting
 * @property naam de naam van de richting
 * @property competenties de hoofdcompetenties van de richting
 * @property datumGeslaagd indien behaald geeft deze aan wanneer dit gebeurde
 * @property beoordelingen bevat de beoordelingen gegeven door leerkrachten op de betreffende deelcompetentie
 * @constructor Maakt een nieuw Richting object
 */
class Richting(val id: Int, val naam: String, @SerializedName("activiteiten")var competenties:MutableList<LeerlingHoofdCompetentie>) {

       constructor() : this(-1, "default", mutableListOf())

}