package com.talentcoach.id11.id11_android.communicatie


import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast

import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Richting
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import kotlinx.android.synthetic.main.fragment_aanbiedingen.*
import kotlinx.android.synthetic.main.fragment_werkaanbieding.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 *
 */
class AanbiedingenFragment : Fragment() {


    private var leerlingId: Int? = null

    lateinit var leerling: Leerling
    lateinit var werkaanbieding: Werkaanbieding


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_aanbiedingen, container, false)
        var aanbiedingsInfo = view.findViewById(R.id.aanbiedingsInfo) as LinearLayout
        aanbiedingsInfo.visibility = View.GONE
        var progressSpinner = view.findViewById(R.id.progressSpinner) as ProgressBar
        progressSpinner.visibility = View.VISIBLE
        var dislikeBtn = view.findViewById(R.id.dislikeBtn) as MaterialButton
        dislikeBtn.isEnabled = false
        var likeBtn = view.findViewById(R.id.likeBtn) as MaterialButton
        likeBtn.isEnabled = false

        doAsync {
            // TODO("Get leerlingId van de ingelogde leerling")
            leerlingId = 1
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
                        getWerkaanbieding()
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
                Toast.makeText(activity, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun getWerkaanbieding() {
        doAsync {
            var temp = DataManager.getWerkaanbiedingForLeerling(leerling)
            uiThread {
                if (temp != null) {
                    werkaanbieding = temp
                    showAanbieding()
                } else {
                    showGeenAanbieding()
                }
            }
        }
    }

    private fun showAanbieding() {
        progressSpinner.visibility = View.GONE
        aanbiedingsInfo.visibility = View.VISIBLE
        dislikeBtn.isEnabled = true
        likeBtn.isEnabled = true

        werkgeverNaam.text = werkaanbieding.werkgever.naam
        opdrachtBeschrijving.text = werkaanbieding.omschrijving

        // TODO("Adres bijhouden in werkgever in backend en hier tonen")

        // TODO("Tags bijhouden in werkaanbieding in backend en hier tonen")
    }

    private fun showGeenAanbieding() {
        aanbiedingsInfo.visibility = View.GONE

    }

//    override fun likeClicked() {
//        // huidigeWerkaanbieding will never be null because buttons are hidden otherwise
//        leerling.bewaardeWerkaanbiedingen.add(werkaanbiedingFragment.werkaanbieding!!)
//        werkaanbiedingFragment.werkaanbieding = null
//        val toaster = Toast.makeText(activity, getString(R.string.werkaanbieding_bewaard), Toast.LENGTH_SHORT)
//        toaster.show()
//        getAndShowWerkaanbieding()
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Tab1Fragment.
         */
        @JvmStatic
        fun newInstance() = AanbiedingenFragment()
    }
}
