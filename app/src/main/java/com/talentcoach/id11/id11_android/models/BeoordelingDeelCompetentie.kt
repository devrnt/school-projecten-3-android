package com.talentcoach.id11.id11_android.models

import java.util.*
/**
 * Deel van *models*.
 *
 * BeoordelingDeelCompetentie geeft een beoordeling weer van een Deelcompetentie
 *
 * @property id het id de beoordeling
 * @property score de score die de leerling haalde op de betreffende deelcompetentie
 * @property test de naam van de test waarbij de leerling de score haalde
 * @property datum de datum waarop de beoordeling werd toegekend
 * @constructor Maakt een nieuw BeoordelingDeelCompetentie object
 */
class BeoordelingDeelCompetentie(
        val id: Int,
        val score: Int,
        val test: String,
        val datum: Date)
