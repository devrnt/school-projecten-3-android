package com.talentcoach.id11.id11_android.models

enum class Geslacht(val getal: Int) {
    Man(0),
    Vrouw(1);

    companion object {
        fun valueOf(getal: Int): Geslacht? = Geslacht.values().find { it.getal == getal }
    }
}