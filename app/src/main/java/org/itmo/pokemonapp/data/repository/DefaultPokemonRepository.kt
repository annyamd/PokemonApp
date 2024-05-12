package org.itmo.pokemonapp.data.repository

import android.util.Log
import org.itmo.pokemonapp.data.model.domain.Pokemon
import org.itmo.pokemonapp.data.model.domain.PokemonShort
import org.itmo.pokemonapp.data.model.domain.Result
import org.itmo.pokemonapp.data.model.dto.PokemonDto
import org.itmo.pokemonapp.data.model.dto.PokemonShortDto
import org.itmo.pokemonapp.data.network.PokemonApi
import org.itmo.pokemonapp.data.network.PokemonImageUrlProvider

class DefaultPokemonRepository(private val api: PokemonApi) : PokemonRepository {

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

    override suspend fun getPokemonByName(name: String): Result<Pokemon> =
        try {
            val pokemonDto = api.getPokemon(name)
            mapPokemonDtoToEntity(pokemonDto).let {
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

    private fun mapPokemonDtoToEntity(dto: PokemonDto): Pokemon {
        val imgUrl = PokemonImageUrlProvider.getById(dto.id)
        return Pokemon(
            id = dto.id,
            name = dto.name,
            height = dto.height,
            weight = dto.weight,
            speciesName = dto.species.name,
            types = dto.types.map { it.type.name },
            abilities = dto.abilities.map { it.ability.name },
            stats = dto.stats.associate {
                it.stat.name to it.value
            },
            imgUrl = imgUrl
        )
    }

}