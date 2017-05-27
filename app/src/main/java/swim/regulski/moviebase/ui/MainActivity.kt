package swim.regulski.moviebase.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import swim.regulski.moviebase.R
import swim.regulski.moviebase.R.id.*
import swim.regulski.moviebase.adapter.MovieAdapter
import swim.regulski.moviebase.libs.OnItemClickListener
import swim.regulski.moviebase.libs.RecyclerViewAdapter
import swim.regulski.moviebase.libs.SwipeToDismissTouchListener
import swim.regulski.moviebase.libs.SwipeableItemClickListener
import swim.regulski.moviebase.model.DataProvider

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
class MainActivity : Activity() {
    private val timeToDismiss = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRV()
    }

    private fun setupRV() {
        val deferred = async(CommonPool) {
            DataProvider.fetch()
        }

        movieRV.layoutManager = LinearLayoutManager(this)
        movieRV.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        movieRV.itemAnimator = DefaultItemAnimator()
        val adapter = MovieAdapter(this, DataProvider.movieList)
        movieRV.adapter = adapter

        val touchListener = SwipeToDismissTouchListener(
                RecyclerViewAdapter(movieRV),
                object : SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter> {
                    override fun canDismiss(position: Int): Boolean = true

                    override fun onPendingDismiss(recyclerView: RecyclerViewAdapter, position: Int) = Unit

                    override fun onDismiss(view: RecyclerViewAdapter, position: Int) = adapter.remove(position)
                })
        touchListener.setDismissDelay(timeToDismiss)

        movieRV.setOnTouchListener(touchListener)
        /*movieRV.setOnScrollListener(touchListener.makeScrollListener() as RecyclerView.OnScrollListener)*/
        movieRV.addOnItemTouchListener(swipeableItemClickListener(touchListener))

        runBlocking {
            deferred.await()
            movieRV.adapter.notifyDataSetChanged()
        }
    }

    private fun swipeableItemClickListener(touchListener: SwipeToDismissTouchListener<RecyclerViewAdapter>): SwipeableItemClickListener {
        return SwipeableItemClickListener(this,
                OnItemClickListener { view, position ->
                    when (view.id) {
                        undo -> Unit
                        txt_delete -> Unit
                        txt_undo -> {
                            touchListener.undoPendingDismiss()
                            Toast.makeText(this@MainActivity, "Undone", LENGTH_SHORT).show()
                        }
                        else -> {
                            val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                            intent.putExtra("position", position)
                            startActivity(intent)
                        }
                    }
                })
    }

    private fun Any.log() {
        Log.d(this::class.qualifiedName, this.toString())
    }
}