package com.talentcoach.id11.id11_android.joborganisatie

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.talentcoach.id11.id11_android.R
import com.talentcoach.id11.id11_android.data.DummyAlgemeneInfoRepository
import com.talentcoach.id11.id11_android.data.DummyDbContext
import com.talentcoach.id11.id11_android.data.DummySpecifiekeInfoRepository
import com.talentcoach.id11.id11_android.data.DummyWerkgeverRepository
import com.talentcoach.id11.id11_android.managers.DataManager
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BekijkAlgemeneInfoTest {
    @get:Rule
    var activityRule: ActivityTestRule<JobNavigationFragment>
            = ActivityTestRule(JobNavigationFragment::class.java)
    lateinit var activity: JobNavigationFragment
    lateinit var context: Context

    init {
        DataManager.specifiekeInfoRepository = DummySpecifiekeInfoRepository()
        DataManager.werkgeverRepository = DummyWerkgeverRepository()
        DataManager.algemeneInfoRepository = DummyAlgemeneInfoRepository()
    }

    @Before
    fun init() {
        // code to execute before each test
        context = InstrumentationRegistry.getTargetContext()
        activity = activityRule.activity
    }

    @After
    fun resetDbContext() {
        DummyDbContext.resetDbContext()
    }

    @Test
    fun startActivity_showsAlgemeneInfoInExpandableListView(){
        val algemeneInfos = DummyDbContext.algemeneInfos
        val headers = algemeneInfos.map { ai -> ai.titel }
        val bodies = algemeneInfos.map { ai -> ai.omschrijving }
        assertTrue(
                "Headers are not the same",
                headers.all { h -> activity.algemeneInfoFragment.headerList!!.contains(h) }
        )

        assertTrue(
                "Bodies are not the same",
                bodies.all { b -> activity.algemeneInfoFragment.bodyList!!.contains(b) }
        )
    }

    @Test
    fun clickAlgInfoBtn_switchesToInfoFragmentWithAlgemeneInfo(){
        onView(withId(R.id.specInfoBtn)).perform(click())
        onView(withId(R.id.algInfoBtn)).perform(click())

        assertTrue(
                "Algemene Info is not visible",
                activity.algemeneInfoFragment.isVisible
        )
    }

}