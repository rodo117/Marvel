package com.accedo.marvel.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI


import com.accedo.marvel.R
import com.accedo.marvel.data.Character
import com.accedo.marvel.viewmodels.CharactersViewModel
import com.accedo.marvel.viewmodels.CustomViewModelFactory


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

        viewModel = ViewModelProviders.of(this,CustomViewModelFactory(isCellphone)).get(CharactersViewModel::class.java)


        val observer = Observer<Character> { character ->
            supportActionBar?.title = character.name;
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val bundle = Bundle()
            bundle.putSerializable(KEY, character)
            navigationController.navigate(R.id.action_charactersFragment_to_characterDetailsFragment, bundle)
        }

        viewModel.liveDataCharacterSelected.observe(this, observer)

    }

    override fun onSupportNavigateUp() = navigationController.navigateUp()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            changeToolBarItems()
        } else
            super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            changeToolBarItems()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeToolBarItems() {
        supportFragmentManager.popBackStack()
        supportActionBar?.setTitle(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}
