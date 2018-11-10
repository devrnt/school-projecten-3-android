package com.talentcoach.id11.id11_android.joborganisatie

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.data.DummyDbContext
import com.talentcoach.id11.id11_android.data.DummySpecifiekeInfoRepository
import com.talentcoach.id11.id11_android.data.DummyWerkgeverRepository
import com.talentcoach.id11.id11_android.managers.DataManager
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BekijkSpecifiekeInfoTest {
    @get:Rule
    var activityRule: ActivityTestRule<InfoActivity>
            = ActivityTestRule(InfoActivity::class.java)
    lateinit var activity: InfoActivity
    lateinit var context: Context

    init {
        DataManager.specifiekeInfoRepository = DummySpecifiekeInfoRepository()
        DataManager.werkgeverRepository = DummyWerkgeverRepository()
    }

    @Before
    fun init() {
        // code to execute before each test
        context = InstrumentationRegistry.getTargetContext()
        activity = activityRule.activity
        DummyDbContext.resetDbContext()

    }

    @Test
    fun clickSpecInfoBtn_showsSpecifiekeInfoInExpandableListView(){
        onView(withId(R.id.specInfoBtn)).perform(click())
        val specifiekeInfos = DataManager.getSpecifiekeInfoForWerkgever(DummyDbContext.werkgevers[0])
        val headers = specifiekeInfos.map { si -> si.titel }
        val bodies = specifiekeInfos.map { si -> si.omschrijving }
        var matches = headers.intersect(activity.specifiekeInfoFragment.headerList!!.asIterable()).any()
        assertTrue(
                "Headers are not the same",
                matches
        )

        matches = bodies.intersect(activity.specifiekeInfoFragment.bodyList!!.asIterable()).any()
        assertTrue(
                "Bodies are not the same",
                matches
        )
    }

    @Test
    fun clickSpecInfoBtn_switchesToInfoFragmentWithSpecifiekeInfo(){
        onView(withId(R.id.specInfoBtn)).perform(click())

        assertTrue(
                "Specifieke Info is not visible",
                activity.specifiekeInfoFragment.isVisible
        )
    }

}