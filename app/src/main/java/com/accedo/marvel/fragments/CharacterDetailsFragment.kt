package com.accedo.marvel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.accedo.marvel.R
import com.accedo.marvel.data.Character
import com.accedo.marvel.viewmodels.CharactersViewModel

class CharacterDetailsFragment : Fragment() {

    private lateinit var viewModel: CharactersViewModel

    companion object {
        private val KEY = "details_character"
        fun newInstance(characterSelected: Character): CharacterDetailsFragment {
            val args = Bundle()
            args.putSerializable(KEY, characterSelected)
            val fragment = CharacterDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.details_layout, container, false)

        val character = arguments?.getSerializable(KEY) as? Character

        viewModel = activity?.run {
            ViewModelProviders.of(this)[CharactersViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        println(character?.name)


        return view
    }


}