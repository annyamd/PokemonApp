package org.itmo.pokemonapp.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.itmo.pokemonapp.PokemonApplication
import org.itmo.pokemonapp.databinding.FragmentPokemonListBinding
import org.itmo.pokemonapp.presentation.view.adapter.PokemonListAdapter
import org.itmo.pokemonapp.presentation.viewmodel.PokemonListIntent
import org.itmo.pokemonapp.presentation.viewmodel.PokemonListViewModel
import org.itmo.pokemonapp.presentation.viewmodel.PokemonListViewState

class PokemonListFragment : Fragment() {

    private var binding: FragmentPokemonListBinding? = null
    private val viewModel: PokemonListViewModel by viewModels {
        val application = requireActivity().application as? PokemonApplication
            ?: throw IllegalStateException("Application must be PokemonApplication")
        PokemonListViewModel.PokemonListViewModelFactory(application.pokemonRepository)
    }

    private var pokemonListAdapter: PokemonListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonListAdapter = pokemonListAdapter ?: PokemonListAdapter(::onPokemonClicked)
        binding?.run {
            pokemonListRv.layoutManager = LinearLayoutManager(context)
            pokemonListRv.adapter = pokemonListAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFlow.collect {
                    updateUi(it)
                }
            }
        }

        viewModel.submitIntent(PokemonListIntent.Init)
    }

    private fun onPokemonClicked(name: String) {
        Toast.makeText(context, "Clicked on: $name!", Toast.LENGTH_SHORT).show()
    }

    private fun updateUi(state: PokemonListViewState) {
        when (state) {
            PokemonListViewState.Loading -> {
                binding?.run {
                    progressBar.isVisible = true
                    pokemonListRv.isVisible = false
                }
            }

            is PokemonListViewState.ListLoaded -> {
                pokemonListAdapter?.pokemonList = state.pokemonList
                binding?.run {
                    progressBar.isVisible = false
                    pokemonListRv.isVisible = true
                }
            }

            is PokemonListViewState.ListLoadError -> Toast.makeText(
                context, "Error! message: ${state.message}!", Toast.LENGTH_SHORT
            ).show()
        }
    }


}