package com.talentcoach.id11.id11_android.communicatie


import android.os.Bundle
import android.support.design.chip.Chip
import android.support.design.chip.ChipGroup
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.talentcoach.id11.id11_android.R
import kotlinx.android.synthetic.main.fragment_interesses.*

/**
 * A simple [Fragment] subclass.
 *
 */
class InteressesFragment : Fragment() {

    lateinit var interesses: List<String>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_interesses, container, false)

        // TODO("Alle interesses ophalen (uit alle werkaanbiedingen)")
        interesses = listOf("Teamwork", "Computers", "Leergierigheid", "Creativiteit", "Haarzorg")
        var chipGroup = view.findViewById(R.id.chipGroup) as ChipGroup

        for (index in interesses) {
            val chip = Chip(chipGroup.context)
            chip.text= index
            chip.setChipBackgroundColorResource(R.color.bg_chip_state_list)
            chip.setTextAppearance(R.style.whiteTextSelected)
//            chip.setChipBackgroundColorResource(R.color.bg_chip_state_list)
//            chip.setChipCo(R.color.text_color_chip_state_list)
//            chip.setTextAppearanceResource(R.style.Widget_MaterialComponents_Chip_Choice)

            // necessary to get single selection working
            chip.isClickable = true
            chip.isCheckable = true
            chipGroup.addView(chip)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Tab1Fragment.
         */
        @JvmStatic
        fun newInstance() = InteressesFragment()
    }

}
