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
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.comics.marvel.R
import com.comics.marvel.adapters.ClickListener
import com.comics.marvel.adapters.MarvelPagedListAdapter
import com.comics.marvel.data.marvelapi.Character
import com.comics.marvel.room.entities.FavoriteCharacter
import com.comics.marvel.viewmodels.CharactersViewModel
import kotlinx.android.synthetic.main.items_recycler_view.view.*

class CharactersFragment : Fragment(), ClickListener, OnClickFavoriteListener {

    lateinit var adapter: MarvelPagedListAdapter
    private lateinit var viewModel: CharactersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.items_recycler_view, container, false)

        val isCellphone = resources.getBoolean(R.bool.isCellphone)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.app_name);
        viewModel = activity?.run {
            ViewModelProvider(this)[CharactersViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        adapter = MarvelPagedListAdapter(clickListener=this, favoriteClickListener = this);

        if(isCellphone){
            view.recycler_view.layoutManager = LinearLayoutManager(context);
        }else {
            val orientation = resources.configuration.orientation
            if(orientation== Configuration.ORIENTATION_PORTRAIT) {
                view.recycler_view.layoutManager = GridLayoutManager(context, 3);
            }else {
                view.recycler_view.layoutManager = GridLayoutManager(context, 5);
            }
        }

        view.recycler_view.adapter = adapter

        val favoriteCharacterObserver = Observer<List<FavoriteCharacter>> { list ->
            adapter.setFavoriteCharacters(list)
        }

        val observer = Observer<PagedList<Character>> { list ->
            viewModel.favoriteCharactersLiveData.observe(viewLifecycleOwner, favoriteCharacterObserver)
            adapter.submitList(list)

        }

        viewModel.liveDataCharacters.observe(viewLifecycleOwner, observer)

        return view
    }

    override fun invoke(characterSelected: Character) {
        viewModel.liveDataCharacterSelected.value = characterSelected
    }

    override fun favoriteClicked(characterID: String?, isChecked: Boolean) {
        characterID?.apply {  viewModel.favoriteCharacterClicked(this, isChecked) }
    }

}

interface OnClickFavoriteListener {
    fun favoriteClicked (characterID :String?, isChecked:Boolean)
}