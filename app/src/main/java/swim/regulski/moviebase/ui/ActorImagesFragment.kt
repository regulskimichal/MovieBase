package swim.regulski.moviebase.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import info.movito.themoviedbapi.Utils
import kotlinx.android.synthetic.main.fragment_actor_images.view.*
import swim.regulski.moviebase.R
import swim.regulski.moviebase.libs.SquareImageView
import swim.regulski.moviebase.model.DataProvider

class ActorImagesFragment : Fragment() {
    val bigActorImages = ArrayList<SquareImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_actor_images, container, false)
        val parentActivity = activity as MovieDetailsActivity
        prepare(view, parentActivity.position)
        return view
    }

    private fun prepare(view: View, position: Int) {
        prepareViews(view)

        val size = if (DataProvider.profileSizes[1] != null) {
            DataProvider.profileSizes[1]
        } else {
            DataProvider.profileSizes.last()
        }

        bigActorImages.forEachIndexed {
            index, it ->
            val uri = Utils.createImageUrl(DataProvider.tmdb, DataProvider.movieList[position].cast[index].profileImage, size).toString()
            view.actorImagesCL.post {
                Picasso.with(activity).load(uri).centerCrop().resize(it.measuredWidth, it.measuredHeight).into(it)
            }
        }
    }

    private fun prepareViews(parent: View) {
        bigActorImages += parent.actor1SIV
        bigActorImages += parent.actor2SIV
        bigActorImages += parent.actor3SIV
        bigActorImages += parent.actor4SIV
        bigActorImages += parent.actor5SIV
        bigActorImages += parent.actor6SIV
    }
}