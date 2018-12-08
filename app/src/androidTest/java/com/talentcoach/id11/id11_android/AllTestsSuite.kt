package com.talentcoach.id11.id11_android

import com.talentcoach.id11.id11_android.login.LoginActivityTest
import com.talentcoach.id11.id11_android.profiel.ProfielFragmentTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


/**
 * Runs all test cases
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
        JobNavigationFragmentTestSuite::class,
        WerkaanbiedingNavigationFragmentTestSuite::class,
        ProfielFragmentTest::class,
        LoginActivityTest::class
)
class AllTestsSuite