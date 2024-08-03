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
    suspend fun invoke(country : String, category : String): Result<List<ArticleEntity>> = withContext(dispatcher) {
        try {
            Result.success(mapper.map(articlesRepository.getArticles(country, category)))
        } catch (ex : Exception) {
            Result.failure(ex)
        }
    }
}