package com.example.castroparcial2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CharacterResponse(
    val results: List<Character>
)

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
) : Parcelable
