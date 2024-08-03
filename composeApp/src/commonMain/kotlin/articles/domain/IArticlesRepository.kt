package articles.domain

import articles.data.dto.ArticleDTO

interface IArticlesRepository {
    suspend fun getArticles() : List<ArticleDTO>
    suspend fun getArticle() : ArticleDTO
}