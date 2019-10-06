package com.accedo.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = recycler_view
        recyclerView.setHasFixedSize(true);
        val llm = LinearLayoutManager(this);
        recyclerView.layoutManager = llm;


    }


}
