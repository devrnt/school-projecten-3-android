package com.talentcoach.id11.id11_android.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.talentcoach.id11.id11_android.HomeActivity
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Gebruiker
import com.talentcoach.id11.id11_android.repositories.GebruikerRepository
import com.talentcoach.id11.id11_android.repositories.responses.LoginResponse
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    var gebruikersnaamInput: EditText? = null
    var wachtwoordInput: EditText? = null

    var gebruikersnaamInputLayout: TextInputLayout? = null
    var wachtwoordInputLayout: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        spEditor = sharedPreferences.edit()

    }

    private fun checkSharedPreferences() {
        val gebruikersnaam = sharedPreferences.getString(getString(R.string.sp_key_username), "")
        val wachtwoord = sharedPreferences.getString(getString(R.string.sp_key_password), "")
        gebruikersnaamInput?.setText(gebruikersnaam)
        wachtwoordInput?.setText(wachtwoord)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        gebruikersnaamInput = view.gebruikersNaam_input
        wachtwoordInput = view.wachtwoord_input

        gebruikersnaamInputLayout = view.gebruikersNaam_inputLayout
        wachtwoordInputLayout = view.wachtwoord_inputLayout

        checkSharedPreferences()


        view.login_btn.setOnClickListener {
            if (!validGebruikersnaam(gebruikersNaam_input.text)) {
                gebruikersNaam_inputLayout.error = getString(R.string.error_username)
            } else {
                gebruikersNaam_inputLayout.error = null
            }

            if (!validWachtwoord(wachtwoord_input.text)) {
                wachtwoord_inputLayout.error = getString(R.string.error_password)
            } else {
                wachtwoord_inputLayout.error = null
            }

            if (validGebruikersnaam(gebruikersNaam_input.text) && validWachtwoord(wachtwoord_input.text)) {
                attemptLogin()
            }
        }

        //  TODO("Clear the error once more than 8 characters are typed", )

        return view
    }

    private fun validGebruikersnaam(text: Editable?): Boolean {
        return text != null && text.isNotBlank()
    }

    private fun validWachtwoord(text: Editable?): Boolean {
        return text != null && text.trim().length >= 8
    }


    private fun attemptLogin() {
        // Reset errors
        gebruikersnaamInputLayout?.error = null
        wachtwoordInputLayout?.error = null

        // Store values at the time of the login attempt
        val gebruiksernaamStr = gebruikersnaamInput?.text.toString()
        val wachtwoordStr = wachtwoordInput?.text.toString()

        val gebruiker = Gebruiker(gebruiksernaamStr, wachtwoordStr)


        // Validation checks are done above
        val url = "http://projecten3studserver11.westeurope.cloudapp.azure.com/api/gebruikers/"

        val retrofit = Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()

        val gebruikersRepository = retrofit.create(GebruikerRepository::class.java)
        val call = gebruikersRepository.login(gebruiker)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // Store gebruiker and wachtwoord in SharedPreferences
                    spEditor.putString(getString(R.string.sp_key_username), gebruiksernaamStr)
                    spEditor.commit()
                    spEditor.putString(getString(R.string.sp_key_password), wachtwoordStr)
                    spEditor.commit()

                    Toast.makeText(context, "${response.body()?.voornaam} ${getString(R.string.succesfull_login)}", Toast.LENGTH_LONG).show()

                    // Store the LoginResponse in SharedPreferences
                    val gson = Gson()
                    val jsonGebruiker = gson.toJson(response.body())

                    spEditor.putString(getString(R.string.sp_key_user), jsonGebruiker)
                    spEditor.commit()

                    
                    // GOTO the HomeActivity
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(context, getString(R.string.wrong_login_credentials), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
            }
        })
    }
}
