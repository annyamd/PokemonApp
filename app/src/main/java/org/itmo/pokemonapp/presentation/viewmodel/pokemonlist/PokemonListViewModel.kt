package org.itmo.pokemonapp.presentation.viewmodel.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.itmo.pokemonapp.data.model.domain.Result
import org.itmo.pokemonapp.data.repository.PokemonRepository

class PokemonListViewModel(
    private val repository: PokemonRepository,
    initialState: PokemonListState = PokemonListState()
) : ViewModel() {
    private var state = initialState

    private val mutableViewStateFlow = MutableStateFlow(mapStateToViewState())
    val viewStateFlow = mutableViewStateFlow.asStateFlow()

    private val reducer = PokemonListReducer()

    fun submitIntent(intent: PokemonListIntent) {
        state = reducer.reduce(intent, state)
        mutableViewStateFlow.value = mapStateToViewState()

        when (intent) {
            is PokemonListIntent.Init -> {
                loadList()
            }

            is PokemonListIntent.ListLoaded,
            is PokemonListIntent.ListLoadError -> {
            }
        }
    }

    private fun mapStateToViewState() =
        when {
            state.isLoading -> PokemonListViewState.Loading
            state.error == null -> PokemonListViewState.ListLoaded(state.list)
            else -> PokemonListViewState.ListLoadError(state.error)
        }

    private fun loadList() = viewModelScope.launch {
        when (val result = repository.getPokemonList()) {
            is Result.Success -> submitIntent(PokemonListIntent.ListLoaded(result.value))
            is Result.Failure -> submitIntent(PokemonListIntent.ListLoadError(result.message))
        }
    }


    class PokemonListViewModelFactory(
        private val repository: PokemonRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
                return PokemonListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}