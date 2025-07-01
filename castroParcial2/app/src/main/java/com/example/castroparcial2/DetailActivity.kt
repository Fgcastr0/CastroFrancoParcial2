package com.example.castroparcial2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import android.widget.ImageView
import com.example.castroparcial2.Character

class DetailActivity : AppCompatActivity() {

    private lateinit var tvSpecies: TextView
    private lateinit var tvGender: TextView
    private lateinit var ivImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val character = intent.getParcelableExtra<Character>("character")

        tvSpecies = findViewById(R.id.textViewLat)
        tvGender = findViewById(R.id.textViewLong)
        ivImage = findViewById(R.id.imageView)

        tvSpecies.text = "Especie: " + (character?.species ?: "Desconocida")
        tvGender.text = "Género: " + (character?.gender ?: "Desconocido")

        character?.image?.let {
            Glide.with(this)
                .load(it)
                .into(ivImage)
        }
    }
}