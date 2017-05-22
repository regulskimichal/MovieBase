package swim.regulski.moviebase.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.activity_main.*
import swim.regulski.moviebase.R
import swim.regulski.moviebase.adapter.MovieAdapter
import swim.regulski.moviebase.libs.OnItemClickListener
import swim.regulski.moviebase.libs.RecyclerViewAdapter
import swim.regulski.moviebase.libs.SwipeToDismissTouchListener
import swim.regulski.moviebase.libs.SwipeableItemClickListener
import swim.regulski.moviebase.model.Shared

class MainActivity : Activity() {
    val timeToDismiss = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {
        movieRV.layoutManager = LinearLayoutManager(this)
        val movieList = Shared.temp
        val adapter = MovieAdapter(this, movieList)
        movieRV.adapter = adapter
        movieRV.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        movieRV.itemAnimator = DefaultItemAnimator()

        val touchListener = SwipeToDismissTouchListener(
                RecyclerViewAdapter(movieRV),
                object : SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter> {
                    override fun canDismiss(position: Int): Boolean = true

                    override fun onPendingDismiss(recyclerView: RecyclerViewAdapter, position: Int) = Unit

                    override fun onDismiss(view: RecyclerViewAdapter, position: Int) = adapter.remove(position)
                })
        touchListener.setDismissDelay(timeToDismiss)

        movieRV.setOnTouchListener(touchListener)
        movieRV.setOnScrollListener(touchListener.makeScrollListener() as RecyclerView.OnScrollListener)
        movieRV.addOnItemTouchListener(SwipeableItemClickListener(this,
                OnItemClickListener { view, position ->
                    when (view.id) {
                        R.id.undo -> Unit
                        R.id.txt_delete -> Unit
                        R.id.txt_undo -> {
                            touchListener.undoPendingDismiss()
                            Toast.makeText(this@MainActivity, "Undone", LENGTH_SHORT).show()
                        }
                        else -> {
                            val intent = Intent(this@MainActivity, MovieDetailsActivity::class.java)
                            intent.putExtra("position", position)
                            startActivity(intent)
                        }
                    }
                }))
    }
}