package com.talentcoach.id11.id11_android.communicatie


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import kotlinx.android.synthetic.main.fragment_werkaanbieding.*

/**
 * Deel van *communicatie*.
 * Fragment that shows a given Werkaanbieding or default text when there is no Werkaanbieding to show
 * @property The activity that listens to this fragment
 * @property werkaanbieding The werkaanbieding that needs to be shown
 * @property noWerkaanbiedingFound true if there is a werkaanbieding to be shown, otherwise false
 */
class WerkaanbiedingFragment : Fragment() {
    var iClickListener: IClickListener? = null // will be set to WerkaanbiedingNavigationFragment


    var werkaanbieding: Werkaanbieding? = null
        set(value) {
            field = value
            showWerkaanbieding()
        }
    var noWerkaanbiedingFound = false
        set(value) {
            field = value
            showWerkaanbieding()
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_werkaanbieding, container, false)
    }

    override fun onResume() {
        super.onResume()
        showWerkaanbieding()
    }

    private fun showWerkaanbieding() {
        activity?.runOnUiThread {
            if (!noWerkaanbiedingFound) { // set text
                werkgever?.text = getString(R.string.wa_werkgever, werkaanbieding?.werkgever?.naam
                        ?: "")
                omschrijving?.text = getString(R.string.wa_beschrijving, werkaanbieding?.omschrijving
                        ?: "")
            } else { // hide buttons and show default text when no Werkaanbieding is found
                werkgever?.text = getString(R.string.no_werkaanbieding_1)
                omschrijving?.text = getString(R.string.no_werkaanbieding_2)
            }
        }
    }
}
