package org.itmo.pokemonapp.data.model.domain


data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val speciesName: String,
    val types: List<String>,
    val abilities: List<String>,
    val stats: Map<String, Int>,
    val imgUrl: String
)
