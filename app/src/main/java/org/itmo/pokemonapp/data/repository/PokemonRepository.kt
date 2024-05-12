package org.itmo.pokemonapp.data.repository

import org.itmo.pokemonapp.data.model.domain.PokemonShort
import org.itmo.pokemonapp.data.model.domain.Result


interface PokemonRepository{

    suspend fun getPokemonList() : Result<List<PokemonShort>>
}