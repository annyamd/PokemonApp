package org.itmo.pokemonapp.presentation.viewmodel.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.itmo.pokemonapp.data.model.domain.Result
import org.itmo.pokemonapp.data.repository.PokemonRepository

class PokemonViewModel(
    private val pokemonName: String,
    private val repository: PokemonRepository,
    initialState: PokemonState = PokemonState()
) : ViewModel() {
    private var state: PokemonState = initialState

    private val mutableViewStateFlow = MutableStateFlow(mapStateToViewState())
    val viewStateFlow = mutableViewStateFlow.asStateFlow()

    private val reducer = PokemonReducer()

    fun submitIntent(intent: PokemonIntent) {
        state = reducer.reduce(intent, state)
        mutableViewStateFlow.value = mapStateToViewState()

        when (intent) {
            is PokemonIntent.Init -> {
                loadPokemon()
            }

            is PokemonIntent.PokemonLoaded,
            is PokemonIntent.PokemonLoadError -> {
            }
        }
    }

    private fun mapStateToViewState() =
        when {
            state.isLoading -> PokemonViewState.Loading
            state.error == null -> PokemonViewState.PokemonLoaded(state.pokemon!!)
            else -> PokemonViewState.PokemonLoadError(state.error)
        }

    private fun loadPokemon() = viewModelScope.launch {
        when (val result = repository.getPokemonByName(pokemonName)) {
            is Result.Success -> submitIntent(PokemonIntent.PokemonLoaded(result.value))
            is Result.Failure -> submitIntent(PokemonIntent.PokemonLoadError(result.message))
        }
    }


    class PokemonViewModelFactory(
        private val pokemonName: String,
        private val repository: PokemonRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
                return PokemonViewModel(pokemonName, repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}