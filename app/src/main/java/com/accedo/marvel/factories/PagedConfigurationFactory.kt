package com.accedo.marvel.factories

import androidx.paging.PagedList

class PagedConfigurationFactory {

    private val CELLPHONE_VALUE = 10
    private val TABLET_AND_COMICS_VALUE = 20

    fun getConfiguration(isGoingToShowTen: Boolean): PagedList.Config {

        if (isGoingToShowTen) {
            return createConfig(CELLPHONE_VALUE)
        } else {
            return createConfig(TABLET_AND_COMICS_VALUE)
        }

    }

       private fun createConfig(numberOfPages:Int):PagedList.Config{
           return PagedList.Config.Builder()
               .setPageSize(numberOfPages)
               .setEnablePlaceholders(false)
               .build()
       }


}