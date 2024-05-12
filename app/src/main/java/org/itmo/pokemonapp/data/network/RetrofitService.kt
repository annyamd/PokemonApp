package org.itmo.pokemonapp.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitService {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()

    val pokemonApi = retrofit.create<PokemonApi>()


    companion object {
        private const val BASE_URL: String = "https://pokeapi.co"
    }

}