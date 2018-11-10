package com.talentcoach.id11.id11_android.profiel

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.login.LoginActivity
import com.talentcoach.id11.id11_android.models.Gebruiker
import com.talentcoach.id11.id11_android.models.Geslacht
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import kotlinx.android.synthetic.main.activity_profiel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class ProfielActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    lateinit var gebruiker: Gebruiker
    lateinit var leerling: Leerling

    private var leerlingId: Int? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit_btn -> {
                val intent = Intent(this, ProfielEditActivity::class.java)

                val gson = Gson()
                val llnJson = gson.toJson(leerling)
                intent.putExtra(getString(R.string.sp_key_leerling), llnJson)

                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // when activity_profiel_edit is closed, update the data
    override fun onResume() {
        super.onResume()
        getLeerling()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiel)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        spEditor = sharedPreferences.edit()

        val gson = Gson()
        gebruiker = gson.fromJson(sharedPreferences.getString(getString(R.string.sp_key_user), "Default"), Gebruiker::class.java)

        // TODO("Read the leerlingId from the shared preferences, the id will probably be in the gebruiker")
        leerlingId = 1

        getLeerling()

        logout_btn.setOnClickListener {
            // remove the saved user password and user credentials
            spEditor.putString(getString(R.string.sp_key_password), "")
            spEditor.apply()
            spEditor.putString(getString(R.string.sp_key_user), "")
            spEditor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // finish all parent activities
            finishAffinity()
        }

    }

    private fun getLeerling() {

        val call = LeerlingAPI.repository.getById(leerlingId!!)

        call.enqueue(object : Callback<Leerling> {
            // Got response from the server
            // check response.IsSuccesfull to check for any codes (UnAuthorized...)
            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {
                when {
                    response.isSuccessful -> {
                        leerling = response.body()!!
                        showDetails()
                    }
                    else ->
                        // TODO("These cases will only be needed in development, not in production")
                        when (response.code()) {
                            404 -> Toast.makeText(applicationContext, "Leerling niet gevonden", Toast.LENGTH_LONG).show()
                            401 -> Toast.makeText(applicationContext, "Je hebt niet de nodige rechten", Toast.LENGTH_LONG).show()
                        }
                }
            }

            // backend can't be reached
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
                Toast.makeText(applicationContext, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun showDetails() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        // header details
        // TODO("Will we add the property klas in the backend?")
        klas_text.text = "3HUc"
        interessesCardTxt.text = leerling.interesses.split(" ").count().toString()
        aantalWerkaanbiedingenTxt.text = leerling.bewaardeWerkaanbiedingen.count().toString()

        // details
        userfullNameTxt.text = gebruiker.gebruikersnaam.capitalize()
        firstAndLastnameTxt.text = gebruiker.volledigeNaam
        emailadressTxt.text = leerling.email
        geboorteDatumTxt.text = dateFormat.format(leerling.geboorteDatum)
        richtingTxt.text = leerling.richting.naam.capitalize()
        genderTxt.text = Geslacht.valueOf(leerling.geslacht).toString().capitalize()
        when {
            leerling.interesses.isNotEmpty() -> interestsTxt.text = leerling.interesses.capitalize().replace(" ", ", ")
            else -> interestsTxt.text = getString(R.string.no_interests)
        }
    }
}
