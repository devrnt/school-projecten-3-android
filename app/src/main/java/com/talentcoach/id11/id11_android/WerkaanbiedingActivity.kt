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
import java.lang.Exception

class WerkaanbiedingActivity : AppCompatActivity() {
    private var leerling = Leerling(-1, "default")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_werkaanbieding)

        showWerkaanbieding()
        initButtons()
    }

    private fun initButtons() {
        like.setOnClickListener {
            leerling.bewaardeWerkaanbieding.add(leerling.huidigeWerkaanbieding!!)
            leerling.huidigeWerkaanbieding = null
            showWerkaanbieding()
        }

        noLike.setOnClickListener {
            leerling.verwijderdeWerkaanbiedingen.add(leerling.huidigeWerkaanbieding!!)
            leerling.huidigeWerkaanbieding = null
            showWerkaanbieding()
        }
    }

    private fun showWerkaanbieding() {
        try {
            werkgever.text = ""
            omschrijving.text = ""
            progress.visibility = View.VISIBLE

            doAsync {
                if (leerling.id < 0) // check of er een 'ingelogde' leerling is
                    leerling = LeerlingRepository().getLeerlingById(1L)

                if (leerling.huidigeWerkaanbieding == null) { // check of er al een huidigeWerkaanbieding wordt getoond voor de leerling
                    leerling.huidigeWerkaanbieding = WerkaanbiedingRepository().getWerkaanbiedingVoorLeerling(leerling)
                }
                uiThread {
                    progress.visibility = View.GONE // verbergt progressbar
                    val wa = leerling.huidigeWerkaanbieding

                    if (wa != null) { // stelt tekst in
                        werkgever.text = getString(R.string.wa_werkgever, wa.werkgever.naam)
                        omschrijving.text = getString(R.string.wa_beschrijving, wa.omschrijving)
                    } else {
                        werkgever.text = getString(R.string.no_werkaanbieding)
                        omschrijving.text = getString(R.string.modify_interesses)
                        noLike.visibility = View.INVISIBLE
                        like.visibility = View.INVISIBLE
                    }

                }
            }

        } catch (e: Exception) { // eventuele exceptions tonen
            val toast = Toast.makeText(this, e.message?.substring(0, 20), Toast.LENGTH_LONG)
            toast.show()
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            doAsync {
                LeerlingRepository().saveLeerling(leerling) // als de activity zijn focus verliest dan wordt de leerling geüpdatet in de databank
            }
        } catch (e: Exception) {
            val toast = Toast.makeText(this@WerkaanbiedingActivity, e.message?.subSequence(0, 20), Toast.LENGTH_LONG)
            toast.show()
        }
    }

}
