package org.itmo.pokemonapp.presentation.viewmodel.pokemon

import org.itmo.pokemonapp.data.model.domain.Pokemon

sealed class PokemonViewState {
    data object Loading : PokemonViewState()
    data class PokemonLoaded(val pokemon: Pokemon) : PokemonViewState()
    data class PokemonLoadError(val message: String?) : PokemonViewState()
}