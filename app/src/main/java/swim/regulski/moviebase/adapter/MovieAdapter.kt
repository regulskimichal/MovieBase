package swim.regulski.moviebase.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import info.movito.themoviedbapi.Utils
import kotlinx.android.synthetic.main.data.view.*
import kotlinx.android.synthetic.main.data_reversed.view.*
import swim.regulski.moviebase.R
import swim.regulski.moviebase.model.DataProvider
import swim.regulski.moviebase.model.Movie

class MovieAdapter(val context: Context, val movieList: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView: View

        if (viewType == 0) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
        } else {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_card_reversed, parent, false)
        }

        return MovieHolder(itemView, viewType)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = movieList[position]
        holder.title.text = "${movie.title} (${movie.year})"
        val uri: String = Utils.createImageUrl(DataProvider.tmdb, movie.poster, DataProvider.posterSizes[0]).toString()
        Picasso.with(context).load(uri).into(holder.poster)
    }

    override fun getItemCount(): Int = movieList.size

    override fun getItemViewType(position: Int): Int = position % 2

    fun remove(position: Int) {
        movieList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - 1)
    }

    inner class MovieHolder(view: View, viewType: Int) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var poster: ImageView

        init {
            if (viewType == 0) {
                title = view.titleTV
                poster = view.posterIV
            } else {
                title = view.titleReversedTV
                poster = view.posterReversedIV
            }
        }
    }
}