package com.talentcoach.id11.id11_android

import com.talentcoach.id11.id11_android.joborganisatie.BekijkAlgemeneInfoTest
import com.talentcoach.id11.id11_android.joborganisatie.BekijkSpecifiekeInfoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


/**
 * Runs all test cases concerning InfoActivity
 */
@RunWith(Suite::class)
@Suite.SuiteClasses(
        BekijkAlgemeneInfoTest::class,
        BekijkSpecifiekeInfoTest::class
)
class InfoActivityTestSuite