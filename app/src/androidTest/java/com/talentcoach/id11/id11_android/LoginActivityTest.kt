package com.talentcoach.id11.id11_android

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.talentcoach.id11.id11_android.login.LoginActivity
import com.talentcoach.id11.id11_android.login.LoginFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Test should start with a clean app install
// TODO("Sharedpreferences clearen zodat de username en password zoieso leeg zijn")
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    private lateinit var activity: LoginActivity
    private lateinit var loginFragment: LoginFragment
    private lateinit var context: Context

    @Before
    fun init() {
        context = InstrumentationRegistry.getTargetContext()
        activity = activityRule.activity
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
}