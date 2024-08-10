package articles.domain.model.map

import articles.data.dto.ArticleDTO
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.math.abs

class Mapper {

    fun map(values: List<ArticleDTO>): List<ArticleEntity> =
        values.map {
            ArticleEntity(
                it.author,
                it.content,
                it.description,
                getDaysAgoString(it.publishedAt),
                it.title,
                it.urlToImage
            )
        }

    private fun getDaysAgoString(date: String?): String {
        date?.let {
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
            val days = today.daysUntil(
                Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()).date
            )
            val result = when {
                abs(days) > 1 -> "${abs(days)} days ago"
                abs(days) == 1 -> "Yesterday"
                else -> "Today"
            }
            return result
        } ?: return ""
    }
}