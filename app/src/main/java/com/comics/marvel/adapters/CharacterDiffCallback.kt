package com.comics.marvel.adapters

import androidx.recyclerview.widget.DiffUtil
import com.comics.marvel.data.marvelapi.Character

class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem?.id == newItem?.id
                && oldItem?.name == newItem?.name
                && oldItem?.thumbnail.path == newItem?.thumbnail.path    }

}