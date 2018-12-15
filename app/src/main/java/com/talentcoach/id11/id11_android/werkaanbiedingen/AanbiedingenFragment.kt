package com.talentcoach.id11.id11_android.werkaanbiedingen


import android.os.Bundle
import android.support.design.button.MaterialButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import kotlinx.android.synthetic.main.fragment_aanbiedingen.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.design.chip.Chip
import android.support.design.chip.ChipDrawable
import android.support.design.chip.ChipGroup
import android.widget.*


/**
 * A simple [Fragment] subclass.
 *
 */
class AanbiedingenFragment : Fragment() {


    private var leerlingId: Int? = null

    lateinit var leerling: Leerling
    lateinit var werkaanbieding: Werkaanbieding

    lateinit var progressSpinner: ProgressBar
    lateinit var aanbiedingsInfo: LinearLayout
    lateinit var geenAanbiedingenText: TextView
    lateinit var dislikeBtn: MaterialButton
    lateinit var likeBtn: MaterialButton
    lateinit var werkgeverNaam: TextView
    lateinit var opdrachtBeschrijving: TextView
    lateinit var werkgeverAddress: TextView
    lateinit var chipGroupAanbieding: ChipGroup

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_aanbiedingen, container, false)
        aanbiedingsInfo = view.findViewById(R.id.aanbiedingsInfo) as LinearLayout
        aanbiedingsInfo.visibility = View.GONE
        progressSpinner = view.findViewById(R.id.progressSpinner) as ProgressBar
        progressSpinner.visibility = View.VISIBLE
        geenAanbiedingenText = view.findViewById(R.id.geenAanbiedingenText) as TextView
        geenAanbiedingenText.visibility = View.GONE

        werkgeverNaam = view.findViewById(R.id.werkgeverNaam)
        opdrachtBeschrijving = view.findViewById(R.id.opdrachtBeschrijving)
        werkgeverAddress = view.findViewById(R.id.werkgeverAddress)
        chipGroupAanbieding = view.findViewById(R.id.chipGroupAanbieding)

        dislikeBtn = view.findViewById(R.id.dislikeBtn) as MaterialButton
        dislikeBtn.setOnClickListener(onClickListener)
        dislikeBtn.isEnabled = false
        likeBtn = view.findViewById(R.id.likeBtn) as MaterialButton
        likeBtn.setOnClickListener(onClickListener)
        likeBtn.isEnabled = false

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
        aanbiedingsInfo.visibility = View.GONE
        progressSpinner.visibility = View.VISIBLE
        dislikeBtn.isEnabled = false
        likeBtn.isEnabled = false

        doAsync {
            var temp = DataManager.getInteressantsteWerkaanbieding(leerlingId!!)
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
        geenAanbiedingenText.visibility = View.GONE
        dislikeBtn.isEnabled = true
        likeBtn.isEnabled = true

        werkgeverNaam.text = werkaanbieding.werkgever.naam
        opdrachtBeschrijving.text = werkaanbieding.omschrijving
        werkgeverAddress.text = werkaanbieding.werkgever.werkplaats

        chipGroupAanbieding.removeAllViews()
        werkaanbieding.tags.forEach {
            var c = Chip(context)
            c.text = it
            c.isClickable = false
            c.setChipBackgroundColorResource(R.color.bg_chip_state_list)
            c.setTextAppearance(R.style.whiteTextSelected)
            c.isCheckable = true
            if (leerling.interesses.contains(it)) {
                c.isChecked = true
            }
            chipGroupAanbieding.addView(c)
        }
    }

    private fun showGeenAanbieding() {
        progressSpinner.visibility = View.GONE
        geenAanbiedingenText.visibility = View.VISIBLE
    }

    private val onClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.dislikeBtn -> {
                doAsync {
                    DataManager.dislikeWerkaanbieding(leerlingId!!, werkaanbieding.id)
                    uiThread {
                        getWerkaanbieding()
                    }
                }
            }
            R.id.likeBtn -> {
                doAsync {
                    DataManager.likeWerkaanbieding(leerlingId!!, werkaanbieding.id)
                    uiThread {
                        getWerkaanbieding()
                    }
                }
            }
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
        fun newInstance() = AanbiedingenFragment()
    }
}
