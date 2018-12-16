package com.talentcoach.id11.id11_android.job

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkgever
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import kotlinx.android.synthetic.main.fragment_werkaanbieding.*
import org.jetbrains.anko.doAsync
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobInfoFragment : Fragment() {
    private var leerlingId: Int? = null
    lateinit var leerling: Leerling
    var werkgever: Werkgever? = null

    lateinit var werkgeverInfoLayout: LinearLayout
    lateinit var progressSpinner: ProgressBar
    lateinit var geenWerkgeverText: TextView
    lateinit var werkgeverNaam: TextView
    lateinit var werkgeverAddress: TextView
    lateinit var werkgeverEmail: TextView
    lateinit var werkgeverTelefoonnr: TextView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_job_info, container, false)

        werkgeverInfoLayout = view.findViewById(R.id.werkgeverInfoLayout) as LinearLayout
        werkgeverInfoLayout.visibility = View.GONE
        geenWerkgeverText = view.findViewById(R.id.geenWerkgeverText) as TextView
        geenWerkgeverText.visibility = View.GONE
        progressSpinner = view.findViewById(R.id.progressSpinner) as ProgressBar
        werkgeverNaam = view.findViewById(R.id.werkgeverNaam)
        werkgeverAddress = view.findViewById(R.id.adrestv)
        werkgeverEmail = view.findViewById(R.id.emailtv)
        werkgeverTelefoonnr = view.findViewById(R.id.telefoonnrtv)

        doAsync {
            val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
            leerlingId = sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()
            getLeerling()
        }

        return view
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
                        werkgever = leerling.werkgever
                        progressSpinner.visibility = View.GONE
                        if (werkgever != null){
                            showWerkgever()
                        } else {
                            showGeenWerkgever()
                        }
                    }
                    else ->
                        // TODO("These cases will only be needed in development, not in production")
                        when (response.code()) {
                            404 -> Toast.makeText(activity, "Leerling niet gevonden", Toast.LENGTH_LONG).show()
                            401 -> Toast.makeText(activity, "Je hebt niet de nodige rechten", Toast.LENGTH_LONG).show()
                        }
                }
            }

            // backend can't be reached
            override fun onFailure(call: Call<Leerling>, t: Throwable) {
//                Toast.makeText(activity, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
                val toast = Toast.makeText(activity, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
                val v = toast.getView().findViewById(android.R.id.message) as TextView
                if (v != null) v.gravity = Gravity.CENTER
                toast.show()
            }
        })
    }

    private fun showWerkgever() {
        werkgeverInfoLayout.visibility = View.VISIBLE
        werkgeverNaam.text = werkgever!!.naam
        werkgeverAddress.text = werkgever!!.werkplaats
        werkgeverEmail.text = werkgever!!.email
        werkgeverTelefoonnr.text = werkgever!!.telefoonNummer.toString()
    }

    private fun showGeenWerkgever() {
        geenWerkgeverText.visibility = View.VISIBLE
    }



}