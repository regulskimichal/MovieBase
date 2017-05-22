package swim.regulski.moviebase.model

import com.google.gson.annotations.SerializedName

data class Movie(@SerializedName("") var title: String,
                 var genre: Int,
                 var year: String = "",
                 var rating: Float = 0.0f,
                 var description: String = "")