package com.talentcoach.id11.id11_android.profiel

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import com.talentcoach.id11.id11_android.R
import kotlinx.android.synthetic.main.activity_profiel_edit.*

class ProfielEditActivity : AppCompatActivity() {
    var llnInteresses: String? = null
    lateinit var interessesSuggestions: List<String>

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.confirm_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.confirm_btn -> {
                if (checkValidInteresses()) {
                    // TODO("Perform the update to the backend")

                    val intent = Intent(this, ProfielEditActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiel_edit)

        llnInteresses = intent?.getStringExtra("interesses")
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

        nacho_text_view.setIllegalCharacters(',', '.', ';', '\n')


        nacho_text_view.chipifyAllUnterminatedTokens()
        nacho_text_view.setNachoValidator(null)
        nacho_text_view.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL)
        nacho_text_view.setAdapter(adapter)

    }


    private fun checkValidInteresses(): Boolean {
        val allChips = nacho_text_view.allChips
        val interessesToCheck = allChips.map { chip -> chip.text }
        return if (!interessesToCheck.all { word -> interessesSuggestions.contains(word) }) {
            // TODO("Duidelijke aanduiding dat er nog een fout in zit")
            Toast.makeText(applicationContext, "Er is nog een foute suggestie", Toast.LENGTH_LONG).show()
            false
        } else {
            Toast.makeText(applicationContext, "Alles komt overeen", Toast.LENGTH_LONG).show()
            true
        }
    }
}
