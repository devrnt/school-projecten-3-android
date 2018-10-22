package com.talentcoach.id11.id11_android


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import kotlinx.android.synthetic.main.fragment_werkaanbieding.*

class WerkaanbiedingFragment : Fragment() {
    var reactInterface: ReactInterface? = null


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

    private fun showWerkaanbieding() {
        if (!noWerkaanbiedingFound) { // stelt tekst in
            werkgever.text = getString(R.string.wa_werkgever, werkaanbieding?.werkgever?.naam ?: "")
            omschrijving.text = getString(R.string.wa_beschrijving, werkaanbieding?.omschrijving ?: "")
        } else {
            werkgever.text = getString(R.string.no_werkaanbieding)
            omschrijving.text = ""
            like.visibility = View.GONE
            noLike.visibility = View.GONE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_werkaanbieding, container, false)
    }

    override fun onResume() {
        super.onResume()
        like.setOnClickListener {
            reactInterface?.likeClicked()
        }

        noLike.setOnClickListener {
            reactInterface?.noLikeClicked()
        }
        showWerkaanbieding()
    }

    interface ReactInterface {
        fun likeClicked()
        fun noLikeClicked()
    }
}
