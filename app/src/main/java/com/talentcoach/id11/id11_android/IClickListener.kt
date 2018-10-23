package com.talentcoach.id11.id11_android

interface IClickListener {
    fun likeClicked()
    fun noLikeClicked()
    fun removeClicked(pos: Int)
    fun itemClicked(pos: Int)
}