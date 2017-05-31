package swim.regulski.moviebase.ui

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import info.movito.themoviedbapi.Utils
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import swim.regulski.moviebase.R
import swim.regulski.moviebase.model.DataProvider

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
class MovieDetailsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: ConstraintLayout = inflater.inflate(R.layout.fragment_movie_details, container, false) as ConstraintLayout
        val parentActivity = activity as MovieDetailsActivity
        prepare(view, parentActivity.position)
        return view
    }

    private fun prepare(view: View, position: Int) {
        val deferred = async(CommonPool) {
            DataProvider.get(position)
        }

        runBlocking {
            val movie = deferred.await()
            view.titleDetailsTV.text = "${movie.title} (${movie.year})"
            view.ratingBar.rating = movie.rating
            view.descriptionTV.text = movie.overview
            val uri = Utils.createImageUrl(DataProvider.tmdb, movie.backdrop, DataProvider.backdropSizes[1]).toString()

            view.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                movie.rating = rating
            }

            view.movieDetails.post {
                Picasso.with(activity).load(uri).centerCrop().resize(backdropIV.measuredWidth, backdropIV.measuredHeight).into(backdropIV)
            }
        }
    }
}