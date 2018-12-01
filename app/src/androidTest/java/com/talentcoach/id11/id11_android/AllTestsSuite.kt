package com.talentcoach.id11.id11_android

import com.talentcoach.id11.id11_android.login.LoginActivityTest
import com.talentcoach.id11.id11_android.profiel.ProfielActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


/**
 * Runs all test cases
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
        JobNavigationFragmentTestSuite::class,
        WerkaanbiedingNavigationFragmentTestSuite::class,
        ProfielActivityTest::class,
        LoginActivityTest::class
)
class AllTestsSuite