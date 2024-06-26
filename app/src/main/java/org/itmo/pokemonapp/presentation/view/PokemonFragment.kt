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
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.itmo.pokemonapp.PokemonApplication
import org.itmo.pokemonapp.R
import org.itmo.pokemonapp.data.model.domain.Pokemon
import org.itmo.pokemonapp.databinding.FragmentPokemonBinding
import org.itmo.pokemonapp.presentation.viewmodel.pokemon.PokemonIntent
import org.itmo.pokemonapp.presentation.viewmodel.pokemon.PokemonViewModel
import org.itmo.pokemonapp.presentation.viewmodel.pokemon.PokemonViewState

class PokemonFragment : Fragment() {
    private var pokemonName: String? = null

    private var binding: FragmentPokemonBinding? = null
    private val viewModel: PokemonViewModel by viewModels {
        val application = requireActivity().application as? PokemonApplication
            ?: throw IllegalStateException("Application must be PokemonApplication")
        PokemonViewModel.PokemonViewModelFactory(pokemonName!!, application.pokemonRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonName = requireArguments().getString(POKEMON_NAME)
        this.pokemonName = pokemonName

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFlow.collect {
                    updateUi(it)
                }
            }
        }

        viewModel.submitIntent(PokemonIntent.Init)
    }

    private fun updateUi(state: PokemonViewState) {
        when (state) {
            PokemonViewState.Loading -> {
                binding?.run {
                    progressBar.isVisible = true
                    pokemonLayout.isVisible = false
                }
            }

            is PokemonViewState.PokemonLoaded -> {
                bindPokemonToView(state.pokemon)

                binding?.run {
                    progressBar.isVisible = false
                    pokemonLayout.isVisible = true
                }
            }

            is PokemonViewState.PokemonLoadError -> {
                binding?.progressBar?.isVisible = false
                Toast.makeText(
                    context, "Error! message: ${state.message}!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun bindPokemonToView(pokemon: Pokemon) {
        binding?.run {
            pokemonName.text = pokemon.name
            pokemonSpecies.text = pokemon.speciesName
            pokemonHeight.text = pokemon.height.toString()
            pokemonWeight.text = pokemon.weight.toString()
            pokemonTypes.text = pokemon.types.joinToString(separator = ", ")
            pokemonAbilities.text = pokemon.abilities.joinToString(separator = ", ")
            chipAttack.text = getString(R.string.stat_attack, pokemon.stats["attack"])
            chipDefense.text = getString(R.string.stat_defense, pokemon.stats["defense"])
            chipHp.text = getString(R.string.stat_hp, pokemon.stats["hp"])
            chipSpecialDefense.text =
                getString(R.string.stat_special_defense, pokemon.stats["special-defense"])
            chipSpeed.text = getString(R.string.stat_speed, pokemon.stats["speed"])

            Glide.with(image).load(pokemon.imgUrl).into(image)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val POKEMON_NAME: String = "pokemon name"
    }

}