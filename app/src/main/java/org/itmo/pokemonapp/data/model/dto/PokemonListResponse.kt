package org.itmo.pokemonapp.data.model.dto

data class PokemonListResponse (
    val count: Int,
    val results: List<PokemonShortDto>
)