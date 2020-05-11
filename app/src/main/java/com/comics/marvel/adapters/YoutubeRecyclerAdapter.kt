package com.comics.marvel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.comics.marvel.R
import com.comics.marvel.data.youtubeapi.Video
import kotlinx.android.synthetic.main.youtube_character_item.view.*

class YoutubeRecyclerAdapter: RecyclerView.Adapter<YoutubeRecyclerAdapter.ViewHolder>() {

     var videoList =  listOf<Video>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeRecyclerAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.youtube_character_item, parent, false))
    }

    override fun getItemCount(): Int {
       return  videoList.size
    }

    override fun onBindViewHolder(holder: YoutubeRecyclerAdapter.ViewHolder, position: Int) {
        val item = videoList[position]
        holder.bind(item)
    }

    fun setList ( list: List<Video>){
        this.videoList = list
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tv_title = itemView.tv_title
        val tv_description = itemView.tv_description
        val iv_video_thumbnail = itemView.iv_video_thumbnail

        fun bind (item:Video){
            tv_title.text = item.title
            tv_description.text = item.description
            itemView.iv_video_thumbnail?.fromUrl(item.image)
        }

        private fun ImageView.fromUrl(url: String) {
            Glide.with(itemView).load(url).placeholder(R.drawable.marvel_placeholder).dontAnimate().into(this)
        }


    }

}