package com.talentcoach.id11.id11_android.communicatie

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.data.DummyDbContext
import com.talentcoach.id11.id11_android.data.DummyLeerlingRepository
import com.talentcoach.id11.id11_android.data.DummyWerkaanbiedingRepository
import com.talentcoach.id11.id11_android.managers.DataManager
import kotlinx.android.synthetic.main.activity_werkaanbieding.*
import kotlinx.android.synthetic.main.fragment_werkaanbieding.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Contains all test cases concerning the user story
 * "Bekijk Werkaanbieding"
 */
@RunWith(AndroidJUnit4::class)
class BekijkWerkaanbiedingTest {
    @get:Rule
    var activityRule: ActivityTestRule<WerkaanbiedingActivity>
            = ActivityTestRule(WerkaanbiedingActivity::class.java)
    lateinit var activity: WerkaanbiedingActivity
    lateinit var werkaanbiedingFragment: WerkaanbiedingFragment
    lateinit var werkaanbiedingButtonsFragment: WerkaanbiedingButtonsFragment
    lateinit var context: Context

    init {
        DataManager.werkaanbiedingRepository = DummyWerkaanbiedingRepository()
        DataManager.leerlingRepository = DummyLeerlingRepository()
    }

    @Before
    fun init() {
        // code to execute before each test
        context = InstrumentationRegistry.getTargetContext()
        activity = activityRule.activity
        werkaanbiedingFragment = activity.werkaanbiedingFragment
        werkaanbiedingButtonsFragment = activity.werkaanbiedingButtonsFragment
        DummyDbContext.resetDbContext()
    }


    //region === Werkaanbieding bekijken ===
    @Test
    fun startActivity_ShowsWerkaanbiedingFragment() {
        Assert.assertTrue("WerkaanbiedingFragment must be visible", werkaanbiedingFragment.isVisible)
    }

    @Test
    fun startActivity_WhenWerkaanbiedingFound_ShowsReactButtons() {
        Assert.assertTrue("React buttons must be shown", werkaanbiedingButtonsFragment.isVisible)
    }

    @Test
    fun startActivity_WhenWerkaanbiedingFound_ShowsWerkaanbiedingText() {
        val werkaanbieding = werkaanbiedingFragment.werkaanbieding
        val textWerkgever = context.getString(R.string.wa_werkgever, werkaanbieding?.werkgever?.naam)
        val textOmschrijving = context.getString(R.string.wa_beschrijving, werkaanbieding?.omschrijving)
        Assert.assertEquals(textWerkgever, activity.werkgever.text)
        Assert.assertEquals(textOmschrijving, activity.omschrijving.text)
    }

    @Test
    fun startActivity_WhenNoWerkaanbiedingFound_HidesReactButtons() {
        if (werkaanbiedingFragment.noWerkaanbiedingFound)
            Assert.assertFalse("React buttons must be hidden", werkaanbiedingButtonsFragment.isVisible)
    }

    @Test
    fun startActivity_WhenNoWerkaanbiedingFound_ShowsDefaultText() {
        if (werkaanbiedingFragment.noWerkaanbiedingFound)
            Assert.assertEquals(activity.werkgever.text, context.getString(R.string.no_werkaanbieding_1))
    }

    @Test
    fun startActivity_ToggleButtonShowsBewaardeWerkaanbiedingenText() {
        Assert.assertEquals(activity.toggleFragmentBtn.text, context.getString(R.string.mijn_werkaanb_button))
    }

    @Test
    fun onResume_WhenActivityWasPaused_ShowsWerkaanbiedingFragmentAgainWithSameWerkaanbieding(){
        val werkaanbieding = werkaanbiedingFragment.werkaanbieding

        activityRule.runOnUiThread {
                InstrumentationRegistry.getInstrumentation().callActivityOnPause(activity)
                InstrumentationRegistry.getInstrumentation().callActivityOnResume(activity)
        }

        assertEquals(werkaanbieding, werkaanbiedingFragment.werkaanbieding)
        Assert.assertTrue("WerkaanbiedingenFragment must be visible", werkaanbiedingFragment.isVisible)
    }

    //endregion
}