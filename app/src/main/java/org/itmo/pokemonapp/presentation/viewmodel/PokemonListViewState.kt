package org.itmo.pokemonapp.presentation.viewmodel

import org.itmo.pokemonapp.data.model.domain.PokemonShort

sealed class PokemonListViewState {
    data object Loading : PokemonListViewState()
    data class ListLoaded(val pokemonList: List<PokemonShort>) : PokemonListViewState()
    data class ListLoadError(val message: String?) : PokemonListViewState()
}