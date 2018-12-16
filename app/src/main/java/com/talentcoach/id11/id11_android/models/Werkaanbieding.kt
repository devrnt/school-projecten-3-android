package com.talentcoach.id11.id11_android.models

import java.io.Serializable
/**
 * Deel van *models*.
 * Een joboffer gecreert door Werkgevers, wordt gebruikt in het jobs gedeelte van de applicatie;
 * Werkaanbieding het verband tussen een leerling en een compentie;
 * gekent door de leerling, bevat de score, beoordelingen;
 * een verwijzing naar de competentie en een veld geslaagd en de datum van geslaagd;
 *
 * @property id het id van de Werkaanbieding
 * @property werkgever de Werkgever die de Werkaanbieding aanbiedt
 * @property omschrijving Een korte omschrijving van de job
 * @property tags Interesse tags, deze stemmen overeen met interesse tags van leerlingen, op basis van deze worden de meest interessante per leeling bepaald
 * @constructor Maakt een Werkaanbieding object
 */
class Werkaanbieding(val id: Long,
                     val werkgever: Werkgever,
                     val omschrijving: String,
                     val tags: List<String>) : Serializable