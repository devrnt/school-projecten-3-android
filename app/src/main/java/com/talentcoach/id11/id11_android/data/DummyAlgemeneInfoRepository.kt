package com.talentcoach.id11.id11_android.data

import com.talentcoach.id11.id11_android.models.AlgemeneInfo
import com.talentcoach.id11.id11_android.models.IRepository

class DummyAlgemeneInfoRepository : IRepository<AlgemeneInfo> {
    override fun getById(id: Int): AlgemeneInfo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(toUpdate: AlgemeneInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): List<AlgemeneInfo> {
        return DummyDbContext.algemeneInfos
    }

}