package com.comics.marvel.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.comics.marvel.R
import com.comics.marvel.adapters.ClickListener
import com.comics.marvel.adapters.MarvelPagedListAdapter
import com.comics.marvel.data.marvelapi.Character
import com.comics.marvel.viewmodels.CharactersViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_view.view.*
import kotlinx.android.synthetic.main.card_view.view.btn_youtube
import kotlinx.android.synthetic.main.items_recycler_view.view.*

class CharacterDetailsFragment : Fragment(), ClickListener, OnClickFavoriteListener {
    override fun invoke(p1: Character) {
    }

    private lateinit var viewModel: CharactersViewModel
    lateinit var adapter: MarvelPagedListAdapter
    private val KEY = "details_character"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        adapter = MarvelPagedListAdapter(true, this, this);
        val character = arguments?.getSerializable(KEY) as? Character
        (activity as AppCompatActivity ).supportActionBar?.title = character?.name;

        view.recycler_view.adapter = adapter
        view.textview_name.text = character?.description
        view.img_button_favorite.isChecked = character?.isFavorite!!
        view.img_button_favorite.setOnClickListener{
            character?.isFavorite = view.img_button_favorite.isChecked
            viewModel.favoriteCharacterClicked(character.id, view.img_button_favorite.isChecked)
        }
        view.btn_youtube.visibility = View.VISIBLE
        view.btn_youtube.setOnClickListener{
            val bundle = Bundle()
            bundle.putSerializable(KEY, character)
         Navigation.findNavController(view).navigate(R.id.action_characterDetailsFragment_to_youtubeCharacterVideos, bundle)
        }

        Glide.with(view).load(character?.image).placeholder(R.drawable.marvel_placeholder).dontAnimate().into(view.imageview_image)

        viewModel = activity?.run {
            ViewModelProvider(this)[CharactersViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        character?.let { viewModel.initLiveDataComics(it) }

        val observer = Observer<PagedList<Character>> { list ->
            adapter.submitList(list)
        }

        viewModel.liveDataComics.observe(viewLifecycleOwner, observer)

        return view
    }

    override fun favoriteClicked(characterID: String?, isChecked: Boolean) {
    }


}