package org.itmo.pokemonapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.replace
import org.itmo.pokemonapp.R

class MainActivity : AppCompatActivity() {
    private val fragmentFactory = FragmentFactory(::onPokemonItemSelected)

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add<PokemonListFragment>(R.id.frag_container)
                .commit()
        }
    }

    private fun onPokemonItemSelected(pokemonName: String) {
        val argBundle =
            bundleOf(PokemonFragment.POKEMON_NAME to pokemonName)
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace<PokemonFragment>(containerViewId = R.id.frag_container, args = argBundle)
            .addToBackStack(null)
            .commit()
    }
}