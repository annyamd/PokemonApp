package org.itmo.pokemonapp.presentation.viewmodel.pokemon

import org.itmo.pokemonapp.data.model.domain.Pokemon

sealed class PokemonIntent {
    data object Init : PokemonIntent()
    data class PokemonLoaded(val pokemon: Pokemon) : PokemonIntent()
    data class PokemonLoadError(val message: String?) : PokemonIntent()
}