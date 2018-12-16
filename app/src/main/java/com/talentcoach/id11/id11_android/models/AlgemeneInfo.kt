package com.talentcoach.id11.id11_android.models
/**
 * Deel van *models*.
 *
 * Algemene info die wordt gebruikt in de FAQ tab
 *
 * @property id het id van de algemene info
 * @property titel de titel van de algemene info, vormt een vraag in FAQ
 * @property omschrijving het antwoord op de titel, in FAQ
 * @constructor Maakt een nieuw Algemeneen Info object
 */
class AlgemeneInfo(val id: Long, var titel: String, var omschrijving: String)