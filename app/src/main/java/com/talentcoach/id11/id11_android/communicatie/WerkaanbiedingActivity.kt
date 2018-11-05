package com.talentcoach.id11.id11_android.communicatie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Leerling
import kotlinx.android.synthetic.main.activity_werkaanbieding.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class WerkaanbiedingActivity : AppCompatActivity(), IClickListener { // implements IClickListener to listen to button clicks in fragments
    var leerling = Leerling(-1, "default", mutableListOf(), mutableListOf())
        private set
    val werkaanbiedingenListFragment = WerkaanbiedingenListFragment() // Leerling.bewaardeWerkaanbiedingen
    val werkaanbiedingFragment = WerkaanbiedingFragment() // Leerling.huidigeWerkaanbieding
    val werkaanbiedingButtonsFragment = WerkaanbiedingButtonsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_werkaanbieding)

        doAsync {
            if (leerling.id < 0) // check for a 'logged in' Leerling
                leerling = DataManager.getLeerlingById(1) // in future, id will come from a logged in User

            uiThread {
                // set fragment's IClickListener to this activity so it can listen to their button clicks
                werkaanbiedingFragment.iClickListener = this@WerkaanbiedingActivity
                werkaanbiedingenListFragment.iClickListener = this@WerkaanbiedingActivity
                werkaanbiedingButtonsFragment.iClickListener = this@WerkaanbiedingActivity

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
                        if (leerling.bewaardeWerkaanbiedingen.isEmpty()) {
                            val toast = Toast.makeText(this@WerkaanbiedingActivity, getString(R.string.geen_bew_werkaanb), Toast.LENGTH_LONG)
                            toast.show()
                        } else
                            showBewaardeWerkaanbiedingen()
                    } else {
                        getAndShowWerkaanbieding()
                    }
                }
            }
        }


    }

    private fun showWerkaanbieding(showButtonsFragment: Boolean = true) {
        toggleFragmentBtn.text = getString(R.string.mijn_werkaanb_button)
        val fragmentManager = supportFragmentManager.beginTransaction()
                .hide(werkaanbiedingenListFragment)
                .show(werkaanbiedingFragment)
        if (showButtonsFragment)
            fragmentManager.show(werkaanbiedingButtonsFragment)
        else
            fragmentManager.hide(werkaanbiedingButtonsFragment)
        fragmentManager.commit()
    }

    private fun showBewaardeWerkaanbiedingen() {
        werkaanbiedingenListFragment.werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen
        toggleFragmentBtn.text = getString(R.string.bekijk_werkaanb_btn)
        supportFragmentManager.beginTransaction()
                .hide(werkaanbiedingFragment)
                .hide(werkaanbiedingButtonsFragment)
                .show(werkaanbiedingenListFragment)
                .commit()
    }


    override fun noLikeClicked() {
        // huidigeWerkaanbieding will never be null because buttons are hidden otherwise
        leerling.verwijderdeWerkaanbiedingen.add(werkaanbiedingFragment.werkaanbieding!!)
        werkaanbiedingFragment.werkaanbieding = null
        getAndShowWerkaanbieding()
    }

    override fun likeClicked() {
        // huidigeWerkaanbieding will never be null because buttons are hidden otherwise
        leerling.bewaardeWerkaanbiedingen.add(werkaanbiedingFragment.werkaanbieding!!)
        werkaanbiedingFragment.werkaanbieding = null
        val toaster = Toast.makeText(this, getString(R.string.werkaanbieding_bewaard), Toast.LENGTH_SHORT)
        toaster.show()
        getAndShowWerkaanbieding()
    }

    override fun removeClicked(pos: Int) {
        var index = pos

        if (pos < 0) {
            index = leerling.bewaardeWerkaanbiedingen.indexOfFirst { bw -> bw.id == werkaanbiedingFragment.werkaanbieding?.id }
        }
        leerling.verwijderdeWerkaanbiedingen.add(leerling.bewaardeWerkaanbiedingen.removeAt(index))
        showBewaardeWerkaanbiedingen()
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

            werkaanbiedingFragment.werkaanbieding = DataManager.getWerkaanbiedingVoorLeerling(leerling)

            uiThread {
                progress.visibility = View.GONE // verbergt progressbar
                werkaanbiedingButtonsFragment.onlyRemove = false
                if (werkaanbiedingFragment.werkaanbieding == null) {
                    werkaanbiedingFragment.noWerkaanbiedingFound = true
                    showWerkaanbieding(false)
                } else {
                    showWerkaanbieding()
                }

            }
        }
    }

    override fun onPause() {
        super.onPause()
        doAsync {
            DataManager.update(leerling) // persists Leerling
        }
        leerling = Leerling(-1, "default", mutableListOf(), mutableListOf())
    }


}
