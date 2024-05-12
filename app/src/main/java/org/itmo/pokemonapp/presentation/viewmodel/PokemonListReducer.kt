package org.itmo.pokemonapp.presentation.viewmodel

class PokemonListReducer {
    fun reduce(intent: PokemonListIntent, state: PokemonListState): PokemonListState {
        return when (intent) {
            is PokemonListIntent.Init -> state.copy(isLoading = true)
            is PokemonListIntent.ListLoaded -> state.copy(list = intent.pokemonList, isLoading = false)
            is PokemonListIntent.ListLoadError -> state.copy(error = intent.message, isLoading = false)
        }
    }
}