package com.talentcoach.id11.id11_android.werkaanbiedingen

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.constraint.ConstraintLayout
import android.support.design.button.MaterialButton
import android.support.design.card.MaterialCardView
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Leerling
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.repositories.LeerlingAPI
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v4.view.ViewCompat.setActivated
import android.support.v7.widget.CardView
import android.text.TextUtils
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import com.talentcoach.id11.id11_android.R.id.chipGroupBewaardeAanbieding
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Werkgever
import kotlinx.android.synthetic.main.fragment_aanbiedingen.*
import org.jetbrains.anko.uiThread


class BewaardeAanbiedingenFragment: Fragment() {

    private var leerlingId: Int? = null

    lateinit var leerling: Leerling

    var werkaanbiedingenList: MutableList<Werkaanbieding> = mutableListOf()
    var adapter: WerkaanbiedingenAdapter? = null
    var recyclerView: RecyclerView? = null

    lateinit var progressSpinner: ProgressBar
    lateinit var geenBewaardeAanbiedingenText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflates the RecyclerView with specified layout file
        val view = inflater.inflate(R.layout.fragment_bewaarde_aanbiedingen, container, false) as FrameLayout
        recyclerView = view.findViewById(R.id.werkaanbiedingenRecView) as RecyclerView

        progressSpinner = view.findViewById(R.id.progressSpinnerBA) as ProgressBar
        progressSpinner.visibility = View.VISIBLE
        geenBewaardeAanbiedingenText = view.findViewById(R.id.geenBewaardeAanbiedingenText) as TextView
        geenBewaardeAanbiedingenText.visibility = View.GONE

        doAsync {
            val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
            leerlingId = sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()
            // HIER NIET VOLLEDIGE LEERLING OPHALEN MAAR ENKEL AL ZIJN WERKAANBIEDINGEN
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
                        werkaanbiedingenList = leerling.bewaardeWerkaanbiedingen
//                        progressSpinner.visibility = View.GONE
                        progressSpinner.visibility = View.GONE
                        if (werkaanbiedingenList.size > 0) {
                            val adapter = BewaardeAanbiedingenFragment.WerkaanbiedingenAdapter(leerlingId!!, werkaanbiedingenList, leerling.interesses)
                            recyclerView?.adapter = adapter
                            recyclerView?.layoutManager = LinearLayoutManager(activity) // items get displayed in a vertical list
                        } else {
                            geenBewaardeAanbiedingenText.visibility = View.VISIBLE
                        }
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
//                Toast.makeText(activity, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG).show()
                val toast = Toast.makeText(activity, getString(R.string.something_went_wrong_login), Toast.LENGTH_LONG)
                val v = toast.getView().findViewById(android.R.id.message) as TextView
                if (v != null) v.gravity = Gravity.CENTER
                toast.show()
            }
        })

    }

    // Is called when user opens this tab again. When that happens we refresh the data so it's always up to date
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            doAsync {
                val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
                leerlingId = sharedPreferences.getString(getString(R.string.sp_key_leerling), "Default").toInt()
                // HIER NIET VOLLEDIGE LEERLING OPHALEN MAAR ENKEL AL ZIJN WERKAANBIEDINGEN
                getLeerling()
            }
        }
    }

    class WerkaanbiedingenAdapter(
            val leerlingId: Int,
            val werkaanbiedingen: MutableList<Werkaanbieding>,
            val interesses: List<String>
    ) : RecyclerView.Adapter<WerkaanbiedingenAdapter.AanbiedingenViewHolder>() {

        private var mExpandedPosition = -1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AanbiedingenViewHolder {
            // gets the ViewHolder, in this case a ConstraintLayout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_bewaarde_aanbieding_card, parent, false) as ConstraintLayout
            return AanbiedingenViewHolder(view)
        }

        override fun onBindViewHolder(holder: AanbiedingenViewHolder, pos: Int) {
            val werkaanbieding = werkaanbiedingen[pos]

            val werkgeverNaamOnCard = holder.constraintLayout.findViewById<TextView>(R.id.werkgeverNaamOnCard)
            werkgeverNaamOnCard.text = werkaanbieding.werkgever.naam
            val opdrachtBeschrijvingOnCard = holder.constraintLayout.findViewById<TextView>(R.id.opdrachtBeschrijvingOnCard)
            opdrachtBeschrijvingOnCard.text = werkaanbieding.omschrijving
            val adresOnCard = holder.constraintLayout.findViewById<TextView>(R.id.adresOnCard)
            adresOnCard.text = werkaanbieding.werkgever.werkplaats

            val isExpanded = pos == mExpandedPosition

            val details = holder.constraintLayout.findViewById<LinearLayout>(R.id.details)
            val beschrijvingHeader = holder.constraintLayout.findViewById<TextView>(R.id.opdrachtHeaderOnCard)
            val beschrijving = holder.constraintLayout.findViewById<TextView>(R.id.opdrachtBeschrijvingOnCard)

            details.visibility = if (isExpanded) View.VISIBLE else View.GONE
            beschrijvingHeader.visibility = if (isExpanded) View.VISIBLE else View.GONE
            beschrijving.maxLines = if (isExpanded) Integer.MAX_VALUE else 2
            beschrijving.ellipsize = if (isExpanded) null else TextUtils.TruncateAt.END

            val chipGroupBewaardeAanbieding = holder.constraintLayout.findViewById<ChipGroup>(R.id.chipGroupBewaardeAanbieding)
            chipGroupBewaardeAanbieding.removeAllViews()
            werkaanbieding.tags.forEach {
                var c = Chip(holder.constraintLayout.context)
                c.text = it
                c.isClickable = false
                c.setChipBackgroundColorResource(R.color.bg_chip_state_list)
                c.setTextAppearance(R.style.whiteTextSelected)
                c.isCheckable = true
                if (interesses.contains(it)) {
                    c.isChecked = true
                }
                chipGroupBewaardeAanbieding.addView(c)
            }

            val deleteBewaardeAanbiedingButton = holder.constraintLayout.findViewById<MaterialButton>(R.id.deleteBewaardeAanbiedingButton)
            deleteBewaardeAanbiedingButton.setOnClickListener {
                doAsync {
                    DataManager.undoWerkaanbieding(leerlingId, werkaanbieding.id)
                    uiThread {
                        werkaanbiedingen.removeAt(pos)
                        notifyDataSetChanged()
                    }
                }
            }

            holder.itemView.isActivated = isExpanded
            holder.itemView.setOnClickListener {
                mExpandedPosition = if (isExpanded) -1 else pos
//                TransitionManager.beginDelayedTransition(recyclerView)
                notifyDataSetChanged()
            }
        }

        override fun getItemCount(): Int {
            return werkaanbiedingen.size
        }

        class AanbiedingenViewHolder(val constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout)
    }

}