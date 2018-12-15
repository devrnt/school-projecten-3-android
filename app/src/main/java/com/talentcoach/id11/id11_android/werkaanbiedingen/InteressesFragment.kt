package com.talentcoach.id11.id11_android.werkaanbiedingen


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast

import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 *
 */
class InteressesFragment : Fragment() {

    private var leerlingId: Int? = null
    lateinit var leerling: Leerling
    lateinit var interesses: List<String>
    lateinit var chipGroup: ChipGroup
    lateinit var progressSpinner: ProgressBar

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_interesses, container, false)
        chipGroup = view.findViewById(R.id.chipGroup) as ChipGroup
        progressSpinner = view.findViewById(R.id.progressSpinnerInteresses) as ProgressBar
        progressSpinner.visibility = View.VISIBLE

        doAsync {
            val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
            leerlingId = sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()
            // TODO("HIER NIET VOLLEDIGE LEERLING OPHALEN MAAR ENKEL ZIJN INTERESSES")
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
                        getAndShowInteresses()
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

    private fun getAndShowInteresses() {
        doAsync {
            interesses = DataManager.getAllTags()
            uiThread {
                progressSpinner.visibility = View.GONE
                chipGroup.removeAllViews()
                for (index in interesses) {
                    val chip = Chip(chipGroup.context)
                    chip.text = index
                    chip.setChipBackgroundColorResource(R.color.bg_chip_state_list)
                    chip.setTextAppearance(R.style.whiteTextSelected)
                    chip.isClickable = true
                    chip.isCheckable = true
                    if (leerling.interesses.contains(index)) {
                        chip.isChecked = true
                    }
                    chip.setOnClickListener {

                    }
                    chipGroup.addView(chip)
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
        fun newInstance() = InteressesFragment()
    }

}
