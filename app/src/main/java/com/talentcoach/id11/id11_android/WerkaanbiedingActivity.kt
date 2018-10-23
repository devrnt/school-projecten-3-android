package com.talentcoach.id11.id11_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingRepository
import com.talentcoach.id11.id11_android.repositories.WerkaanbiedingRepository
import kotlinx.android.synthetic.main.activity_werkaanbieding.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class WerkaanbiedingActivity : AppCompatActivity(), IClickListener { // implements IClickListener to listen to button clicks in fragments

    private var leerling = Leerling(-1, "default")
    val werkaanbiedingenListFragment = WerkaanbiedingenListFragment() // Leerling.bewaardeWerkaanbiedingen
    val werkaanbiedingFragment = WerkaanbiedingFragment() // Leerling.huidigeWerkaanbieding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_werkaanbieding)

        getWerkaanbieding() // Activity starts with showing a Werkaanbieding

        // set fragment's IClickListener to this activity so it can listen to their button clicks
        werkaanbiedingFragment.iClickListener = this
        werkaanbiedingenListFragment.iClickListener = this

        // adds werkaanbiedingFragment to this activity
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentFrame, werkaanbiedingFragment)
                .commit()

        // set onClick for toggleFragmentButton
        toggleFragmentBtn.setOnClickListener {// toggle between WerkaanbiedingFragment and WerkaanbiedingenListFragment
            if (toggleFragmentBtn.text == getString(R.string.mijn_werkaanb_button)) { // show WerkaanbiedingenListFragment
                toggleFragmentBtn.text = getString(R.string.bekijk_werkaanb_btn)
                werkaanbiedingenListFragment.werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen
                // remove fragment with Leerling.huidigeWerkaanbieding and show fragment with Leerling.bewaardeWerkaanbiedingen
                supportFragmentManager.beginTransaction()
                        .remove(werkaanbiedingFragment)
                        .add(R.id.fragmentFrame, werkaanbiedingenListFragment)
                        .commit()
                werkaanbiedingenListFragment.werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen
            } else { // remove fragment with Leerling.bewaardeWerkaanbiedingen and show fragment with Leerling.huidigeWerkaanbieding
                toggleFragmentBtn.text = getString(R.string.mijn_werkaanb_button)
                supportFragmentManager.beginTransaction()
                        .remove(werkaanbiedingenListFragment)
                        .add(R.id.fragmentFrame, werkaanbiedingFragment)
                        .commit()
            }
        }
    }


    override fun noLikeClicked() {
        // huidigeWerkaanbieding will never be null because buttons are hidden otherwise
        leerling.verwijderdeWerkaanbiedingen.add(leerling.huidigeWerkaanbieding!!)
        leerling.huidigeWerkaanbieding = null
        getWerkaanbieding()
    }

    override fun likeClicked() {
        // huidigeWerkaanbieding will never be null because buttons are hidden otherwise
        leerling.bewaardeWerkaanbiedingen.add(leerling.huidigeWerkaanbieding!!)
        leerling.huidigeWerkaanbieding = null
        val toaster = Toast.makeText(this, getString(R.string.werkaanbieding_bewaard), Toast.LENGTH_SHORT)
        toaster.show()
        getWerkaanbieding()
    }

    override fun removeClicked(pos: Int) {
        val wa = leerling.bewaardeWerkaanbiedingen.removeAt(pos)
        leerling.verwijderdeWerkaanbiedingen.add(wa)
    }

    private fun getWerkaanbieding() {
        progress.visibility = View.VISIBLE // show progressbar
        doAsync {
            if (leerling.id < 0) // check for a 'logged in' Leerling
                leerling = LeerlingRepository().getLeerlingById(1L) // in future, id will come from a logged in User

            if (leerling.huidigeWerkaanbieding == null) {
                leerling.huidigeWerkaanbieding = WerkaanbiedingRepository().getWerkaanbiedingVoorLeerling(leerling)
            }
            uiThread {
                progress.visibility = View.GONE // verbergt progressbar
                werkaanbiedingFragment.werkaanbieding = leerling.huidigeWerkaanbieding
                if (werkaanbiedingFragment.werkaanbieding == null) {
                    werkaanbiedingFragment.noWerkaanbiedingFound = true
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        doAsync {
            LeerlingRepository().saveLeerling(leerling) // persists Leerling
        }
    }

}
