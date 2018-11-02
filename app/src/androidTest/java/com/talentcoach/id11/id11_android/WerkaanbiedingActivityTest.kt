package com.talentcoach.id11.id11_android

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.talentcoach.id11.id11_android.communicatie.WerkaanbiedingActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class WerkaanbiedingActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<WerkaanbiedingActivity>
            = ActivityTestRule(WerkaanbiedingActivity::class.java)

    @Before
    fun init(){
        // init before every test here
    }

    fun textViews_TextGetsSet_WhenWerkaanbiedingIsRetrieved(){
        // setup, execute and assert test
    }

}