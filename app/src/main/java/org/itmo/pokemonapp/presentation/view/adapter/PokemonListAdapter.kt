package org.itmo.pokemonapp.presentation.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import org.itmo.pokemonapp.data.model.domain.Pokemon
import org.itmo.pokemonapp.data.model.domain.PokemonShort
import org.itmo.pokemonapp.databinding.ItemPokemonBinding

class PokemonListAdapter(private val onItemClicked: (String) -> Unit) :
    RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    var pokemonList: List<PokemonShort> = emptyList()
        set(value) {
            field = value
            Log.i("retrofit", "Adapter ok")
            notifyDataSetChanged()
        }


    class PokemonViewHolder(val binding: ItemPokemonBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonBinding.inflate(inflater, parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount() = pokemonList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.binding.run {
            name.text = pokemon.name
            Glide.with(image).load(pokemon.imgUrl).into(image)

            root.setOnClickListener {
                onItemClicked.invoke(pokemon.name)
            }
        }
    }


}