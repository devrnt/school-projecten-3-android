package com.talentcoach.id11.id11_android.models

import java.io.Serializable
/**
 * Deel van *models*.
 *
 * Leerling object, bevat alle persoonlijke informatie van de leerling, de werkaanbiedingen, hoofdcompetenties, ...
 * @property id het id van de gebruiker
 * @property vooornaam de voornaam van de leerling
 * @property achternaam de achternaam van de leerling
 * @property geslacht het geslacht van de leerling
 * @property email het email adres van de leerling
 * @property interesses een lijst van de intresses van de leerling, op basis hiervan worden WergAanbiedingen gevonden
 * @property bewaardeWerkaanbiedingen de werkaanbiedingen die de leerling als interessant heeft aangeduid
 * @property verwijderdeWerkaanbiedingen de werkaanbiedingen die de leerling als niet interessant heeft aangeduid
 * @property hoofdCompetenties de hoofdcompetenties, met daarin de deelcompetenties van de leerling
 * @property werkgever de werkgever van de leerling, indien een werkaanbieding als werk aangeweze krijgt
 * @constructor Maakt een nieuw Leerling object
 */
class Leerling(
        val id: Int,
        var richting: Richting,
        val geslacht: Int,
        val geboorteDatum: String,
        var email: String,
        var interesses: List<String>,
        val bewaardeWerkaanbiedingen: MutableList<Werkaanbieding>,
        val verwijderdeWerkaanbiedingen: MutableList<Werkaanbieding>,
        val naam: String,
        val voornaam: String,
        val hoofdCompetenties: MutableList<LeerlingHoofdCompetentie>,
        val werkgever: Werkgever?) : Serializable