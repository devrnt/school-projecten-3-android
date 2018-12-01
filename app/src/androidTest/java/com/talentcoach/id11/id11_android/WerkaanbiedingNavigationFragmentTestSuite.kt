package com.talentcoach.id11.id11_android

import com.talentcoach.id11.id11_android.communicatie.*
import org.junit.runner.RunWith
import org.junit.runners.Suite


/**
 * Runs all test cases concerning WerkaanbiedingNavigationFragment
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
        BekijkWerkaanbiedingTest::class,
        ReageerWerkaanbiedingTest::class,
        OverzichtBewaardeWerkaanbiedingenTest::class,
        BewaardeWerkaanbiedingDetailTest::class,
        BewaardeWerkaanbiedingVerwijderenTest::class
)
class WerkaanbiedingNavigationFragmentTestSuite