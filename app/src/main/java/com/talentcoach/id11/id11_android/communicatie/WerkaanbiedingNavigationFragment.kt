package com.talentcoach.id11.id11_android.communicatie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Richting
import kotlinx.android.synthetic.main.fragment_werkaanbieding_navigation.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Enables the user to view and react to a Werkaanbieding and to manage his/her Bewaarde Werkaanbiedingen
 * Implements IClickListener to listen to button clicks in its fragments
 * @property leerling The current logged in Leerling
 * @property werkaanbiedingenListFragment Fragment containing a RecyclerView to show Bewaarde Werkaanbiedingen
 * @property werkaanbiedingFragment Fragment that shows a Werkaanbieding (or default text if there is none to show)
 * @property werkaanbiedingButtonsFragment Fragment that shows a noLike and like button so the user can react to a werkaanbieding
 */
class WerkaanbiedingNavigationFragment : Fragment(), IClickListener {
    var leerling = Leerling(1, Richting(1, "", mutableListOf()), 0, "1993-07-05T00:00:00", "leerling@school.be", mutableListOf(),
            mutableListOf(), mutableListOf(), "Stroobants", "Bruno", mutableListOf(), null)
        private set
    // shown when user toggles to Bewaarde Werkaanbiedingen
    val werkaanbiedingenListFragment = WerkaanbiedingenListFragment() // shows Leerling.bewaardeWerkaanbiedingen

    // shown at the start
    // also shown when user wants to watch a Bewaarde Werkaanbieding in detail
    val werkaanbiedingFragment = WerkaanbiedingFragment()
    val werkaanbiedingButtonsFragment = WerkaanbiedingButtonsFragment()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_werkaanbieding_navigation, container, false)

        doAsync {
            if (leerling.id < 0) // check for a 'logged in' Leerling
                leerling = DataManager.getLeerlingById(1) // in future, id will come from a logged in User

            uiThread {
                // set fragment's IClickListener to this activity so it can listen to their button clicks
                werkaanbiedingFragment.iClickListener = this@WerkaanbiedingNavigationFragment
                werkaanbiedingenListFragment.iClickListener = this@WerkaanbiedingNavigationFragment
                werkaanbiedingButtonsFragment.iClickListener = this@WerkaanbiedingNavigationFragment

                // set the items within the RecyclerView's adapter to the leerling's Bewaarde Werkaanbiedingen
                werkaanbiedingenListFragment.werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen


                // add fragments to activity
                // onCreateView of each fragment gets called
                fragmentManager!!.beginTransaction()
                        .add(R.id.topFragmentFrame, werkaanbiedingFragment)
                        .hide(werkaanbiedingFragment)
                        .add(R.id.topFragmentFrame, werkaanbiedingenListFragment)
                        .hide(werkaanbiedingenListFragment)
                        .add(R.id.bottomFragmentFrame, werkaanbiedingButtonsFragment)
                        .hide(werkaanbiedingButtonsFragment)
                        .commit()

                // Activity starts with showing a Werkaanbieding, can be toggled between 2 fragments with toggleFragmentBtn
                getAndShowWerkaanbieding()

                // toggle between WerkaanbiedingFragment and WerkaanbiedingenListFragment
                toggleFragmentBtn.setOnClickListener {
                    if (toggleFragmentBtn.text == getString(R.string.mijn_werkaanb_button)) {
                        if (leerling.bewaardeWerkaanbiedingen.isEmpty()) {
                            val toast = Toast.makeText(activity, getString(R.string.geen_bew_werkaanb), Toast.LENGTH_LONG)
                            toast.show()
                        } else
                            showBewaardeWerkaanbiedingen()
                    } else {
                        getAndShowWerkaanbieding()
                    }
                }
            }
        }

        return view
    }

    private fun showWerkaanbieding(showButtonsFragment: Boolean = true) {
        toggleFragmentBtn.text = getString(R.string.mijn_werkaanb_button)
        val fragmentManager = fragmentManager!!.beginTransaction()
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
        fragmentManager!!.beginTransaction()
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
        val toaster = Toast.makeText(activity, getString(R.string.werkaanbieding_bewaard), Toast.LENGTH_SHORT)
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
            if (leerling.id < 0) // check for a 'logged in' Leerling
                leerling = DataManager.getLeerlingById(1) // in future, id will come from a logged in User
            werkaanbiedingFragment.werkaanbieding = DataManager.getWerkaanbiedingForLeerling(leerling)

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
        // resets leerling property so Leerling gets retrieved from the database when activity resumes
        leerling = Leerling(1, Richting(1, "", mutableListOf()), 0, "1993-07-05T00:00:00", "leerling@school.be", mutableListOf(),
                mutableListOf(), mutableListOf(), "Stroobants", "Bruno", mutableListOf(), null)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Tab1Fragment.
         */
        @JvmStatic
        fun newInstance() = WerkaanbiedingNavigationFragment()
    }

}
