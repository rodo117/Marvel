package com.accedo.marvel.activities

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.accedo.marvel.R
import com.accedo.marvel.data.Character
import com.accedo.marvel.fragments.CharacterDetailsFragment
import com.accedo.marvel.fragments.CharactersFragment
import com.accedo.marvel.viewmodels.CharactersViewModel
import com.accedo.marvel.viewmodels.CustomViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isCellphone = resources.getBoolean(R.bool.isCellphone)

        if(isCellphone){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        viewModel = ViewModelProviders.of(this,CustomViewModelFactory(isCellphone)).get(CharactersViewModel::class.java)

        supportFragmentManager.beginTransaction().add(R.id.frame_layout, CharactersFragment())
            .commit()

        val observer = Observer<Character> { character ->
            supportActionBar?.setTitle(character.name);
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, CharacterDetailsFragment.newInstance(character)).addToBackStack(null)
                .commit()
        }

        viewModel.liveDataCharacterSelected.observe(this, observer)

    }

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
