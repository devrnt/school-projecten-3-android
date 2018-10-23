package com.talentcoach.id11.id11_android


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_werkaanbieding_buttons.*

class WerkaanbiedingButtonsFragment : Fragment() {
    var iClickListener: IClickListener? = null // will be set to WerkaanbiedingActivity
    var onlyRemove = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_werkaanbieding_buttons, container, false)
    }

    override fun onResume() {
        super.onResume()
        like.setOnClickListener {
            iClickListener?.likeClicked()
        }

        noLike.setOnClickListener {
            iClickListener?.noLikeClicked()
        }

        if (onlyRemove){
            like.visibility = View.GONE
        }
    }

}
