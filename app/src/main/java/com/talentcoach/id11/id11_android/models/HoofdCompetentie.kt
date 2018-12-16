package com.talentcoach.id11.id11_android.models
/**
 * Deel van *models*.
 *
 * Hoofdcompetentie heeft een omschrijving, graad, icon en kleur, bevat deelcompetenties
 * @property id het id de HoofdCompetentie
 * @property omschrijving tekst die de hoofdcompetentie omschrijft
 * @property graad Beschrijft de graad van de hoofdcompetentie.
 * @property icon Verwijst naar het icoon van de richting waartoe de hoofdcompetentie behoort
 * @property kleur Verwijst naar de kleur van de richting waartoe de hoofdcompetentie behoort
 * @property deelCompetenties een lijst van bijhorende deelcompetenties
 * @constructor Maakt een HoofdCompetentie object
 */
class HoofdCompetentie(
        val id: Long,
        val omschrijving: String,
        val graad: String,
        val icon: String,
        val kleur: String,
        val deelCompetenties: MutableList<DeelCompetentie>)