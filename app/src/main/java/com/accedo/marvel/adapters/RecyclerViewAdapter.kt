package com.accedo.marvel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.accedo.marvel.R
import com.accedo.marvel.data.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var charactersList:MutableList<Character> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tv_name.text = charactersList[position].name
        viewHolder.bind(charactersList[position].image)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card_view = itemView.card_view
        val image_view = itemView.imageview_image
        val tv_name = itemView.textview_name

        fun bind(image: String) {
            itemView.imageview_image.fromUrl(image)
        }

        fun ImageView.fromUrl(url: String) {
            Picasso.get().load(url).into(this)
        }
    }

    fun setData(images:List<Character>){
        val size = this.charactersList.size
        charactersList.addAll(images)
        val sizeNew = this.charactersList.size
        //notifyDataSetChanged()
        notifyItemRangeInserted(size, sizeNew)
    }

}