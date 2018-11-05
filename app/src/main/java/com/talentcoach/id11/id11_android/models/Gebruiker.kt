package com.talentcoach.id11.id11_android.models

class Gebruiker(
        val id: Long,
        val voornaam: String,
        val naam: String,
        val gebruikersnaam: String,
        val wachtwoord: String,
        val token: String) {

    // used when a login request is sent
    // only gebruikersnaam en wachtwoord are required to send the request
    constructor(gebruikersnaam: String, wachtwoord: String) : this(-1, "", "", gebruikersnaam, wachtwoord, "")
}