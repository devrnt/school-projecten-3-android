package com.talentcoach.id11.id11_android

import com.talentcoach.id11.id11_android.communicatie.BekijkWerkaanbiedingTest
import com.talentcoach.id11.id11_android.communicatie.BewaardeWerkaanbiedingDetailTest
import com.talentcoach.id11.id11_android.communicatie.OverzichtBewaardeWerkaanbiedingenTest
import com.talentcoach.id11.id11_android.communicatie.ReageerWerkaanbiedingTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
        BekijkWerkaanbiedingTest::class,
        ReageerWerkaanbiedingTest::class,
        OverzichtBewaardeWerkaanbiedingenTest::class,
        BewaardeWerkaanbiedingDetailTest::class
)
class WerkaanbiedingActivityTestSuite {

}