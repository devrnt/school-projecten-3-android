package com.talentcoach.id11.id11_android.job

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.AlgemeneInfo
import com.talentcoach.id11.id11_android.models.Werkaanbieding
import com.talentcoach.id11.id11_android.werkaanbiedingen.BewaardeAanbiedingenFragment
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
/**
 * Deel van *job*.
 *
 * Fragment dat verantwoordelijk is voor het tonen van bewaarde werkaanbiedingen
 * @property leerlingId het id van de leerling op basis waarvan de bewaarde jobs worden opgehaald
 * @property werkgever het id van de werkgever waarvan de informatie weergegeven wordt
 */
class AlgemeneInfoFragment : Fragment() {

    lateinit var headerList: List<String>
    lateinit var bodyList: List<String>

    var recyclerView: RecyclerView? = null
    lateinit var progressSpinner: ProgressBar

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_algemene_info, container, false)

        recyclerView = view.findViewById(R.id.algemeneInfoRecView) as RecyclerView
        progressSpinner = view.findViewById(R.id.progressSpinnerAI) as ProgressBar
        progressSpinner.visibility = View.VISIBLE

        doAsync {
            val algemeneInfo = DataManager.algemeneInfoRepository.getAll()
            uiThread {
                progressSpinner.visibility = View.GONE
                val adapter = AlgemeneInfoFragment.AlgemeneInfoAdapter(algemeneInfo)
                recyclerView?.adapter = adapter
                recyclerView?.layoutManager = LinearLayoutManager(activity)
            }

            headerList = algemeneInfo.map { ai -> ai.titel }.toList()
            bodyList = algemeneInfo.map { ai -> ai.omschrijving }.toList()
        }

        return view
    }

    class AlgemeneInfoAdapter(
            val algemeneInfo: List<AlgemeneInfo>
    ) : RecyclerView.Adapter<AlgemeneInfoAdapter.AlgemeneInfoViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlgemeneInfoViewHolder {
            // gets the ViewHolder, in this case a ConstraintLayout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_algemene_info_card, parent, false) as ConstraintLayout
            return AlgemeneInfoViewHolder(view)
        }

        override fun onBindViewHolder(holder: AlgemeneInfoViewHolder, pos: Int) {
            val info = algemeneInfo[pos]
            val infoHeader = info.titel
            val infoBody = info.omschrijving

            var infoHeaderOnCard = holder.constraintLayout.findViewById<TextView>(R.id.infoHeaderOnCard)
            infoHeaderOnCard.text = infoHeader

            var infoBodyOnCard = holder.constraintLayout.findViewById<TextView>(R.id.infoBodyOnCard)
            infoBodyOnCard.text = infoBody
        }

        override fun getItemCount(): Int {
            return algemeneInfo.size
        }

        class AlgemeneInfoViewHolder(val constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout)
    }
}