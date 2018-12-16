package com.talentcoach.id11.id11_android.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.talentcoach.id11.id11_android.R
/**
 * Deel van *login*.
 *
 * Activy verantwoordelijk voor het inloggen van gebruikers
 * @property loginFragment Bouwt het inlogscherm op en bevat de achterliggende functionaliteit
 *
 */
class LoginActivity : AppCompatActivity() {
    val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.login_container, loginFragment)
                    .commit()
        }
    }
}