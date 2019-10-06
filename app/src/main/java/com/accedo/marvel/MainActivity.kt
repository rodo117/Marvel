package com.accedo.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.accedo.marvel.adapters.RecyclerViewAdapter
import com.accedo.marvel.data.Character
import com.accedo.marvel.viewmodels.CharactersViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var charactersViewModel: CharactersViewModel
    lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = recycler_view
        recyclerView.setHasFixedSize(true);
        val llm = LinearLayoutManager(this);
        recyclerView.layoutManager = llm;
        adapter = RecyclerViewAdapter();
        recyclerView.adapter = adapter;

        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)

        val charactersObserver = Observer<List<Character>> { list ->
           adapter.setData(list)
        }

        charactersViewModel.characters.observe(this,charactersObserver)

    }


}
