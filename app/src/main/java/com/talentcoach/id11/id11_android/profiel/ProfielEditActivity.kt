package com.talentcoach.id11.id11_android.profiel

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import kotlinx.android.synthetic.main.activity_profiel_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfielEditActivity : AppCompatActivity() {

    var llnInteresses: String? = null

    // these suggestions will be displayed when the user is typing
    lateinit var interessesSuggestions: List<String>

    lateinit var leerling: Leerling

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.confirm_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.confirm_btn -> {
                if (checkValidInteresses()) {
                    UpdateLeerling()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiel_edit)
        val gson = Gson()
        leerling = gson.fromJson(intent.getStringExtra(getString(R.string.sp_key_leerling)), Leerling::class.java)

        llnInteresses = leerling.interesses
        if (llnInteresses == null) {
            llnInteresses = ""
        }

        // get all the interesses (this will prob be fetched from all the users)
        // TODO("Fetch the interesses")
        interessesSuggestions = listOf("teamwork", "haarzorg", "metsen", "computers")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, interessesSuggestions)

        // llnInteresses are split on spaces
        when {
            llnInteresses?.isNotEmpty()!! -> {
                val arrayInteresses = llnInteresses!!.split(" ")
                nacho_text_view.setText(arrayInteresses)
            }
        }

        // nacho setup
        nacho_text_view.setAdapter(adapter)
        nacho_text_view.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL)

        nacho_text_view.setIllegalCharacters(',', '.', ';', '\n')

        nacho_text_view.chipifyAllUnterminatedTokens()
    }

    private fun UpdateLeerling() {
        val allChips = nacho_text_view.allChips
        val interessesToCheck = allChips.map { chip -> chip.text }
        val interessesToUpdate = interessesToCheck.joinToString(separator = " ")

        leerling.interesses = interessesToUpdate

        // TODO("Read leerlingId from sharedPreferences or getExtra from intent")
        val call = LeerlingAPI.repository.update(1, leerling)

        call.enqueue(object : Callback<Leerling> {
            // got response from the server
            // check response.IsSuccesfull to check for any codes (UnAuthorized...)
            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, getString(R.string.profile_updated), Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    // TODO("These cases will only be needed in development, not in production")
                    when (response.code()) {
                        404 -> Toast.makeText(applicationContext, "Leerling niet gevonden", Toast.LENGTH_LONG).show()
                        401 -> Toast.makeText(applicationContext, "Je hebt niet de nodige rechten", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // Backend can't be reached
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
                Toast.makeText(applicationContext, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun checkValidInteresses(): Boolean {
        // get list of strings of the chips
        val interessesToCheck = nacho_text_view.chipValues
        // i think this can be done lot easier
        var fouteInteresses = ""
        interessesToCheck.forEach { woord ->
            if (!interessesSuggestions.contains(woord)) {
                if (fouteInteresses.isNotEmpty()) fouteInteresses += ","
                fouteInteresses += " $woord"
            }
        }

        return when {
            !interessesToCheck.all { word -> interessesSuggestions.contains(word) } -> {
                if (fouteInteresses.contains(",")) {
                    error_text.text = getString(R.string.foute_suggesties_meervoud, fouteInteresses)
                } else {
                    error_text.text = getString(R.string.foute_suggesties_enkel, fouteInteresses)
                }
                false
            }
            else -> true
        }
    }
}


