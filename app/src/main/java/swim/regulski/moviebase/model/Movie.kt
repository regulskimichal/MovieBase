package swim.regulski.moviebase.model

import info.movito.themoviedbapi.model.Artwork

data class Movie(var id: Int,
                 var title: String,
                 var year: String,
                 var poster: String,
                 var backdrop: String,
                 var rating: Float = 0.0f,
                 var overview: String = "",
                 var genres: String = "",
                 var cast: MutableList<Cast> = ArrayList(),
                 var hasLoadedDetails: Boolean = false)