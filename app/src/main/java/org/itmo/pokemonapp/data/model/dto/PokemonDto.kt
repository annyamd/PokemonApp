package org.itmo.pokemonapp.data.model.dto

import com.google.gson.annotations.SerializedName

data class PokemonDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val species: NamedResource,
    val types: List<TypeDto>,
    val abilities: List<AbilityDto>,
    val stats: List<StatDto>
)

data class TypeDto(val type: NamedResource)

data class AbilityDto(val ability: NamedResource)

data class NamedResource(val name: String, val url: String)

data class StatDto(
    val stat: NamedResource,
    @SerializedName("base_stat")
    val value: Int
)