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
    val werkaanbiedingButtonsFragment = WerkaanbiedingButtonsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_werkaanbieding)

        // set fragment's IClickListener to this activity so it can listen to their button clicks
        werkaanbiedingFragment.iClickListener = this
        werkaanbiedingenListFragment.iClickListener = this
        werkaanbiedingButtonsFragment.iClickListener = this

        werkaanbiedingenListFragment.werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen


        // add fragments to activity
        supportFragmentManager.beginTransaction()
                .add(R.id.topFragmentFrame, werkaanbiedingFragment)
                .hide(werkaanbiedingFragment)
                .add(R.id.topFragmentFrame, werkaanbiedingenListFragment)
                .hide(werkaanbiedingenListFragment)
                .add(R.id.bottomFragmentFrame, werkaanbiedingButtonsFragment)
                .hide(werkaanbiedingButtonsFragment)
                .commit()

        // Activity starts with showing a Werkaanbieding, can be toggled between 2 fragments with Button
        getAndShowWerkaanbieding()

        // toggle between WerkaanbiedingFragment and WerkaanbiedingenListFragment
        toggleFragmentBtn.setOnClickListener {
            if (toggleFragmentBtn.text == getString(R.string.mijn_werkaanb_button)) {
                showBewaardeWerkaanbiedingen()
            } else {
                showWerkaanbieding()
            }
        }
    }

    private fun showWerkaanbieding() {
        toggleFragmentBtn.text = getString(R.string.mijn_werkaanb_button)
        supportFragmentManager.beginTransaction()
                .hide(werkaanbiedingenListFragment)
                .show(werkaanbiedingFragment)
                .show(werkaanbiedingButtonsFragment)
                .addToBackStack(null)
                .commit()
    }

    private fun showBewaardeWerkaanbiedingen() {
        println(leerling.bewaardeWerkaanbiedingen)
        werkaanbiedingenListFragment.werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen
        werkaanbiedingenListFragment.adapter?.notifyDataSetChanged()
        toggleFragmentBtn.text = getString(R.string.bekijk_werkaanb_btn)
        supportFragmentManager.beginTransaction()
                .hide(werkaanbiedingFragment)
                .hide(werkaanbiedingButtonsFragment)
                .show(werkaanbiedingenListFragment)
                .addToBackStack(null)
                .commit()
    }


    override fun noLikeClicked() {
        // huidigeWerkaanbieding will never be null because buttons are hidden otherwise
        leerling.verwijderdeWerkaanbiedingen.add(leerling.huidigeWerkaanbieding!!)
        leerling.huidigeWerkaanbieding = null
        getAndShowWerkaanbieding()
    }

    override fun likeClicked() {
        // huidigeWerkaanbieding will never be null because buttons are hidden otherwise
        leerling.bewaardeWerkaanbiedingen.add(leerling.huidigeWerkaanbieding!!)
        leerling.huidigeWerkaanbieding = null
        val toaster = Toast.makeText(this, getString(R.string.werkaanbieding_bewaard), Toast.LENGTH_SHORT)
        toaster.show()
        getAndShowWerkaanbieding()
    }

    override fun removeClicked(pos: Int) {
        val wa = leerling.bewaardeWerkaanbiedingen.removeAt(pos)
        leerling.verwijderdeWerkaanbiedingen.add(wa)
    }

    override fun itemClicked(pos: Int) {
        werkaanbiedingButtonsFragment.onlyRemove = true
        werkaanbiedingFragment.noWerkaanbiedingFound = false
        werkaanbiedingFragment.werkaanbieding = leerling.bewaardeWerkaanbiedingen[pos]
        showWerkaanbieding()
    }

    private fun getAndShowWerkaanbieding() {
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
                showWerkaanbieding()
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
