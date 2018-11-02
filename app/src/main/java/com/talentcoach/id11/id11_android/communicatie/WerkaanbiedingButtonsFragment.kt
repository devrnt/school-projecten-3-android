package com.talentcoach.id11.id11_android.communicatie


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.talentcoach.id11.id11_android.R
import kotlinx.android.synthetic.main.fragment_werkaanbieding_buttons.*

class WerkaanbiedingButtonsFragment : Fragment() {
    var iClickListener: IClickListener? = null // will be set to WerkaanbiedingActivity
    var onlyRemove = false
        set(value) {
            field = value
            like?.visibility = if (value) View.GONE else View.VISIBLE
        }

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
            if (onlyRemove)
                iClickListener?.removeClicked(-1)
            else
                iClickListener?.noLikeClicked()
        }

    }

}
