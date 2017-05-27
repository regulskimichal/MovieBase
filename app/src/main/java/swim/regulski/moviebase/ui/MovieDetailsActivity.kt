package swim.regulski.moviebase.ui

import android.app.Activity
import android.os.Bundle
import android.view.ViewTreeObserver
import com.squareup.picasso.Picasso
import info.movito.themoviedbapi.Utils
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import swim.regulski.moviebase.R
import swim.regulski.moviebase.model.DataProvider

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
class MovieDetailsActivity : Activity() {
    var uri: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val position = intent.getIntExtra("position", -1)
        prepare(position)
    }

    private fun prepare(position: Int) {
        val deferred = async(CommonPool) {
            DataProvider.get(position)
        }

        movieDetails.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                movieDetails.viewTreeObserver.removeOnGlobalLayoutListener(this)
                Picasso.with(this@MovieDetailsActivity).load(uri).centerCrop().resize(backdropIV.measuredWidth, backdropIV.measuredHeight).into(backdropIV)
            }
        })

        runBlocking {
            val movie = deferred.await()
            titleDetailsTV.text = "${movie.title} (${movie.year})"
            ratingBar.rating = movie.rating
            descriptionTV.text = movie.overview
            uri = Utils.createImageUrl(DataProvider.tmdb, movie.backdrop, DataProvider.backdropSizes[1]).toString()

            ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                movie.rating = rating
            }
        }
    }
}
