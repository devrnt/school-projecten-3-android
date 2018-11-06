package com.talentcoach.id11.id11_android.profiel

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.gson.Gson
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.R.id.logout_btn
import com.talentcoach.id11.id11_android.R.id.userfullNameTxt
import com.talentcoach.id11.id11_android.login.LoginActivity
import com.talentcoach.id11.id11_android.repositories.responses.LoginResponse
import kotlinx.android.synthetic.main.activity_profiel.*

class ProfielActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    lateinit var user: LoginResponse


    lateinit var userfullNameText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiel)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        spEditor = sharedPreferences.edit()

        this.userfullNameText = userfullNameTxt

        readFromSharedPrerences()

        logout_btn.setOnClickListener {
            // remove the saved user password and user credentials
            spEditor.putString(getString(R.string.sp_key_password), "")
            spEditor.commit()
            spEditor.putString(getString(R.string.sp_key_user), "")
            spEditor.commit()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        this.userfullNameText.text = "${user.voornaam} ${user.naam}"

    }

    private fun readFromSharedPrerences() {
        val gson = Gson()
        val jsonGebruiker = sharedPreferences.getString(getString(R.string.sp_key_user), "Geen ingelogde gebruiker")
        user = gson.fromJson(jsonGebruiker, LoginResponse::class.java)
    }
}
