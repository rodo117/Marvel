package com.accedo.marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.accedo.marvel.data.Character
import com.accedo.marvel.fragments.CharacterDetailsFragment
import com.accedo.marvel.fragments.CharactersFragment
import com.accedo.marvel.viewmodels.CharactersViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CharactersViewModel
    val PAGES_SIZE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)

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


    fun configPaging(){
        val config = PagedList.Config.Builder()
            .setPageSize(PAGES_SIZE)
            .setEnablePlaceholders(false)
            .build()
    }

}
