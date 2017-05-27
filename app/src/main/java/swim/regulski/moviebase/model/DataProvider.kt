package swim.regulski.moviebase.model

import info.movito.themoviedbapi.TmdbApi
import info.movito.themoviedbapi.TmdbMovies
import info.movito.themoviedbapi.model.Genre

object DataProvider {
    private val APIKEY = "7dfbf2930619bd632086a6f1a125643c"

    val tmdb: TmdbApi = TmdbApi(APIKEY)
    val movieList: MutableList<Movie> = ArrayList()
    val posterSizes: List<String> = tmdb.configuration.posterSizes
    val backdropSizes: List<String> = tmdb.configuration.backdropSizes
    val profileSizes: List<String> = tmdb.configuration.profileSizes

    private val tmdbMovies = tmdb.movies

    internal fun fetch(): MutableList<Movie> {
        if (movieList.size == 0) {
            movieList += tmdbMovies.getNowPlayingMovies("en", 1).results.map {
                it ->
                Movie(it.id,
                        it.title,
                        it.releaseDate.substring(0, 4),
                        it.posterPath,
                        it.backdropPath,
                        it.voteAverage / 2,
                        it.overview)
            }
        }

        return movieList
    }

    internal fun get(position: Int): Movie {
        val current = movieList[position]

        if (!current.hasLoadedDetails) {
            val fromDB = tmdbMovies.getMovie(current.id, "en", TmdbMovies.MovieMethod.credits)
            current.cast.addAll(fromDB.cast.map {
                it ->
                Cast(it.id, it.character, it.name, it.profilePath)
            })
            current.genres = fromDB.genres.convertToString()
            current.hasLoadedDetails = true
        }

        return current
    }

    private fun MutableList<Genre>.convertToString(): String = when (size) {
        0 -> "undefined"
        else -> fold("") {
            acc, genre ->
            "$acc/${genre.name}"
        }.substring(1)
    }
}