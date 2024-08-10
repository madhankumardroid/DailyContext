package articles.domain.interactors

import articles.domain.IArticlesRepository
import articles.domain.model.map.ArticleEntity
import articles.domain.model.map.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetArticlesUseCase(
    private val articlesRepository: IArticlesRepository,
    private val mapper: Mapper,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(language: String = "en"): Result<List<ArticleEntity>> =
        withContext(dispatcher) {
            try {
                val articles = articlesRepository.getArticles(language).articles
                Result.success(mapper.map(articles))
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }
}