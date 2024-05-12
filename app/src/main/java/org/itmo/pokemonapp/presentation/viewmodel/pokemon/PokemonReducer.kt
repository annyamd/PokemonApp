package org.itmo.pokemonapp.presentation.viewmodel.pokemon

class PokemonReducer {
    fun reduce(intent: PokemonIntent, state: PokemonState): PokemonState {
        return when (intent) {
            is PokemonIntent.Init -> state.copy(isLoading = true)
            is PokemonIntent.PokemonLoaded -> state.copy(pokemon = intent.pokemon, isLoading = false)
            is PokemonIntent.PokemonLoadError -> state.copy(error = intent.message, isLoading = false)
        }
    }
}