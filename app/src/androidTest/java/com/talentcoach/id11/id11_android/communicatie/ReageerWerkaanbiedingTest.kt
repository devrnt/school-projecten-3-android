package com.talentcoach.id11.id11_android.communicatie

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.data.DummyDbContext
import com.talentcoach.id11.id11_android.data.DummyLeerlingRepository
import com.talentcoach.id11.id11_android.data.DummyWerkaanbiedingRepository
import com.talentcoach.id11.id11_android.managers.DataManager
import kotlinx.android.synthetic.main.fragment_werkaanbieding.*
import kotlinx.android.synthetic.main.fragment_werkaanbiedingen_list.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Contains all test cases concerning the user story
 * "Reageren op een werkaanbieding"
 */
@RunWith(AndroidJUnit4::class)
class ReageerWerkaanbiedingTest {
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
    fun init(){
        // code to execute before each test
        context = InstrumentationRegistry.getTargetContext()
        activity = activityRule.activity
        werkaanbiedingFragment = activity.werkaanbiedingFragment
        werkaanbiedingenListFragment = activity.werkaanbiedingenListFragment
        werkaanbiedingButtonsFragment = activity.werkaanbiedingButtonsFragment
        DummyDbContext.resetDbContext()
    }

    @Test
    fun clickNoLike_ShowsNewWerkaanbieding(){
        val werkaanbieding1 = activity.werkaanbiedingFragment.werkaanbieding

        Espresso.onView(ViewMatchers.withId(R.id.noLike)).perform(ViewActions.click())

        val werkaanbieding2 = activity.werkaanbiedingFragment.werkaanbieding
        Assert.assertNotEquals(werkaanbieding1, werkaanbieding2)
    }

    @Test
    fun clickNoLike_AddsWerkaanbiedingToVerwijderdeWerkaanbiedingen(){
        val werkaanbieding = activity.werkaanbiedingFragment.werkaanbieding

        Espresso.onView(ViewMatchers.withId(R.id.noLike)).perform(ViewActions.click())

        val verwijderdeWerkaanbiedingen = activity.leerling.verwijderdeWerkaanbiedingen

        Assert.assertEquals(
                1,
                verwijderdeWerkaanbiedingen.count()
        )
        Assert.assertTrue(
                "Werkaanbieding is not present in VerwijderdeWerkaanbiedingen",
                verwijderdeWerkaanbiedingen.any { wa -> wa.id == werkaanbieding?.id }
        )
    }

    @Test
    fun clickLike_ShowsNewWerkaanbieding(){
        val werkaanbieding1 = activity.werkaanbiedingFragment.werkaanbieding

        Espresso.onView(ViewMatchers.withId(R.id.like)).perform(ViewActions.click())

        val werkaanbieding2 = activity.werkaanbiedingFragment.werkaanbieding
        Assert.assertNotEquals(werkaanbieding1, werkaanbieding2)
    }

    @Test
    fun clickLike_AddsWerkaanbiedingToBewaardeWerkaanbiedingen(){
        val werkaanbieding = activity.werkaanbiedingFragment.werkaanbieding
        val initialCount = activity.leerling.bewaardeWerkaanbiedingen.count()

        Espresso.onView(ViewMatchers.withId(R.id.like)).perform(ViewActions.click())

        val bewaardeWerkaanbiedingen = activity.leerling.bewaardeWerkaanbiedingen

        Assert.assertEquals(
                1,
                bewaardeWerkaanbiedingen.count() - initialCount
        )
        Assert.assertTrue(
                "Werkaanbieding is not present in BewaardeWerkaanbiedingen",
                bewaardeWerkaanbiedingen.any { wa -> wa.id == werkaanbieding?.id }
        )
    }

    @Test
    fun clickLike_AddsWerkaanbiedingToItemsInAdapter(){
        val initialCount = activity.werkaanbiedingenRecView.adapter!!.itemCount

        Espresso.onView(ViewMatchers.withId(R.id.like)).perform(ViewActions.click())


        Assert.assertEquals(
                1,
                activity.werkaanbiedingenRecView.adapter!!.itemCount - initialCount
        )
    }

    @Test
    fun react_WhenNoNewWerkaanbiedingIsShown_ButtonsAreHidden(){
        while (werkaanbiedingFragment.werkaanbieding != null)
            Espresso.onView(ViewMatchers.withId(R.id.noLike)).perform(ViewActions.click())
        Assert.assertFalse(
                "Buttons are still visible",
                werkaanbiedingButtonsFragment.isVisible
        )
    }

    @Test
    fun react_WhenNoNewWerkaanbiedingIsShown_ShowDefaultText(){
        while (werkaanbiedingFragment.werkaanbieding != null)
            Espresso.onView(ViewMatchers.withId(R.id.noLike)).perform(ViewActions.click())
        Assert.assertEquals(
                context.getString(R.string.no_werkaanbieding),
                werkaanbiedingFragment.werkgever.text
        )
    }
    //endregion
}