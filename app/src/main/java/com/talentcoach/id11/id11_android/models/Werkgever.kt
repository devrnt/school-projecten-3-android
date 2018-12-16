package com.talentcoach.id11.id11_android.models
/**
 * Deel van *models*.
 *
 * Een Werkgever is een weergave van een werkgever-gebruiker die jobs aanbiedt.
 *
 * @property id het id van de Werkgever
 * @property naam naam van de werkgever, meestal bedrijfsnaam
 * @property werkplaats het adres van de werkplaats
 * @property email een contact email adres voor de werkgever
 * @property telefoonNummer een contact telefoonnummer voor de werkgever
 * @constructor Maakt een nieuw Werkgever object
 */
class Werkgever(val id: Int, val naam: String, val werkplaats: String, val email: String, val telefoonNummer: Long)
