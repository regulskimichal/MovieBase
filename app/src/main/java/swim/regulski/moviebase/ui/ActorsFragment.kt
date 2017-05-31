package swim.regulski.moviebase.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import swim.regulski.moviebase.R

class ActorsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_actor, container, false)
        prepare()
        return view
    }

    private fun prepare() {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.actor_images_frame, ActorImagesFragment())
        transaction.add(R.id.actor_details_frame, ActorDetailsFragment())
        transaction.commit()
    }
}