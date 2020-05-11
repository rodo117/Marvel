package com.comics.marvel.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI


import com.comics.marvel.R
import com.comics.marvel.data.marvelapi.Character
import com.comics.marvel.viewmodels.CharactersViewModel
import com.comics.marvel.viewmodels.CustomViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CharactersViewModel
    private lateinit var navigationController: NavController
    private val KEY = "details_character"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationController = findNavController(R.id.navigation_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navigationController)

        val isCellphone = resources.getBoolean(R.bool.isCellphone)

        if(isCellphone){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        viewModel = ViewModelProvider(this,CustomViewModelFactory(isCellphone, application)).get(CharactersViewModel::class.java)


        val observer = Observer<Character> { character ->
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val bundle = Bundle()
            bundle.putSerializable(KEY, character)
            navigationController.navigate(R.id.action_charactersFragment_to_characterDetailsFragment, bundle)
        }

        viewModel.liveDataCharacterSelected.observe(this, observer)

    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()
}
