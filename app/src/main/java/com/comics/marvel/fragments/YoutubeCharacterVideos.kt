package com.comics.marvel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.comics.marvel.R
import com.comics.marvel.adapters.YoutubeRecyclerAdapter
import com.comics.marvel.data.marvelapi.Character
import com.comics.marvel.data.youtubeapi.Video
import com.comics.marvel.viewmodels.YoutubeViewModel
import kotlinx.android.synthetic.main.items_recycler_view.view.*

class YoutubeCharacterVideos:Fragment() {

    private val KEY = "details_character"

    private lateinit var viewModel: YoutubeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.items_recycler_view, container, false)

        val character = arguments?.getSerializable(KEY) as? Character
        viewModel = ViewModelProvider(this).get(YoutubeViewModel::class.java)
        character?.name?.let { viewModel.getCharacterVideos(it) }

        val adapter = YoutubeRecyclerAdapter()
        view.recycler_view.layoutManager = LinearLayoutManager(context);
        view.recycler_view.adapter = adapter

        val observer = Observer<List<Video>> { list ->
           adapter.setList(list)
        }

        viewModel.liveDataVideos.observe(viewLifecycleOwner, observer)

       return view
    }

}