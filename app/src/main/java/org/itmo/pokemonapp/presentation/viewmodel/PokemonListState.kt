package org.itmo.pokemonapp.presentation.viewmodel

import org.itmo.pokemonapp.data.model.domain.PokemonShort

data class PokemonListState (
    val list: List<PokemonShort> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = true,
)