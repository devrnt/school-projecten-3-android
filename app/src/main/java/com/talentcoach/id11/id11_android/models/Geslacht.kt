package com.talentcoach.id11.id11_android.models
/**
 * Deel van *models*.
 *
 * Geslacht. Enum:
 * 0 = Man
 * 1 = Vrouw
 *
 */
enum class Geslacht(val getal: Int) {
    Man(0),
    Vrouw(1);

    companion object {
        fun valueOf(getal: Int): Geslacht? = Geslacht.values().find { it.getal == getal }
    }
}