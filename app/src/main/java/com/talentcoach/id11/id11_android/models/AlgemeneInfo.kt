package com.talentcoach.id11.id11_android.models

class AlgemeneInfo(var title: String, var description: String)

object AlgemeneInfoSupplier {
    val algemeneInfo = listOf(
            AlgemeneInfo("Ik ben ziek, wat nu?", "Bij ziekte gelieve de werkgever te contacteren (zie gegevens hieronder)\n Afwezigheid moet op voorhand verwittigd worden. Is dit door omstandiheden niet mogelijk geef zo snel mogelijk een seintje. \n\n Contactgegevens: \n -Naam: Sam De Koster\n -GSM: 047966165\n -Email: sam.dekoster@devlieger"),
            AlgemeneInfo("Ik kan niet tijdig aanwezig zijn, wat nu?", "Geef zo rap als mogelijk een seintje aan je werkgever. \nProbeer volgende keer iets vroeger te vertrekken."),
            AlgemeneInfo("Ik voel me niet goed op men werk", "Contacteer uw persoonlijke stagebegeleider of probeer een gesprek te regelen met je stageleider.")
    )
}