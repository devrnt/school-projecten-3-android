package com.talentcoach.id11.id11_android


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import kotlinx.android.synthetic.main.activity_werkaanbieding.*
import kotlinx.android.synthetic.main.fragment_werkaanbieding.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class WerkaanbiedingFragment : Fragment() {
    var werkaanbieding: Werkaanbieding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_werkaanbieding, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (werkaanbieding != null) { // stelt tekst in
            werkgever.text = getString(R.string.wa_werkgever, werkaanbieding?.werkgever?.naam)
            omschrijving.text = getString(R.string.wa_beschrijving, werkaanbieding?.omschrijving)
        } else {
            werkgever.text = getString(R.string.no_werkaanbieding)
            omschrijving.text = getString(R.string.modify_interesses)
        }
    }

}
