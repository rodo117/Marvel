package com.comics.marvel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comics.marvel.R
import com.comics.marvel.data.marvelapi.Character
import com.comics.marvel.fragments.OnClickFavoriteListener
import com.comics.marvel.room.entities.FavoriteCharacter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

typealias ClickListener = (Character) -> Unit
class MarvelPagedListAdapter(private val isDetails:Boolean = false, private val clickListener: ClickListener, private val favoriteClickListener: OnClickFavoriteListener) : PagedListAdapter<Character,MarvelPagedListAdapter.ViewHolder >(
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
        viewHolder.setFavoriteCharacter(character?.isFavorite)
        viewHolder.btnFavorite?.setOnClickListener {
           favoriteClickListener.favoriteClicked(character?.id, viewHolder.btnFavorite?.isChecked )
            character?.isFavorite = viewHolder.btnFavorite?.isChecked
        }

        with(viewHolder){
            character?.let {
                itemView.setOnClickListener{
                    clickListener(character)
                }
            }
        }
    }

    fun setFavoriteCharacters(favoriteCharacterList: List<FavoriteCharacter>) {
        GlobalScope.launch(Dispatchers.Default) {
            favoriteCharacterList?.forEach {
                currentList?.forEach { character ->
                    if (character.id == it.id)
                        character.isFavorite = true
                }
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val card_view = itemView.card_view
        val image_view = itemView.imageview_image
        val tv_name = itemView.textview_name
        val btnFavorite = itemView.img_button_favorite

        fun bind(image: String?) {
            image?.let { itemView.imageview_image?.fromUrl(it) }
        }

        fun setFavoriteCharacter(isFavorite:Boolean? ){
            isFavorite?.let { btnFavorite?.isChecked = it }
        }

        fun ImageView.fromUrl(url: String) {
            Glide.with(itemView).load(url).placeholder(R.drawable.marvel_placeholder).dontAnimate().into(this)
        }
    }

}