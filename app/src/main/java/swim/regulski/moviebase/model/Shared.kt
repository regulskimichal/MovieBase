package swim.regulski.moviebase.model

import swim.regulski.moviebase.R

object Shared {
    internal val temp: ArrayList<Movie> = prepareMovieData()

    fun prepareMovieData(): ArrayList<Movie> {
        val temp = ArrayList<Movie>()
        var movie = Movie("Mad Max: Fury Road", R.string.action, "2015", 4.0f, "")
        temp.add(movie)

        movie = Movie("Inside Out", R.string.animation, "2015", 4.0f, "")
        temp.add(movie)

        movie = Movie("Star Wars: Episode VII - The Force Awakens", R.string.action, "2015", 4.0f, "")
        temp.add(movie)

        movie = Movie("Shaun the Sheep", R.string.animation, "2015", 4.0f, "")
        temp.add(movie)

        movie = Movie("The Martian", R.string.science_fiction, "2015", 4.0f, "")
        temp.add(movie)

        movie = Movie("Mission: Impossible Rogue Nation", R.string.action, "2015", 4.0f, "")
        temp.add(movie)

        movie = Movie("Up", R.string.animation, "2009", 4.0f, "")
        temp.add(movie)

        movie = Movie("Star Trek", R.string.science_fiction, "2009", 4.0f, "")
        temp.add(movie)

        movie = Movie("The LEGO Movie", R.string.animation, "2014", 4.0f, "")
        temp.add(movie)

        movie = Movie("Iron Man", R.string.action, "2008", 4.0f, "")
        temp.add(movie)

        movie = Movie("Aliens", R.string.science_fiction, "1986", 4.0f, "")
        temp.add(movie)

        movie = Movie("Chicken Run", R.string.animation, "2000", 4.0f, "")
        temp.add(movie)

        movie = Movie("Back to the Future", R.string.science_fiction, "1985", 4.0f, "")
        temp.add(movie)

        movie = Movie("Raiders of the Lost Ark", R.string.action, "1981", 4.0f, "")
        temp.add(movie)

        movie = Movie("Goldfinger", R.string.action, "1965", 4.0f, "")
        temp.add(movie)

        movie = Movie("Guardians of the Galaxy", R.string.science_fiction, "2014", 4.0f, "")
        temp.add(movie)

        return temp
    }
}