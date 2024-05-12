package org.itmo.pokemonapp.presentation.viewmodel

import org.itmo.pokemonapp.data.model.domain.PokemonShort

sealed class PokemonListIntent {
    data object Init : PokemonListIntent()
    data class ListLoaded(val pokemonList: List<PokemonShort>) : PokemonListIntent()
    data class ListLoadError(val message: String?) : PokemonListIntent()
}