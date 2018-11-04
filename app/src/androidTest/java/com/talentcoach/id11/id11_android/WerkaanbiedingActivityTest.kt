package com.talentcoach.id11.id11_android

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.talentcoach.id11.id11_android.communicatie.WerkaanbiedingActivity
import com.talentcoach.id11.id11_android.communicatie.WerkaanbiedingFragment
import com.talentcoach.id11.id11_android.communicatie.WerkaanbiedingenListFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class WerkaanbiedingActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<WerkaanbiedingActivity>
            = ActivityTestRule(WerkaanbiedingActivity::class.java)
    lateinit var activity: WerkaanbiedingActivity
    lateinit var werkaanbiedingFragment: WerkaanbiedingFragment
    lateinit var werkaanbiedingenListFragment: WerkaanbiedingenListFragment

    @Before
    fun init(){
        activity = activityRule.activity
        werkaanbiedingFragment = activity.werkaanbiedingFragment
        werkaanbiedingenListFragment = activity.werkaanbiedingenListFragment
    }


    //region === Werkaanbieding bekijken ===
    @Test
    fun startActivity_ShowsWerkaanbiedingFragment(){
        Assert.assertTrue(werkaanbiedingFragment.isVisible)
    }

    fun startActivity_WhenWerkaanbiedingFound_ShowsReactButtons(){
        //TODO: implement test
    }

    fun startActivity_WhenNoWerkaanbiedingFound_HidesReactButtons(){
        //TODO: implement test
    }
    //endregion

    //region === Werkaanbieding reageren ===
    @Test
    fun clickNoLike_ShowsNewWerkaanbieding(){
        val werkaanbieding1 = activity.werkaanbiedingFragment.werkaanbieding
        onView(withId(R.id.noLike)).perform(click())
        val werkaanbieding2 = activity.werkaanbiedingFragment.werkaanbieding
        Assert.assertNotEquals(werkaanbieding1, werkaanbieding2)
    }
    //endregion


    //region === Bewaarde Werkaanbiedingen bekijken ===
    @Test
    fun toggleButton_WhenClickedForTheFirstTime_ShowsWerkaanbiedingenListFragment(){
        onView(withId(R.id.toggleFragmentBtn)).perform(click())
        Assert.assertTrue(werkaanbiedingenListFragment.isVisible)
    }

    @Test
    fun toggleButton_WhenClickedForTheFirstTime_HidesWerkaanbiedingFragment(){
        onView(withId(R.id.toggleFragmentBtn)).perform(click())
        Assert.assertFalse(werkaanbiedingFragment.isVisible)
    }
    //endregion

}