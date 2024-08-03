package articles.domain.interactors

import articles.domain.IArticlesRepository
import articles.domain.model.map.ArticleEntity
import articles.domain.model.map.Mapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetArticlesByKeywordUseCase (
    private val articlesRepository: IArticlesRepository,
    private val mapper: Mapper,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun invoke(keyword : String): Result<List<ArticleEntity>> = withContext(dispatcher) {
        try {
            Result.success(mapper.map(articlesRepository.getArticlesByKeyword(keyword)))
        } catch (ex : Exception) {
            Result.failure(ex)
        }
    }
}