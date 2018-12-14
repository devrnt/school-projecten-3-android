package com.talentcoach.id11.id11_android.profiel

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.login.LoginActivity
import com.talentcoach.id11.id11_android.models.Gebruiker
import com.talentcoach.id11.id11_android.models.Geslacht
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import kotlinx.android.synthetic.main.fragment_profiel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ProfielFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    lateinit var gebruiker: Gebruiker
    lateinit var leerling: Leerling

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.edit_menu, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when (item?.itemId) {
//            R.id.edit_btn -> {
//                val intent = Intent(this, ProfielEditActivity::class.java)
//
//                val gson = Gson()
//                val llnJson = gson.toJson(leerling)
//                intent.putExtra(getString(R.string.sp_key_leerling), llnJson)
//
//                startActivity(intent)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    // when activity_profiel_edit is closed, update the data
//    override fun onResume() {
//        super.onResume()
//        getLeerling()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_profiel, container, false)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        spEditor = sharedPreferences.edit()

        val gson = Gson()
        gebruiker = gson.fromJson(sharedPreferences.getString(getString(R.string.login_login_response), "Default"), Gebruiker::class.java)

        getLeerling()

        var logout_btn = view.findViewById(R.id.logout_btn) as Button
        logout_btn.setOnClickListener {
            // remove the saved user password and user credentials
            spEditor.putString(getString(R.string.login_password), "")
            spEditor.apply()
            spEditor.putString(getString(R.string.login_login_response), "")
            spEditor.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            // finish all parent activities
            activity!!.finish()
        }

        return view
    }

    private fun getLeerling() {
        LeerlingAPI.repository.getById(sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()).enqueue(object : Callback<Leerling> {
            override fun onResponse(call: Call<Leerling>, response: Response<Leerling>) {
                when {
                    response.isSuccessful -> {
                        leerling = response.body()!!
                        showDetails()
                    }
                    else ->
                        // TODO("These cases will only be needed in development, not in production")
                        when (response.code()) {
                            404 -> Toast.makeText(activity, getString(R.string.leerling_niet_gevonden), Toast.LENGTH_LONG).show()
                            401 -> Toast.makeText(activity, getString(R.string.geen_rechten), Toast.LENGTH_LONG).show()
                        }
                }
            }

            // backend can't be reached
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
                Toast.makeText(activity, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun showDetails() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        // header details
        // TODO("Will we add the property klas in the backend?")
        interessesCardTxt.text = leerling.interesses.count().toString()
        aantalWerkaanbiedingenTxt.text = leerling.bewaardeWerkaanbiedingen.count().toString()

        // details
        userfullNameTxt.text = gebruiker.gebruikersnaam.capitalize()
        firstAndLastnameTxt.text = "${leerling.voornaam} ${leerling.naam}"
        emailadressTxt.text = leerling.email
        geboorteDatumTxt.text = leerling.geboorteDatum.substring(0,10)
        richtingTxt.text = leerling.richting.naam.capitalize()
        genderTxt.text = Geslacht.valueOf(leerling.geslacht).toString().capitalize()
        when {

            leerling.interesses.isNotEmpty() -> interestsTxt.text = leerling.interesses.joinToString(", ")
            else -> interestsTxt.text = getString(R.string.no_interests)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Tab1Fragment.
         */
        @JvmStatic
        fun newInstance() = ProfielFragment()
    }
}
