package org.itmo.pokemonapp.presentation.viewmodel.pokemon

import org.itmo.pokemonapp.data.model.domain.Pokemon

data class PokemonState (
    val pokemon: Pokemon? = null,
    val error: String? = null,
    val isLoading: Boolean = true,
)