package com.accedo.marvel.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.accedo.marvel.R
import com.accedo.marvel.adapters.ClickListener
import com.accedo.marvel.adapters.MarvelPagedListAdapter
import com.accedo.marvel.data.Character
import com.accedo.marvel.viewmodels.CharactersViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.imageview_image
import kotlinx.android.synthetic.main.card_view.view.textview_name
import kotlinx.android.synthetic.main.items_recycler_view.view.*

class CharacterDetailsFragment : Fragment(), ClickListener {
    override fun invoke(p1: Character) {
    }

    private lateinit var viewModel: CharactersViewModel
    lateinit var adapter: MarvelPagedListAdapter

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val orientation = resources.configuration.orientation

        var view = inflater.inflate(R.layout.details_layout, container, false)
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view = inflater.inflate(R.layout.details_landscape_tablet_layout, container, false)
        }
        if (resources.getBoolean(R.bool.isCellphone)) {
            view.recycler_view.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        } else {
            view.recycler_view.layoutManager = GridLayoutManager(context, 3);
        }

        adapter = MarvelPagedListAdapter(true, this);
        val character = arguments?.getSerializable(KEY) as? Character


        view.recycler_view.adapter = adapter
        view.textview_name.text = character?.description
        Picasso.get()
            .load(character?.image)
            .into(view.imageview_image)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[CharactersViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        character?.let { viewModel.initLiveDataComics(it) }

        val observer = Observer<PagedList<Character>> { list ->
            adapter.submitList(list)
        }

        viewModel.liveDataComics.observe(this, observer)

        return view
    }


}