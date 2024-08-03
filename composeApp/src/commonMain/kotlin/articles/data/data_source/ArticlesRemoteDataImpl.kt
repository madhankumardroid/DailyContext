package articles.data.data_source

import DailyContext.composeApp.BuildConfig
import articles.data.dto.ArticleDTO
import articles.data.dto.ArticlesDTO
import articles.data.repository.IArticlesRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ArticlesRemoteDataImpl(private val httpClient: HttpClient) : IArticlesRemoteDataSource {
    override suspend fun getArticles(
        country: String,
        category: String,
        page: Int
    ): List<ArticleDTO> {
        val response: ArticlesDTO =
            httpClient.get("${BuildConfig.BASE_URL}/top-headlines?country=$country&category=$category&page=$page")
                .body()
        return response.articles
    }

    override suspend fun getArticlesByDate(
        fromDate: String,
        toDate: String,
        page: Int
    ): List<ArticleDTO> {
        val response: ArticlesDTO =
            httpClient.get("${BuildConfig.BASE_URL}/everything?from=$fromDate&to=$toDate&page=$page")
                .body()
        return response.articles
    }

    override suspend fun getArticle(): ArticleDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getArticlesByKeyword(keyword: String, page: Int): List<ArticleDTO> {
        val response: ArticlesDTO =
            httpClient.get("${BuildConfig.BASE_URL}/everything?q=$keyword&page=$page")
                .body()
        return response.articles
    }

    override suspend fun getArticlesByLanguage(language: String, page: Int): List<ArticleDTO> {
        val response: ArticlesDTO =
            httpClient.get("${BuildConfig.BASE_URL}/everything?language=$language&page=$page")
                .body()
        return response.articles    }
}