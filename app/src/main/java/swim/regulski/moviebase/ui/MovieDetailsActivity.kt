package swim.regulski.moviebase.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_details.*
import swim.regulski.moviebase.R

class MovieDetailsActivity : AppCompatActivity() {
    var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        setSupportActionBar(movieDetailsToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

        position = intent.getIntExtra("position", -1)
    }

    fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPagerAdapter(supportFragmentManager)
        adapter.addFragment(MovieDetailsFragment(), R.string.movie_details)
        adapter.addFragment(ActorsFragment(), R.string.actors)
        viewPager.adapter = adapter
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val fragments = ArrayList<Fragment>()
        private val names = ArrayList<Int>()

        override fun getCount(): Int = fragments.size

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getPageTitle(position: Int): CharSequence = getString(names[position])

        fun addFragment(fragment: Fragment, name: Int) {
            fragments += fragment
            names += name
        }
    }
}
