package org.itmo.pokemonapp.data.model.domain


sealed interface Result<T> {
    data class Success<T>(val value: T) : Result<T>
    data class Failure<T>(val message: String? = null) : Result<T>

//    enum class FailureType { INTERNET, NOT_SUCCESS }

}