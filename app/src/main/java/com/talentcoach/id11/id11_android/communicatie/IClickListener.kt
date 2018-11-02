package com.talentcoach.id11.id11_android.communicatie

interface IClickListener {
    /**
     * Handle a click on the Like button
     */
    fun likeClicked()

    /**
     * Handle a click on the NoLike button
     */
    fun noLikeClicked()

    /**
     * Handle a click on the Remove button
     */
    fun removeClicked(pos: Int)

    /**
     * Handle a item click
     */
    fun itemClicked(pos: Int)
}