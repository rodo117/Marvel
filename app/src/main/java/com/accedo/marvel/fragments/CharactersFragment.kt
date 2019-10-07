package com.accedo.marvel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.accedo.marvel.R
import com.accedo.marvel.adapters.ClickListener
import com.accedo.marvel.adapters.MarvelPagedListAdapter
import com.accedo.marvel.data.Character
import com.accedo.marvel.viewmodels.CharactersViewModel
import kotlinx.android.synthetic.main.items_recycler_view.view.*

class CharactersFragment : Fragment(), ClickListener {

    lateinit var adapter: MarvelPagedListAdapter
    private lateinit var viewModel: CharactersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.items_recycler_view, container, false)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[CharactersViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        adapter = MarvelPagedListAdapter(this);
        view.recycler_view.layoutManager = LinearLayoutManager(context);
        view.recycler_view.adapter = adapter

        val observer = Observer<PagedList<Character>> { list ->
            adapter.submitList(list)
        }

        viewModel.liveDataCharacters.observe(this, observer)

        return view
    }

    override fun invoke(characterSelected: Character) {
        viewModel.liveDataCharacterSelected.value = characterSelected
    }


}