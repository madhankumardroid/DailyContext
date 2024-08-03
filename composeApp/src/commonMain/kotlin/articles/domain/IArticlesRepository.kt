package articles.domain

import articles.data.dto.ArticleDTO

interface IArticlesRepository {
    val defaultPageValue: Int
        get() = 1

    suspend fun getArticles(
        country: String,
        category: String,
        page: Int = defaultPageValue
    ): List<ArticleDTO>

    suspend fun getArticlesByDate(
        fromDate: String,
        toDate: String,
        page: Int = defaultPageValue
    ): List<ArticleDTO>

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