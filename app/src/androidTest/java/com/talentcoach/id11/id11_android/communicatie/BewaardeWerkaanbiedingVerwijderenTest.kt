package com.talentcoach.id11.id11_android.communicatie

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.widget.ImageButton
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.data.DummyDbContext
import com.talentcoach.id11.id11_android.data.DummyLeerlingRepository
import com.talentcoach.id11.id11_android.data.DummyWerkaanbiedingRepository
import com.talentcoach.id11.id11_android.managers.DataManager
import kotlinx.android.synthetic.main.fragment_werkaanbiedingen_list.*
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Contains all test cases concerning the user story
 * "Bewaarde Werkaanbieding verwijderen"
 */
@RunWith(AndroidJUnit4::class)
class BewaardeWerkaanbiedingVerwijderenTest {
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
    }

    @After
    fun resetDbContext(){
        DummyDbContext.resetDbContext()
    }

    @Test
    fun removeClickedInList_RemovesItemFromAdapterItems(){
        val initialCount = activity.werkaanbiedingenRecView.adapter!!.itemCount
        Espresso.onView(ViewMatchers.withId(R.id.werkaanbiedingenRecView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<WerkaanbiedingenListFragment.WerkaanbiedingenListAdapter.WerkaanbiedingenViewHolder>
                (0, MyViewAction.clickChildViewWithId(R.id.removeImgBtn)))
        assertEquals(-1, activity.werkaanbiedingenRecView.adapter!!.itemCount - initialCount)
    }

    @Test
    fun removeClickedInList_RemovesItemFromBewaardeWerkaanbiedingen(){
        val initialCount = activity.leerling.bewaardeWerkaanbiedingen.count()
        Espresso.onView(ViewMatchers.withId(R.id.werkaanbiedingenRecView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<WerkaanbiedingenListFragment.WerkaanbiedingenListAdapter.WerkaanbiedingenViewHolder>
                (0, MyViewAction.clickChildViewWithId(R.id.removeImgBtn)))
        assertEquals(-1, activity.leerling.bewaardeWerkaanbiedingen.count() - initialCount)
    }

    @Test
    fun removeClickedInDetail_RemovesItemFromAdapterItems(){
        val initialCount = activity.werkaanbiedingenRecView.adapter!!.itemCount
        selectFirstItem()
        clickButton(R.id.noLike)
        assertEquals(-1, activity.werkaanbiedingenRecView.adapter!!.itemCount - initialCount)
    }

    @Test
    fun removeClickedInDetail_RemovesItemFromBewaardeWerkaanbiedingen(){
        val initialCount = activity.leerling.bewaardeWerkaanbiedingen.count()
        selectFirstItem()
        clickButton(R.id.noLike)
        assertEquals(-1, activity.leerling.bewaardeWerkaanbiedingen.count() - initialCount)
    }

    private fun clickButton(id: Int, times: Int = 1) {
        for (i in 1..times){
            Espresso.onView(ViewMatchers.withId(id)).perform(ViewActions.click())
        }
    }

    private fun selectFirstItem(){
        Espresso.onView(ViewMatchers.withId(R.id.werkaanbiedingenRecView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<WerkaanbiedingenListFragment.WerkaanbiedingenListAdapter.WerkaanbiedingenViewHolder>
                (0, ViewActions.click()))
    }


    // source: https://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item#30338665
    // adds a function to click on a child view within an item of a RecyclerView
    object MyViewAction {

        fun clickChildViewWithId(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<ImageButton>(id)
                    v.performClick()
                }
            }
        }

    }
}