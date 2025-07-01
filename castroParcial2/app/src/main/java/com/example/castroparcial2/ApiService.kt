package com.example.castroparcial2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterResponse>

    @GET("character")
    suspend fun getCharactersByStatus(@Query("status") status: String): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<Character>
}
