package com.talentcoach.id11.id11_android.communicatie

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
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
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith

/**
 * Contains all test cases concerning the user story
 * "Overzicht Bewaarde Werkaanbiedingen bekijken"
 */
@RunWith(AndroidJUnit4::class)
class OverzichtBewaardeWerkaanbiedingenTest {
    @get:Rule
    var activityRule: ActivityTestRule<WerkaanbiedingActivity>
            = ActivityTestRule(WerkaanbiedingActivity::class.java)
    lateinit var activity: WerkaanbiedingActivity
    lateinit var werkaanbiedingFragment: WerkaanbiedingFragment
    lateinit var werkaanbiedingenListFragment: WerkaanbiedingenListFragment
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
        werkaanbiedingenListFragment = activity.werkaanbiedingenListFragment
    }

    @After
    fun resetDbContext(){
        DummyDbContext.resetDbContext()
    }

    @Test
    fun toggleButtonClick_WhenClickedWithoutBewaardeWerkaanbiedingen_DoesNotShowWerkaanbiedingenListFragment(){
        clickButton(R.id.toggleFragmentBtn)
        assertTrue("WerkaanbiedingFragment must be visible", werkaanbiedingFragment.isVisible)
        assertFalse("WerkaanbiedingListFragment must be hidden", werkaanbiedingenListFragment.isVisible)
        assertEquals(context.getString(R.string.mijn_werkaanb_button), activity.toggleFragmentBtn.text)
    }

    @Test
    fun toggleButtonClick_WhenClickedWithBewaardeWerkaanbiedingen_TogglesFragments(){
        clickButton(R.id.like)
        clickButton(R.id.toggleFragmentBtn)
        assertEquals(context.getString(R.string.bekijk_werkaanb_btn), activity.toggleFragmentBtn.text)
        assertFalse("WerkaanbiedingFragment must be hiddden", werkaanbiedingFragment.isVisible)
        assertTrue("WerkaanbiedingListFragment must be visible", werkaanbiedingenListFragment.isVisible)
    }

    @Test
    fun werkaanbiedingenListFragmentShown_ShowsWerkaanbiedingen(){
        val initalCount = werkaanbiedingenListFragment.adapter!!.itemCount
        clickButton(R.id.like)
        clickButton(R.id.toggleFragmentBtn)
        assertEquals(1, werkaanbiedingenListFragment.adapter!!.itemCount - initalCount)
    }

    @Test
    fun toggleButtonClick_WhenWerkaanbiedingenListFragmentIsShown_TogglesFragments(){
        clickButton(R.id.toggleFragmentBtn, 2)
        assertEquals(context.getString(R.string.mijn_werkaanb_button), activity.toggleFragmentBtn.text)
        assertTrue("WerkaanbiedingFragment must be visible", werkaanbiedingFragment.isVisible)
        assertFalse("WerkaanbiedingListFragment must be hidden", werkaanbiedingenListFragment.isVisible)
    }

    @Test
    fun onResume_WhenActivityWasPaused_ShowsWerkaanbiedingenListFragmentAgain(){
        clickButton(R.id.like)
        clickButton(R.id.toggleFragmentBtn)

        activityRule.runOnUiThread {
            getInstrumentation().callActivityOnPause(activity)
            getInstrumentation().callActivityOnResume(activity)
        }

        assertTrue("WerkaanbiedingenListFragment must be visible", werkaanbiedingenListFragment.isVisible)
    }

    private fun clickButton(id: Int, times: Int = 1) {
        for (i in 1..times){
            onView(withId(id)).perform(click())
        }
    }

}