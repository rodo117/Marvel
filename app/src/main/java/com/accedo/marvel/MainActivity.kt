package com.accedo.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.accedo.marvel.adapters.ClickListener
import com.accedo.marvel.adapters.MarvelPagedListAdapter
import com.accedo.marvel.data.Character
import com.accedo.marvel.datasources.CharactersDataSource
import com.accedo.marvel.viewmodels.CharactersViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ClickListener {

    companion object {
        val MAX_REQUEST = 10
    }
    private lateinit var charactersViewModel: CharactersViewModel
    lateinit var adapter: MarvelPagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)

        adapter = MarvelPagedListAdapter(this);
        initializeList()
    }

    private fun initializeList() {
        recycler_view.layoutManager = LinearLayoutManager(this);
        recycler_view.adapter = adapter
        val config = PagedList.Config.Builder()
            .setPageSize(MAX_REQUEST)
            .setEnablePlaceholders(false)
            .build()

        val liveDataCharacters = initializedPagedListBuilder(config).build()

        liveDataCharacters.observe(this, Observer<PagedList<Character>> { pagedList ->

            adapter.submitList(pagedList)
        })
    }

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Character> {

        val dataSourceFactory = object : DataSource.Factory<Int, Character>() {
            override fun create(): DataSource<Int, Character> {
                return CharactersDataSource()
            }
        }
        return LivePagedListBuilder<Int, Character>(dataSourceFactory, config)
    }

    override fun invoke(characterSeleted: Character) {
    }


}
