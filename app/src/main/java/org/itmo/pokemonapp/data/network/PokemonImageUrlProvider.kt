package org.itmo.pokemonapp.data.network

class PokemonImageUrlProvider {

    companion object {
        private const val PATH: String =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork"
        private const val EXT: String = "png"

        fun getById(pokemonId: Int) = "$PATH/$pokemonId.$EXT"

        fun getByUrl(pokemonUrl: String): String {
            //  pokemon url format "https://pokeapi.co/api/v2/pokemon/132/"
            val idEndIndex = pokemonUrl.lastIndex
            val idStartIndex = pokemonUrl.substring(0, idEndIndex).lastIndexOf('/') + 1
            val pokemonId = pokemonUrl.substring(idStartIndex, idEndIndex).toInt()

            return getById(pokemonId)
        }
    }

}