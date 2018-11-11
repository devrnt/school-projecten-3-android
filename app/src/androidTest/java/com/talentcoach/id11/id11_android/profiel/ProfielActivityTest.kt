package com.talentcoach.id11.id11_android.profiel


import android.content.ComponentName
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.login.LoginActivity
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// there should already be user logged-in to launch these tests
@RunWith(AndroidJUnit4::class)
class ProfielActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<ProfielActivity> = ActivityTestRule(ProfielActivity::class.java)

    lateinit var activity: ProfielActivity
    lateinit var context: Context

    @Before
    fun init() {
        // TODO("Write a gebruiker to the sharedPreferences so the tests can run without a previously logged in user)
        // code to execute before each test
        context = InstrumentationRegistry.getTargetContext()
        activity = activityRule.activity

        Intents.init()
    }

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun editButtonClicked_StartProfielEditActivity() {
        onView(withId(R.id.edit_btn)).perform(click())

        intended(hasComponent(ComponentName(getTargetContext(), ProfielEditActivity::class.java)))
        pressBack()
    }

    // keep in mind that this will remove the gebruiker from SharedPref and so gebruiker will be null
    @Test
    fun logoutButtonClicked_StartLoginActivityAndFinishCurrentActivity() {
        onView(withId(R.id.logout_btn)).perform(scrollTo(), click())

        intended(hasComponent(ComponentName(getTargetContext(), LoginActivity::class.java)))
        assertTrue(activity.isFinishing)
    }

}

