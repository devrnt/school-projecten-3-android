package com.talentcoach.id11.id11_android

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.talentcoach.id11.id11_android.models.Werkspreuk
import com.talentcoach.id11.id11_android.repositories.WerkspreukRepository
import kotlinx.android.synthetic.main.activity_communicatie.*

class CommunicatieActivity : AppCompatActivity() {

    private val werkspreukRepo = WerkspreukRepository()
    lateinit var werkspreuk: Werkspreuk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communicatie)

        addListeners()
    }

    fun addListeners() {
        btnWerkspreuk.setOnClickListener {
            showAlert()
        }
    }

    private fun showAlert() {
        val dialog = WerkspreukDialog()
        dialog.show(supportFragmentManager, "Dialog")
    }

}

class WerkspreukDialog : DialogFragment() {

    // replace week with code below
    // val week: Int = Calendar.WEEK_OF_MONTH;
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)


            builder.setMessage("Dit is een tijdelijke tekst")
                    .setNegativeButton("close",
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                            })


            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
