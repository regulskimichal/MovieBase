package swim.regulski.moviebase.ui

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_movie_details.*
import swim.regulski.moviebase.R
import swim.regulski.moviebase.model.Movie
import swim.regulski.moviebase.model.Shared.temp

class MovieDetailsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val position: Int = intent.getIntExtra("position", -1)
        val movie: Movie = temp[position]

        titleTV.text = "${movie.title} (${movie.year})"
        ratingBar.rating = movie.rating
        descriptionTV.text = movie.description

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            temp[position].rating = rating
        }
    }
}
