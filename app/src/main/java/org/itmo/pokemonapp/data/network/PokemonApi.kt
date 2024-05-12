package org.itmo.pokemonapp.data.network

import org.itmo.pokemonapp.data.model.dto.PokemonDto
import org.itmo.pokemonapp.data.model.dto.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("/api/v2/pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse

    @GET("/api/v2/pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): PokemonDto

}