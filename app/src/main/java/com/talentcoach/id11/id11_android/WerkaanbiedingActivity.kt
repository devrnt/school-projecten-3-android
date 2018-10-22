package com.talentcoach.id11.id11_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.talentcoach.id11.id11_android.WerkaanbiedingFragment.ReactInterface
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.repositories.LeerlingRepository
import com.talentcoach.id11.id11_android.repositories.WerkaanbiedingRepository
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_werkaanbieding.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class WerkaanbiedingActivity : AppCompatActivity(), ReactInterface {
    private var leerling = Leerling(-1, "default")
    val werkaanbiedingenListFragment = WerkaanbiedingenListFragment()
    val werkaanbiedingFragment = WerkaanbiedingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_werkaanbieding)

        getWerkaanbieding() // Activity starts with showing a Werkaanbieding
        werkaanbiedingFragment.reactInterface = this
        werkaanbiedingenListFragment.reactInterface = this
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentFrame, werkaanbiedingFragment)
                .commit()

        toggleFragmentBtn.setOnClickListener {// toggle between WerkaanbiedingFragment and WerkaanbiedingenListFragment
            if (toggleFragmentBtn.text == getString(R.string.mijn_werkaanb_button)) { // show WerkaanbiedingenListFragment
                toggleFragmentBtn.text = getString(R.string.bekijk_werkaanb_btn)
                werkaanbiedingenListFragment.werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen
                supportFragmentManager.beginTransaction()
                        .remove(werkaanbiedingFragment)
                        .add(R.id.fragmentFrame, werkaanbiedingenListFragment)
                        .commit()
                werkaanbiedingenListFragment.werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen
            } else { // show WerkaanbiedingFragment
                toggleFragmentBtn.text = getString(R.string.mijn_werkaanb_button)
                supportFragmentManager.beginTransaction()
                        .remove(werkaanbiedingenListFragment)
                        .add(R.id.fragmentFrame, werkaanbiedingFragment)
                        .commit()
            }
        }
    }


    override fun noLikeClicked() {
        leerling.verwijderdeWerkaanbiedingen.add(leerling.huidigeWerkaanbieding!!)
        leerling.huidigeWerkaanbieding = null
        getWerkaanbieding()
    }

    override fun likeClicked() {
        leerling.bewaardeWerkaanbiedingen.add(leerling.huidigeWerkaanbieding!!)
        leerling.huidigeWerkaanbieding = null
        val toaster = Toast.makeText(this, getString(R.string.werkaanbieding_bewaard), Toast.LENGTH_SHORT)
        toaster.show()
        getWerkaanbieding()
    }

    private fun getWerkaanbieding() {
        progress.visibility = View.VISIBLE // show progressbar
        doAsync {
            if (leerling.id < 0) // check of er een 'ingelogde' leerling is
                leerling = LeerlingRepository().getLeerlingById(1L)

            if (leerling.huidigeWerkaanbieding == null) { // check of er al een huidigeWerkaanbieding wordt getoond voor de leerling
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
