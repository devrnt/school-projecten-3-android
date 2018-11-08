package com.talentcoach.id11.id11_android.profiel

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import com.hootsuite.nachos.tokenizer.ChipTokenizer
import com.hootsuite.nachos.validator.NachoValidator
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingRepositoryRetrofit
import kotlinx.android.synthetic.main.activity_profiel_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfielEditActivity : AppCompatActivity() {
    var llnInteresses: String? = null
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

    private fun UpdateLeerling() {
        val allChips = nacho_text_view.allChips
        val interessesToCheck = allChips.map { chip -> chip.text }
        val interessesToUpdate = interessesToCheck.joinToString(separator = " ")

        leerling.interesses = interessesToUpdate

        val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/"
        val retrofit = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()

        val leerlingRepository = retrofit.create(LeerlingRepositoryRetrofit::class.java)
        val call = leerlingRepository.update(1, leerling)

        call.enqueue(object : Callback<Leerling> {
            // Got response from the server
            // Check response.IsSuccesfull to check for any codes (UnAuthorized...)
            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Succesvol bijgewerkt", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    when (response.code()) {
                        404 -> Toast.makeText(applicationContext, "Leerling niet gevonden", Toast.LENGTH_LONG).show()
                        401 -> Toast.makeText(applicationContext, "Je hebt niet de nodige rechten", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // This case the server is down, 502_Bad_Gateway...
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
                Toast.makeText(applicationContext, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiel_edit)
        val gson = Gson()
        leerling = gson.fromJson(intent.getStringExtra("leerling"), Leerling::class.java)

        llnInteresses = leerling?.interesses
        if (llnInteresses == null) {
            llnInteresses = ""
        }

        // Get all the interesses (this will prob be fetched from all the users)
        // TODO("Fetch the interesses")
        interessesSuggestions = listOf<String>("teamwork", "haarzorg", "metsen", "computers")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, interessesSuggestions)

        // llnInteresses are split on spaces
        val arrayInteresses = llnInteresses!!.split(" ")
        nacho_text_view.setText(arrayInteresses)

        nacho_text_view.setAdapter(adapter)
        nacho_text_view.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL)

        nacho_text_view.setIllegalCharacters(',', '.', ';', '\n')

        nacho_text_view.chipifyAllUnterminatedTokens()

        nacho_text_view.setNachoValidator(CustomNachoValidator())

    }


    private fun checkValidInteresses(): Boolean {
        val allChips = nacho_text_view.allChips
        val interessesToCheck = allChips.map { chip -> chip.text }
        return if (!interessesToCheck.all { word -> interessesSuggestions.contains(word) }) {
            // TODO("Duidelijke aanduiding dat er nog een fout in zit")
            error_text.text
            false
        } else {
            //Toast.makeText(applicationContext, "Alles komt overeen", Toast.LENGTH_LONG).show()
            true
        }
    }

    // Used to validate if the interesse is a valid interesse (based on the interesseSugesties)
    inner class CustomNachoValidator : NachoValidator {
        /**
         * Corrects the specified text to make it valid.
         *
         * @param invalidText A string that doesn't pass validation: isValid(invalidText)
         * returns false
         * @return A string based on invalidText such as invoking isValid() on it returns true.
         * @see .isValid
         */
        override fun fixText(chipTokenizer: ChipTokenizer, invalidText: CharSequence?): CharSequence {
            val newText = SpannableStringBuilder(invalidText)
            chipTokenizer.terminateAllTokens(newText)
            return "poiuytfd"
        }

        /**
         * Validates the specified text.
         *
         * @return true If the text currently in the text editor is valid.
         * @see .fixText
         */
        override fun isValid(chipTokenizer: ChipTokenizer, text: CharSequence?): Boolean {
            // The text is considered valid if there are no unterminated tokens (everything is a chip)
            val unterminatedTokens = chipTokenizer.findAllTokens(text)
            return unterminatedTokens.isEmpty()
        }

    }

}


