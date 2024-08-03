package articles.data.repository

import articles.data.dto.ArticleDTO
import articles.domain.IArticlesRepository

class ArticlesRepositoryImpl(private val articlesRemoteDataSource: IArticlesRemoteDataSource) :
    IArticlesRepository {
    override suspend fun getArticles(
        country: String,
        category: String,
        page: Int
    ): List<ArticleDTO> {
        return articlesRemoteDataSource.getArticles(country, category, page).articles
    }

    override suspend fun getArticlesByDate(
        fromDate: String,
        toDate: String,
        page: Int
    ): List<ArticleDTO> {
        return articlesRemoteDataSource.getArticlesByDate(fromDate, toDate, page).articles
    }

    override suspend fun getArticle(): ArticleDTO {
        return articlesRemoteDataSource.getArticle()
    }

    override suspend fun getArticlesByKeyword(keyword: String, page: Int): List<ArticleDTO> {
        return articlesRemoteDataSource.getArticlesByKeyword(keyword, page).articles
    }

    override suspend fun getArticlesByLanguage(language: String, page: Int): List<ArticleDTO> {
        return articlesRemoteDataSource.getArticlesByLanguage(language).articles
    }
}