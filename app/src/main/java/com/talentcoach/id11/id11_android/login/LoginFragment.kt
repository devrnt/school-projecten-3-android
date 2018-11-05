package com.talentcoach.id11.id11_android.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talentcoach.id11.id11_android.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.login_btn.setOnClickListener {
            if (!validGebruikersnaam(gebruikersNaam_input.text)) {
                gebruikersNaam_inputLayout.error = getString(R.string.error_username)
            } else {
                gebruikersNaam_inputLayout.error = null
            }

            if (!validWachtwoord(wachtwoord_input.text)) {
                wachtwoord_inputLayout.error = getString(R.string.error_password)
            } else {
                wachtwoord_inputLayout.error = null
                tryLogin()
            }
        }

        // Clear the error once more than 8 characters are typed
        view.wachtwoord_input.setOnKeyListener { _, _, _ ->
            if (validWachtwoord(wachtwoord_input.text)) {
                wachtwoord_input.error = null
            }
            false
        }

        return view
    }

    private fun validGebruikersnaam(text: Editable?): Boolean {
        return text == null
    }

    private fun validWachtwoord(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }


    private fun tryLogin() {
        // Check backend and add error msg
    }

}