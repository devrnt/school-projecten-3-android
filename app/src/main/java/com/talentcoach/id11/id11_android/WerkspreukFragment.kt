package com.talentcoach.id11.id11_android

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.talentcoach.id11.id11_android.models.Werkspreuk
import com.talentcoach.id11.id11_android.repositories.WerkspreukRepository
import com.talentcoach.id11.id11_android.repositories.responses.LoginResponse
import org.jetbrains.anko.doAsync
import java.util.*

class WerkspreukFragment : Fragment() {
    private val werkspreukRepository = WerkspreukRepository()
    private var url: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_werkspreuk, container, false)

        val backgroundImg = view.findViewById<ImageView>(R.id.werkspreukBgImg)
        val title = view.findViewById<TextView>(R.id.werkspreukTitle)
        val werkspreuk = view.findViewById<TextView>(R.id.werkspreuk)
        val spinner = view.findViewById<ProgressBar>(R.id.werkspreukSpinner)

        var fetchedWerkspreuk: Werkspreuk? = null
        url = getRandomImage()

        spinner.visibility = View.VISIBLE

        // Card image
        Picasso.get()
                .load(url)
                .into(backgroundImg)


        doAsync {
            title.text = "${getWelcomeWord()} ${getUserFirstname()}"
            fetchedWerkspreuk = werkspreukRepository.getWerkspreuk(1)

            activity?.runOnUiThread {
                spinner.visibility = View.GONE
                werkspreuk.text = fetchedWerkspreuk!!.body
            }

        }


        // Inflate the layout for this fragment
        return view
    }

    private fun getRandomImage(): String {
        var urls: MutableList<String> = ArrayList()
        urls.add("https://images.unsplash.com/photo-1541103566371-1a77ffd62e05?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=c3915520661333b5b5bc097686629433&auto=format&fit=crop&w=1050&q=80")
        urls.add("https://images.unsplash.com/photo-1541103554737-fe33e243b45c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=5070c2f1196983d1b380bee6b3315c83&auto=format&fit=crop&w=1050&q=80")
        urls.add("https://images.unsplash.com/photo-1541093680452-bc6fa4b2a187?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=e33e8ae6f97fa843134db4432cdcac15&auto=format&fit=crop&w=334&q=80")
        urls.add("https://images.unsplash.com/photo-1541099998360-da88f154be08?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=6c8fd51a5ffc1e2fb67640e50cf7f267&auto=format&fit=crop&w=1050&q=80")
        urls.add("https://images.unsplash.com/photo-1541069844610-70eda7ae4c82?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=b0a901eaf6226a7cbf1586fffe3d6a40&auto=format&fit=crop&w=675&q=80")
        return urls.shuffled().first()
    }

    private fun getWelcomeWord(): String {
        val rightNow = Calendar.getInstance()
        val currentHour = rightNow.get(Calendar.HOUR_OF_DAY)
        return when (currentHour) {
            in 0..11 -> "Goedemorgen"
            in 12..17 -> "Goedemiddag"
            else -> "Goedenavond"
        }
    }

    private fun getUserFirstname(): String {
        // get the logged in user
        val gson = Gson()
        val jsonGebruiker = sharedPreferences.getString(getString(R.string.login_login_response), "Geen ingelogde gebruiker")
        val user = gson.fromJson(jsonGebruiker, LoginResponse::class.java)

        return user.voornaam
    }

}
