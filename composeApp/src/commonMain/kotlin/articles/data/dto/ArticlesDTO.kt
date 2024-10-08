package articles.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesDTO(
    @SerialName("articles")
    val articles: List<ArticleDTO>,
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int
)