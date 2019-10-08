package com.accedo.marvel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.accedo.marvel.R
import com.accedo.marvel.data.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.*

typealias ClickListener = (Character) -> Unit
class MarvelPagedListAdapter(private val isDetails:Boolean = false, private val clickListener: ClickListener) : PagedListAdapter<Character,MarvelPagedListAdapter.ViewHolder >(
    CharacterDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(!isDetails) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false))
        }else{
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view_details, parent, false))
        }
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val character = getItem(position)
        viewHolder.tv_name.text = character?.name
        viewHolder.bind(character?.image)

        with(viewHolder){
            character?.let {
                itemView.setOnClickListener{
                    clickListener(character)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val card_view = itemView.card_view
        val image_view = itemView.imageview_image
        val tv_name = itemView.textview_name

        fun bind(image: String?) {
            image?.let { itemView.imageview_image?.fromUrl(it) }
        }

        fun ImageView.fromUrl(url: String) {
            Picasso.get().load(url).into(this)
        }
    }

}