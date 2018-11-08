package com.talentcoach.id11.id11_android.profiel

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.login.LoginActivity
import com.talentcoach.id11.id11_android.models.Gebruiker
import com.talentcoach.id11.id11_android.models.Geslacht
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingRepositoryRetrofit
import kotlinx.android.synthetic.main.activity_profiel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfielActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    lateinit var leerling: Leerling
    lateinit var gebruiker: Gebruiker

    var leerlingId: Long? = null

    lateinit var userfullNameText: TextView

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        print("De activity komt hier terug ze")
        // Toast.makeText(applicationContext, "de application is terug", Toast.LENGTH_LONG).show()
        getLeerling()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit_btn -> {
                val intent = Intent(this, ProfielEditActivity::class.java)
                val gson = Gson()
                val llnJson = gson.toJson(leerling)
                intent.putExtra("leerling", llnJson)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiel)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        spEditor = sharedPreferences.edit()

        val gson = Gson()
        gebruiker = gson.fromJson(sharedPreferences.getString(getString(R.string.sp_key_user), "Default"), Gebruiker::class.java)

        // the leerling ID will be red from the sharedPreferences
        leerlingId = 1


        logout_btn.setOnClickListener {
            // remove the saved user password and user credentials
            spEditor.putString(getString(R.string.sp_key_password), "")
            spEditor.commit()
            spEditor.putString(getString(R.string.sp_key_user), "")
            spEditor.commit()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        getLeerling()

    }

    private fun getLeerling() {
        val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/"
        val retrofit = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()

        val leerlingRepository = retrofit.create(LeerlingRepositoryRetrofit::class.java)
        val call = leerlingRepository.getById(1)

        call.enqueue(object : Callback<Leerling> {
            // Got response from the server
            // Check response.IsSuccesfull to check for any codes (UnAuthorized...)
            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Succes", Toast.LENGTH_LONG)
                    leerling = response.body()!!
                    fillDetails()

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

    private fun fillDetails() {
        interessesCardTxt.text = leerling.interesses.split(" ").count().toString()
        aantalWerkaanbiedingenTxt.text = leerling.bewaardeWerkaanbiedingen.count().toString()
        userfullNameTxt.text = gebruiker.gebruikersnaam.capitalize()
        firstAndLastnameTxt.text = gebruiker.volledigeNaam
        emailadressTxt.text = "jonasdevrient@school.be"
        richtingTxt.text = leerling.richting.naam.capitalize()
        genderTxt.text = Geslacht.valueOf(leerling.geslacht).toString().capitalize()
        interestsTxt.text = leerling.interesses.capitalize().replace(" ", ", ")
    }
}
