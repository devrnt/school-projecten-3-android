package com.talentcoach.id11.id11_android.models

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList
/**
 * Deel van *models*.
 *
 * LeerlingHoofdCompetentie het verband tussen een leerling en een hoofdcompentie;
 * gekent door de leerling, bevat de hoofdcompetentie, veld geslaagd, leerlingdeelcompetenties, ...;
 * een verwijzing naar de competentie en een veld geslaagd en de datum van geslaag;
 * @property id het id van de LeerlingHoofdCompetentie
 * @property behaald geeft aan of de Hoofdcompentie al behaald is of niet (behaal als alle deelcompetenties behaald zijn)
 * @property datumGeslaagd indien behaald geeft deze aan wanneer dit gebeurde
 * @property hoofdCompetentie het hoofcompetentie object met de omschrijving
 * @property deelCompetenties bevat de leerlingdeelcompenties die bij deze hoofdcompetentie horen
 * @constructor Maakt een nieuw LeerlinHoofdCompetentie object
 */
class LeerlingHoofdCompetentie(
        val id:Int,
        val behaald: Boolean,
        val datumBehaald: Date,
        val hoofdCompetentie: HoofdCompetentie,
        val deelCompetenties: MutableList<LeerlingDeelCompetentie>
        )