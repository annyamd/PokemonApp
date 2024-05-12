package org.itmo.pokemonapp.data.repository

import android.util.Log
import org.itmo.pokemonapp.data.model.domain.PokemonShort
import org.itmo.pokemonapp.data.model.domain.Result
import org.itmo.pokemonapp.data.model.dto.PokemonShortDto
import org.itmo.pokemonapp.data.network.PokemonApi
import org.itmo.pokemonapp.data.network.PokemonImageUrlProvider

class DefaultPokemonRepository(private val api: PokemonApi) : PokemonRepository{

    //trycatch
    override suspend fun getPokemonList(): Result<List<PokemonShort>> =
        try {
            val pokemonListResponse = api.getPokemonList(0, 100)
            pokemonListResponse.results.map(::mapPokemonShortDtoToEntity).let {
                Result.Success(it)
            }
        } catch (ex: Exception) {
            Log.i("retrofit", ex.stackTraceToString())
            Result.Failure(ex.message)
        }

    private fun mapPokemonShortDtoToEntity(dto: PokemonShortDto): PokemonShort {
        val imgUrl = PokemonImageUrlProvider.getByUrl(dto.url)
        return PokemonShort(
            name = dto.name,
            imgUrl = imgUrl
        )
    }

}