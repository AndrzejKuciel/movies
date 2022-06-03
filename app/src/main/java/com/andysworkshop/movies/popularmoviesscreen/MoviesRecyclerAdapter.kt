package com.andysworkshop.movies.popularmoviesscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andysworkshop.movies.databinding.PosterViewBinding
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIData
import com.squareup.picasso.Picasso

class MoviesRecyclerAdapter(
    private val values: List<PopularMoviesUIData>,
    private val onItemClick: (PopularMoviesUIData, Int) -> Unit
) : RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PosterViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Picasso.get().load(item.posterPath).into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(binding: PosterViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val posterImageView = binding.posterImageView

        init {
            itemView.setOnClickListener {
                onItemClick(values[adapterPosition], adapterPosition)
            }
        }
    }
}