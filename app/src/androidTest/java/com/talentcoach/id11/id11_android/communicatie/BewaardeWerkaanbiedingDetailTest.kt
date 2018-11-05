package com.talentcoach.id11.id11_android.communicatie

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.data.DummyDbContext
import com.talentcoach.id11.id11_android.data.DummyLeerlingRepository
import com.talentcoach.id11.id11_android.data.DummyWerkaanbiedingRepository
import com.talentcoach.id11.id11_android.managers.DataManager
import kotlinx.android.synthetic.main.fragment_werkaanbieding.*
import kotlinx.android.synthetic.main.fragment_werkaanbieding_buttons.*
import kotlinx.android.synthetic.main.fragment_werkaanbiedingen_list.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Contains all test cases concerning the user story
 * "Bewaarde Werkaanbieding in detail bekijken"
 */
@RunWith(AndroidJUnit4::class)
class BewaardeWerkaanbiedingDetailTest {
    @get:Rule
    var activityRule: ActivityTestRule<WerkaanbiedingActivity>
            = ActivityTestRule(WerkaanbiedingActivity::class.java)
    lateinit var activity: WerkaanbiedingActivity
    lateinit var werkaanbiedingFragment: WerkaanbiedingFragment
    lateinit var werkaanbiedingenListFragment: WerkaanbiedingenListFragment
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
        werkaanbiedingenListFragment = activity.werkaanbiedingenListFragment
        werkaanbiedingButtonsFragment = activity.werkaanbiedingButtonsFragment
        clickButton(R.id.like)
        clickButton(R.id.toggleFragmentBtn)
        onView(withId(R.id.werkaanbiedingenRecView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<WerkaanbiedingenListFragment.WerkaanbiedingenListAdapter.WerkaanbiedingenViewHolder>
                (0, click()))
    }

    @After
    fun resetDbContext(){
        DummyDbContext.resetDbContext()
    }

    @Test
    fun selectItemFromList_ShowsWerkaanbiedingFragment(){
        assertTrue("WerkaanbiedingFragment must be visible", werkaanbiedingFragment.isVisible)
    }

    @Test
    fun selectItemFromList_ShowsCorrectWerkaanbieding(){
        val werkaanbieding = activity.leerling.bewaardeWerkaanbiedingen.first()
        assertEquals(werkaanbieding, werkaanbiedingFragment.werkaanbieding)
        assertEquals(context.getString(R.string.wa_werkgever, werkaanbieding.werkgever.naam), activity.werkgever.text)
        assertEquals(context.getString(R.string.wa_beschrijving, werkaanbieding.omschrijving), activity.omschrijving.text)
    }

    @Test
    fun selectItemFromList_ShowsWerkaanbiedingButtonsFragment(){
        assertTrue("WerkaanbiedingButtonsFragment must be shown", werkaanbiedingButtonsFragment.isVisible)
    }

    @Test
    fun selectItemFromList_ShowsOnlyRemoveButtonOnWerkaanbiedingButtonsFragment(){
        assertTrue("Only Remove button should be shown", werkaanbiedingButtonsFragment.onlyRemove)
        assertFalse("Like button should not be visible", activity.like.visibility == 0)
    }

    private fun clickButton(id: Int, times: Int = 1) {
        for (i in 1..times){
            onView(withId(id)).perform(click())
        }
    }


}