package com.accedo.marvel.adapters

import androidx.recyclerview.widget.DiffUtil
import com.accedo.marvel.data.Character

class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        println("CharacterDiffCallback areItemsTheSame")
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {

        println("CharacterDiffCallback areContentsTheSame")
        return oldItem?.id == newItem?.id
                && oldItem?.name == newItem?.name
                && oldItem?.image == newItem?.image    }

}