package com.talentcoach.id11.id11_android.communicatie

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.models.Werkspreuk
import com.talentcoach.id11.id11_android.repositories.WerkspreukRepository
import kotlinx.android.synthetic.main.activity_communicatie.*
import org.jetbrains.anko.toast

/**
 * Deel van *communicatie*.
 *
 * Lets a user launch the activities concering "Communicatie"
 * @property timesRequested records the amount of requests
 * @property werkspreukRepo houdt de repository die de werkspreuken fetched
 * @property werkspreuk de huidge werkspreuk
 */
class CommunicatieActivity : AppCompatActivity() {
    private var timesRequested: Int = 0
    private val werkspreukRepo = WerkspreukRepository()
    var werkspreuk = Werkspreuk(-1, -1, "default")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communicatie)
        addListeners()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun handleExeception(e: Throwable) {
        print("Is not workinghow it should atm")
        toast(e.message.toString())
    }

    fun addListeners() {
        // add temp listeners
        btnWerkaanbieding.setOnClickListener {
            val intent = Intent(this, WerkaanbiedingNavigationFragment::class.java)
            startActivity(intent)
        }
    }
}

