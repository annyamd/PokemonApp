package org.itmo.pokemonapp.presentation.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class FragmentFactory(private val onPokemonSelected: (String) -> Unit) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            PokemonListFragment::class.java.name -> PokemonListFragment(onPokemonSelected)
            PokemonFragment::class.java.name -> PokemonFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}