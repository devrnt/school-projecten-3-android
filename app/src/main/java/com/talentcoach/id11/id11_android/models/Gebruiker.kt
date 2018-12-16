package com.talentcoach.id11.id11_android.models
/**
 * Deel van *models*.
 *
 * Gebruiker object gebruikt om in te loggen, en de correcte leerling op te halen
 * @property id het id van de gebruiker
 * @property vooornaam de voornaam van de gebruiker
 * @property achternaam de achternaam van de gebruiker
 * @property gebruikersnaam de gebruikersnaam waarmee de gebruiker inlogt
 * @property wachtwoord het wachtwoord waarmee de gebruiker inlogt
 * @property token het token dat de ingelogde gebruiker valideert
 * @constructor Maakt een DeelCompetentie object
 */
class Gebruiker(
        val id: Long,
        val voornaam: String,
        val naam: String,
        val gebruikersnaam: String,
        val wachtwoord: String,
        val token: String) {

    val volledigeNaam: String
        get() = "$voornaam $naam"


    // used when a login request is sent
    // only gebruikersnaam en wachtwoord are required to send the request
    constructor(gebruikersnaam: String, wachtwoord: String) : this(-1, "", "", gebruikersnaam, wachtwoord, "")
}