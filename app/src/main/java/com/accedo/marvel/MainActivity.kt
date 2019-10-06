package com.accedo.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.accedo.marvel.data.Character
import com.accedo.marvel.viewmodels.CharactersViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = recycler_view
        recyclerView.setHasFixedSize(true);
        val llm = LinearLayoutManager(this);
        recyclerView.layoutManager = llm;

        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)

        val charactersObserver = Observer<List<Character>> { list ->
            println(list.get(0).name)
        }

        charactersViewModel.characters.observe(this,charactersObserver)


    }


}
