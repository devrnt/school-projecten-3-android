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
import android.view.Gravity
import android.widget.TextView



/**
 * Deel van *login*.
 *
 * Fragment dat inputs voor username en passwoord ondersteund, met een login knop;
 * @property gebruikersnaamInput De textinput die gebruikt wordt voor de username
 * @property wachtwoordInput De textinput die gebruik wordt voor het wachtwoord
 * @property gebruikersnaamInputLayout De input layout voor username error messages
 * @property wachtwoordInputLayout De input layout voor password error message
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

    /**
     * Hulpmethode die controlleert of er een geldige username-combinatie werd ingevuld
     * @return Boolean
     */
    private fun validGebruikersnaam(): Boolean {
        return gebruikersnaamInput.text != null && gebruikersnaamInput.text.isNotBlank()
    }

    /**
     * Hulpmethode die controlleert of er een geldige wachtwoord-combinatie werd ingevuld
     * @return Boolean
     */
    private fun validWachtwoord(): Boolean {
        return wachtwoordInput.text != null && wachtwoordInput.text.trim().length >= 8
    }


    /**
     * Hulpmethode die uit de local storage via de sharedPreferences key values pairs kan ophalen;
     * voor username en paswoord van een eerder ingelogde user
     *
     */
    private fun readSharedPreferences() {
        val gebruikersnaam = sharedPreferences.getString(getString(R.string.login_username), "")
        val wachtwoord = sharedPreferences.getString(getString(R.string.login_password), "")

        // set the input with the username and password that was red from the sharedPreferences
        gebruikersnaamInput.setText(gebruikersnaam)
        wachtwoordInput.setText(wachtwoord)
    }


    /**
     * Methode die op basis van de text- en wachtwoordinput de gebruiker inlogd;
     * Op basis van de loginresponse wordt vervolgens het correcte Leerling object opgehaald
     *
     */
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
                    spEditor.putString(getString(R.string.login_username), gebruiksernaamStr)
                    spEditor.apply()
                    spEditor.putString(getString(R.string.login_password), wachtwoordStr)
                    spEditor.apply()

                    //Toast.makeText(context, "${response.body()?.voornaam} ${getString(R.string.succesfull_login)}", Toast.LENGTH_LONG).show()

                    // store the LoginResponse in SharedPreferences
                    val gson = Gson()
                    val jsonGebruiker = gson.toJson(response.body())
                    // leerling object ophalen
                    val leerlingId = response.body()?.concreteGebruikerId
                    spEditor.putString(getString(R.string.sp_key_leerling), leerlingId.toString())
                    spEditor.putString(getString(R.string.login_login_response), jsonGebruiker)
                    spEditor.apply()

                    //get leerling object

                    val call2 = LeerlingAPI.repository.getById(leerlingId!!)
                    call2.enqueue(object : Callback<Leerling> {
                        override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {
                            if (response.isSuccessful) {

                                Toast.makeText(context, "${getString(R.string.welkom)} ${response.body()?.voornaam}!", Toast.LENGTH_LONG).show()

                            } else {
                                Toast.makeText(context, getString(R.string.wrong_login_credentials), Toast.LENGTH_LONG).show()
                            }
                        }

                        // backend can't be reached
                        override fun onFailure(call: Call<Leerling>, t: Throwable) {
                            println(call)
                            println(t.message)
//                            Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
                            val toast = Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
                            val v = toast.getView().findViewById(android.R.id.message) as TextView
                            if (v != null) v.gravity = Gravity.CENTER
                            toast.show()

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
//                Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
                val toast = Toast.makeText(context, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
                val v = toast.getView().findViewById(android.R.id.message) as TextView
                if (v != null) v.gravity = Gravity.CENTER
                toast.show()
            }
        })
    }
}
