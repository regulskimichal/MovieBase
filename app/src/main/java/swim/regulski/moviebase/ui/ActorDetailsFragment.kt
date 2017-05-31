package swim.regulski.moviebase.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import info.movito.themoviedbapi.Utils
import kotlinx.android.synthetic.main.fragment_actor_details.view.*
import swim.regulski.moviebase.R
import swim.regulski.moviebase.libs.CircleTransform
import swim.regulski.moviebase.model.DataProvider

class ActorDetailsFragment : Fragment() {
    val actorImages = ArrayList<ImageView>()
    val actorNames = ArrayList<TextView>()
    val actorRoles = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_actor_details, container, false)
        val parentActivity = activity as MovieDetailsActivity
        prepare(view, parentActivity.position)
        return view
    }

    private fun prepare(view: View, position: Int) {
        val movie = DataProvider.movieList[position]

        val size = if (DataProvider.profileSizes[1] != null) {
            DataProvider.profileSizes[1]
        } else {
            DataProvider.profileSizes.last()
        }

        prepareViews(view)

        actorImages.forEachIndexed {
            index, it ->
            val uri = Utils.createImageUrl(DataProvider.tmdb, movie.cast[index].profileImage, size).toString()
            view.actorDetailsCL.post {
                Picasso.with(activity).load(uri).centerCrop().transform(CircleTransform()).resize(64, 64).into(it)
                actorNames[index].text = movie.cast[index].name
                actorRoles[index].text = "${getString(R.string._as)} ${movie.cast[index].character}"
            }
        }
    }

    private fun prepareViews(parent: View) {
        actorImages += parent.actor1IV
        actorImages += parent.actor2IV
        actorImages += parent.actor3IV
        actorNames += parent.name1TV
        actorNames += parent.name2TV
        actorNames += parent.name3TV
        actorRoles += parent.role1TV
        actorRoles += parent.role2TV
        actorRoles += parent.role3TV
    }
}