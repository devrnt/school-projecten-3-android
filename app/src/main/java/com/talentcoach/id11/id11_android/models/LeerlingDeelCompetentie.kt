package com.talentcoach.id11.id11_android.models

import java.util.*
/**
 * Deel van *models*.
 *
 * LeerlingDeelCompetentie het verband tussen een leerling en een deelcompentie;
 * gekent door de leerling via LeerlingHoofdCompetentie, bevat de deelcompetentie, de score, beoordelingen;
 * een verwijzing naar de competentie en een veld geslaagd en de datum van geslaag;
 * @property id het id van de leerlingdeelcompetenties
 * @property deelCompetentie de betreffende deelcompentie
 * @property behaald geeft aan of de deelcompentie al behaald is of niet
 * @property datumGeslaagd indien behaald geeft deze aan wanneer dit gebeurde
 * @property beoordelingen bevat de beoordelingen gegeven door leerkrachten op de betreffende deelcompetentie
 * @constructor Maakt een LeerlinDeelCompetentie object
 */
class LeerlingDeelCompetentie(
        val id:Int,
        val deelCompetentie: DeelCompetentie,
        val behaald: Boolean,
        val datumGeslaagd: Date,
        val beoordelingen: MutableList<BeoordelingDeelCompetentie>)