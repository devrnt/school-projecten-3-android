package com.talentcoach.id11.id11_android.data

import com.talentcoach.id11.id11_android.models.IRepository
import com.talentcoach.id11.id11_android.models.SpecifiekeInfo

class DummySpecifiekeInfoRepository: IRepository<SpecifiekeInfo> {

    override fun getById(id: Int): SpecifiekeInfo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(toUpdate: SpecifiekeInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): List<SpecifiekeInfo> {
        return DummyDbContext.specifiekeInfos
    }
}