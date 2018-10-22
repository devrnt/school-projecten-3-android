package com.talentcoach.id11.id11_android

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.talentcoach.id11.id11_android.models.Werkspreuk
import com.talentcoach.id11.id11_android.repositories.WerkspreukRepository
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.action_werkspreuk -> {
            timesRequested++

            if (timesRequested == 1) {
                toast(getString(R.string.werkspreuk_loading_message))
            }
            doAsync(exceptionHandler = { e -> handleExeception(e) }) {
                // replace week with code below
                // val week: Int = Calendar.WEEK_OF_MONTH;

                werkspreuk = werkspreukRepo.getWerkspreuk(1)

                uiThread {
                    showAlert()
                }

            }

            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun handleExeception(e: Throwable) {
        print("Is not workinghow it should atm")
        toast(e.message.toString())
    }

    fun addListeners() {
        // add temp listeners
    }

    private fun showAlert() {
        val dialog = WerkspreukDialog.newInstance(werkspreuk)
        dialog.show(supportFragmentManager, "Dialog")
    }

}

class WerkspreukDialog : DialogFragment() {
    companion object {
        private const val KEY = "werkspreukTxt"
        fun newInstance(werkspreuk: Werkspreuk): WerkspreukDialog {
            val dialog = WerkspreukDialog()
            val args = Bundle().apply {
                putString(KEY, werkspreuk.body)
            }
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)

            builder.setMessage(arguments!!.getString(KEY))
                    .setNegativeButton(getString(R.string.close)
                    ) { dialog, id ->
                        // User cancelled the dialog
                    }


            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
