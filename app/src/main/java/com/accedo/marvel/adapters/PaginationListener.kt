package com.accedo.marvel.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.annotation.NonNull


abstract class PaginationListener : RecyclerView.OnScrollListener() {

    val PAGE_START = 1

    lateinit var layoutManager: LinearLayoutManager

    val PAGE_SIZE = 10


    fun paginationListener(@NonNull layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager

    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE
            ) {
                loadMoreItems();
            }
        }


    }

    protected abstract fun loadMoreItems();

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean


}