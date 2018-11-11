package com.talentcoach.id11.id11_android

import com.talentcoach.id11.id11_android.data.DummyDbContext
import com.talentcoach.id11.id11_android.data.DummyLeerlingRepository
import com.talentcoach.id11.id11_android.data.DummySpecifiekeInfoRepository
import com.talentcoach.id11.id11_android.data.DummyWerkaanbiedingRepository
import com.talentcoach.id11.id11_android.managers.DataManager
import com.talentcoach.id11.id11_android.models.Werkgever
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DataManagerTest {

    init {
        DataManager.leerlingRepository = DummyLeerlingRepository()
        DataManager.werkaanbiedingRepository = DummyWerkaanbiedingRepository()
        DataManager.specifiekeInfoRepository = DummySpecifiekeInfoRepository()
    }

    @Before
    fun reset(){
        DummyDbContext.resetDbContext()
    }

    //region === getWerkaanbiedingForLeerling ===
    @Test
    fun getWerkaanbiedingForLeerling_WhenMatchExists_ReturnsWerkaanbieding() {
        val leerling = DataManager.getLeerlingById(1)
        val werkaanbieding = DataManager.getWerkaanbiedingForLeerling(leerling)
        Assert.assertTrue(werkaanbieding != null)
    }

    @Test
    fun getWerkaanbiedingForLeerling_WhenNoMatchExists_ReturnsNull() {
        val leerling = DataManager.getLeerlingById(3)
        val werkaanbieding = DataManager.getWerkaanbiedingForLeerling(leerling)
        Assert.assertTrue(
                "Should have returned null",
                werkaanbieding == null)
    }
    //endregion

    //region === getSpecifiekeInfoForWerkgever ===
    @Test
    fun getSpecifiekeInfoForWerkgever_ReturnsMatchingInfos(){
        val specifiekeInfos = DataManager.getSpecifiekeInfoForWerkgever(DummyDbContext.werkgevers[0])
        Assert.assertEquals(
                1,
                specifiekeInfos.count()
        )
    }
    //endregion

}