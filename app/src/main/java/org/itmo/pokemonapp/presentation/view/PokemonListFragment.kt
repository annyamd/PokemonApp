package org.itmo.pokemonapp.presentation.view

import android.os.Bundle
import android.util.Log
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
import org.itmo.pokemonapp.presentation.viewmodel.pokemonlist.PokemonListIntent
import org.itmo.pokemonapp.presentation.viewmodel.pokemonlist.PokemonListViewModel
import org.itmo.pokemonapp.presentation.viewmodel.pokemonlist.PokemonListViewState

class PokemonListFragment(private val onPokemonSelected: (String) -> Unit) : Fragment() {

    private var binding: FragmentPokemonListBinding? = null
    private val viewModel: PokemonListViewModel by viewModels {
        val application = requireActivity().application as? PokemonApplication
            ?: throw IllegalStateException("Application must be PokemonApplication")
        PokemonListViewModel.PokemonListViewModelFactory(application.pokemonRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            pokemonListRv.layoutManager = LinearLayoutManager(context)
            pokemonListRv.adapter = pokemonListRv.adapter ?: PokemonListAdapter(::onPokemonClicked)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFlow.collect {
                    updateUi(it)
                }
            }
        }

        if (savedInstanceState == null) {
            viewModel.submitIntent(PokemonListIntent.Init)
        }

    }

    private fun onPokemonClicked(name: String) {
        onPokemonSelected.invoke(name)
    }

    private fun updateUi(state: PokemonListViewState) {
        when (state) {
            PokemonListViewState.Loading -> {
                binding?.run {
                    Log.i("retrofit", "loading state")
                    progressBar.isVisible = true
                    pokemonListRv.isVisible = false
                }
            }

            is PokemonListViewState.ListLoaded -> {
                binding?.run {
                    (pokemonListRv.adapter as PokemonListAdapter).pokemonList = state.pokemonList
                    progressBar.isVisible = false
                    pokemonListRv.isVisible = true
                }
            }

            is PokemonListViewState.ListLoadError -> {
                binding?.progressBar?.isVisible = false
                Toast.makeText(
                    context, "Error! message: ${state.message}!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.pokemonListRv?.adapter = null
        binding = null
    }

}