package articles.domain

import articles.data.dto.ArticleDTO
import articles.data.dto.ArticlesDTO

interface IArticlesRepository {
    val defaultPageValue: Int
        get() = 1

    suspend fun getArticles(
        language: String,
        page: Int = defaultPageValue
    ): ArticlesDTO

    suspend fun getArticlesByDate(
        fromDate: String,
        toDate: String,
        page: Int = defaultPageValue
    ): ArticlesDTO

    suspend fun getArticle(): ArticleDTO
    suspend fun getArticlesByKeyword(
        keyword: String,
        page: Int = defaultPageValue
    ): List<ArticleDTO>

    suspend fun getArticlesByLanguage(
        language: String,
        page: Int = defaultPageValue
    ): List<ArticleDTO>
}