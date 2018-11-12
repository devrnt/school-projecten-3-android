package com.talentcoach.id11.id11_android.profiel


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.google.gson.Gson
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.data.DummyLeerlingRepository
import com.talentcoach.id11.id11_android.login.LoginActivity
import com.talentcoach.id11.id11_android.models.Gebruiker
import com.talentcoach.id11.id11_android.models.Leerling
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// there should already be user logged-in to launch these tests
@RunWith(AndroidJUnit4::class)
class ProfielActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<ProfielActivity> = ActivityTestRule(ProfielActivity::class.java, true, false)

    lateinit var activity: ProfielActivity
    lateinit var context: Context
    private lateinit var preferencesEditor: SharedPreferences.Editor
    private lateinit var gebruiker: Gebruiker
    private lateinit var leerling: Leerling

    @Before
    fun init() {
        context = getInstrumentation().targetContext

        preferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        gebruiker = Gebruiker("JonasDeVrient", "jonasjonas")
        leerling = DummyLeerlingRepository().getById(1)

        writeGebruikerToSharedPreferences()

        activity = activityRule.launchActivity(Intent())
        activity.leerling = leerling

        Intents.init()
    }

    private fun writeGebruikerToSharedPreferences() {
        val gson = Gson()
        val jsonGebruiker = gson.toJson(gebruiker)
        preferencesEditor.putString(context.getString(R.string.sp_key_user), jsonGebruiker).commit()
    }

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun editButtonClicked_StartProfielEditActivity() {
        onView(withId(R.id.edit_btn)).perform(click())

        intended(hasComponent(ComponentName(getTargetContext(), ProfielEditActivity::class.java)))
    }

    // keep in mind that this will remove the gebruiker from SharedPref and so gebruiker will be null
    @Test
    fun logoutButtonClicked_StartLoginActivityAndFinishCurrentActivity() {
        onView(withId(R.id.logout_btn)).perform(scrollTo(), click())

        intended(hasComponent(ComponentName(getTargetContext(), LoginActivity::class.java)))
        assertTrue(activity.isFinishing)
    }

}

