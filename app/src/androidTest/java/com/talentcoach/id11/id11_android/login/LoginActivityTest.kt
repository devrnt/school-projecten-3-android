package com.talentcoach.id11.id11_android.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.talentcoach.id11.id11_android.R
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java, true, false)

    private lateinit var activity: LoginActivity
    private lateinit var loginFragment: LoginFragment
    private lateinit var context: Context
    private lateinit var preferencesEditor: SharedPreferences.Editor

    @Before
    fun init() {
        context = getInstrumentation().targetContext

        preferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        removeSharedPreferences()

        activity = activityRule.launchActivity(Intent())
        loginFragment = activity.loginFragment
    }

    @Test
    fun clickLoginButtonOnFreshInstall_EmptyUsernameAndEmptyPassword_ShowsErrorBothUserAndPass() {
        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click())

        val usernameError = loginFragment.gebruikersnaamInputLayout.error
        val passwordError = loginFragment.wachtwoordInputLayout.error

        Assert.assertEquals(context.getString(R.string.error_username), usernameError)
        Assert.assertEquals(context.getString(R.string.error_password), passwordError)
    }

    @Test
    fun clickLoginButtonOnFreshInstall_EmptyUsername_ShowsErrorUsername() {
        // fill in valid password
        Espresso.onView(ViewMatchers.withId(R.id.wachtwoord_input)).perform(ViewActions.typeText("validpassword"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click())

        val usernameError = loginFragment.gebruikersnaamInputLayout.error
        val passwordError = loginFragment.wachtwoordInputLayout.error

        Assert.assertNull(passwordError)
        Assert.assertEquals(context.getString(R.string.error_username), usernameError)
    }

    @Test
    fun clickLoginButtonOnFreshInstall_EmptyPassword_ShowsErrorPassword() {
        // fill in valid password
        Espresso.onView(ViewMatchers.withId(R.id.gebruikersNaam_input)).perform(ViewActions.typeText("validusername"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(ViewMatchers.withId(R.id.login_btn)).perform(ViewActions.click())

        val usernameError = loginFragment.gebruikersnaamInputLayout.error
        val passwordError = loginFragment.wachtwoordInputLayout.error

        Assert.assertNull(usernameError)
        Assert.assertEquals(context.getString(R.string.error_password), passwordError)
    }

    private fun removeSharedPreferences() {
        // reset the previously saved values in the SharedPreferences
        preferencesEditor.putString(context.getString(R.string.sp_key_username), "").commit()
        preferencesEditor.putString(context.getString(R.string.sp_key_password), "").commit()
    }

}
