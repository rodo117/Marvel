package com.accedo.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.accedo.marvel.adapters.RecyclerViewAdapter
import com.accedo.marvel.data.Character
import com.accedo.marvel.viewmodels.CharactersViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var charactersViewModel: CharactersViewModel
    lateinit var adapter: RecyclerViewAdapter
    var isScrolling = false
    var currentItems: Int = 0
    var totalItems: Int = 0
    var scrollOutItems : Int = 0
    lateinit var manager:LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)

        val recyclerView = recycler_view
        recyclerView.setHasFixedSize(true);
        manager = LinearLayoutManager(this);
        recyclerView.layoutManager = manager;
        adapter = RecyclerViewAdapter();
        recyclerView.adapter = adapter;



        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.childCount
                totalItems = manager.itemCount
                scrollOutItems = manager.findFirstVisibleItemPosition()


                if (isScrolling && (currentItems+scrollOutItems == totalItems)) {
                    isScrolling = false
                    charactersViewModel.getCharacteres()
                }
            }

        })


        val charactersObserver = Observer<List<Character>> { list ->
            adapter.setData(list)
        }

        charactersViewModel.characters.observe(this, charactersObserver)

    }


}
