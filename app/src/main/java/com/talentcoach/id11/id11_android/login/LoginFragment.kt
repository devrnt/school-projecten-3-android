package com.talentcoach.id11.id11_android.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import com.talentcoach.id11.id11_android.BottomNavigationActivity
import com.talentcoach.id11.id11_android.HomeActivity
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Gebruiker
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.GebruikersAPI
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import com.talentcoach.id11.id11_android.repositories.responses.LoginResponse
import kotlinx.android.synthetic.main.fragment_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.absoluteValue

/**
 * Fragment that shows an input for username and password and a login button
 * @property gebruikersnaamInput The text input that is used to type the username
 * @property wachtwoordInput The text input that is used to type the password
 * @property gebruikersnaamInputLayout The input layout that is used to display username error messages
 * @property wachtwoordInputLayout The input layout that is used to display password error message
 */
class LoginFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    // actual text inputs
    lateinit var gebruikersnaamInput: EditText
    lateinit var wachtwoordInput: EditText

    // input layouts for the text inputs
    lateinit var gebruikersnaamInputLayout: TextInputLayout
    lateinit var wachtwoordInputLayout: TextInputLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        spEditor = sharedPreferences.edit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // set the class attributes to the corresponding elements in the xml
        gebruikersnaamInput = view.gebruikersNaam_input
        wachtwoordInput = view.wachtwoord_input

        gebruikersnaamInputLayout = view.gebruikersNaam_inputLayout
        wachtwoordInputLayout = view.wachtwoord_inputLayout

        readSharedPreferences()

        view.login_btn.setOnClickListener {
            when {
                // if not valid set error message for username
                !validGebruikersnaam() -> gebruikersnaamInputLayout.error = getString(R.string.error_username)
                // remove the error message for username
                else -> gebruikersnaamInputLayout.error = null
            }

            when {
                // if not valid set error message for password
                !validWachtwoord() -> wachtwoordInputLayout.error = getString(R.string.error_password)
                // remove the error message for password
                else -> wachtwoordInputLayout.error = null
            }

            // if username and password valid attempt to login
            when {
                validGebruikersnaam() && validWachtwoord() -> attemptLogin()
            }
        }

        return view
    }

    private fun validGebruikersnaam(): Boolean {
        return gebruikersnaamInput.text != null && gebruikersnaamInput.text.isNotBlank()
    }

    private fun validWachtwoord(): Boolean {
        return wachtwoordInput.text != null && wachtwoordInput.text.trim().length >= 8
    }

    private fun readSharedPreferences() {
        val gebruikersnaam = sharedPreferences.getString(getString(R.string.sp_key_username), "")
        val wachtwoord = sharedPreferences.getString(getString(R.string.sp_key_password), "")

        // set the input with the username and password that was red from the sharedPreferences
        gebruikersnaamInput.setText(gebruikersnaam)
        wachtwoordInput.setText(wachtwoord)
    }

    private fun attemptLogin() {
        // reset error messages
        gebruikersnaamInputLayout.error = null
        wachtwoordInputLayout.error = null

        // store values at the time of the login attempt
        val gebruiksernaamStr = gebruikersnaamInput.text.toString()
        val wachtwoordStr = wachtwoordInput.text.toString()

        val gebruiker = Gebruiker(gebruiksernaamStr, wachtwoordStr)

        val call = GebruikersAPI.repository.login(gebruiker)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {

                    // store gebruiker and wachtwoord in SharedPreferences
                    spEditor.putString(getString(R.string.sp_key_username), gebruiksernaamStr)
                    spEditor.apply()
                    spEditor.putString(getString(R.string.sp_key_password), wachtwoordStr)
                    spEditor.apply()

                    //Toast.makeText(context, "${response.body()?.voornaam} ${getString(R.string.succesfull_login)}", Toast.LENGTH_LONG).show()

                    // store the LoginResponse in SharedPreferences
                    val gson = Gson()
                    val jsonGebruiker = gson.toJson(response.body())
                    // leerling object ophalen
                    val leerlingId = response.body()?.concreteGebruikerId
                    spEditor.putString(getString(R.string.sp_key_leerling), leerlingId.toString())
                    spEditor.putString(getString(R.string.sp_key_user), jsonGebruiker)
                    spEditor.apply()

                    //get leerling object

                    val call2 = LeerlingAPI.repository.getById(leerlingId!!)
                    call2.enqueue(object : Callback<Leerling> {
                        override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {
                            if (response.isSuccessful) {

                                Toast.makeText(context, "${response.body()?.voornaam} ${getString(R.string.succesfull_login)}", Toast.LENGTH_LONG).show()

                            } else {
                                Toast.makeText(context, getString(R.string.wrong_login_credentials), Toast.LENGTH_LONG).show()
                            }
                        }

                        // backend can't be reached
                        override fun onFailure(call: Call<Leerling>, t: Throwable) {
                            println(call)
                            println(t.message)
                            Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
                        }
                    })


                    // goto the HomeActivity
                    val intent = Intent(activity, BottomNavigationActivity::class.java)
                    startActivity(intent)
                    activity?.finish()

                } else {
                    Toast.makeText(context, getString(R.string.wrong_login_credentials), Toast.LENGTH_LONG).show()
                }
            }

            // backend can't be reached
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                println(call)
                println(t.message)
                Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
            }
        })
    }
}
