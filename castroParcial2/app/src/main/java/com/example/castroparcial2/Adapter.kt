package com.example.castroparcial2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.castroparcial2.Character

class Adapter(private val characters: List<Character>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var onItemClickListener: (Character) -> Unit

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.name)
        private val imgCharacter: ImageView = view.findViewById(R.id.image)

        fun bind(character: Character) {
            tvName.text = character.name
            Glide.with(view.context).load(character.image).into(imgCharacter)

            view.setOnClickListener {
                onItemClickListener(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }
}