package articles.domain.interactors

import articles.domain.IArticlesRepository
import articles.domain.model.map.ArticleEntity
import articles.domain.model.map.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class GetArticlesByDateUseCase (
    private val articlesRepository: IArticlesRepository,
    private val mapper: Mapper,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(fromDate : String = getTodayDate(), toDate : String  = getTodayDate()): Result<List<ArticleEntity>> = withContext(dispatcher) {
        try {
            val articlesByDate = articlesRepository.getArticlesByDate(fromDate, toDate)
            Result.success(mapper.map(articlesByDate.articles))
        } catch (ex : Exception) {
            Result.failure(ex)
        }
    }

    private fun getTodayDate(): String {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        return today.toString()
    }
}