package org.itmo.pokemonapp

import android.app.Application
import org.itmo.pokemonapp.data.network.RetrofitService
import org.itmo.pokemonapp.data.repository.DefaultPokemonRepository

class PokemonApplication : Application() {

    private val retrofitService by lazy { RetrofitService() }

    val pokemonApi by lazy {
        retrofitService.pokemonApi
    }

    val pokemonRepository by lazy {
        DefaultPokemonRepository(pokemonApi)
    }

}