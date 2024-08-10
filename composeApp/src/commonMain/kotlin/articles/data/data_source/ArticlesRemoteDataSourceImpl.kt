package articles.data.data_source

import DailyContext.composeApp.BuildConfig
import articles.data.dto.ArticleDTO
import articles.data.dto.ArticlesDTO
import articles.data.repository.IArticlesRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ArticlesRemoteDataSourceImpl(private val httpClient: HttpClient) : IArticlesRemoteDataSource {
    override suspend fun getArticles(
        language: String,
        page: Int
    ): ArticlesDTO {
        val response: ArticlesDTO =
            httpClient.get("${BuildConfig.BASE_URL}everything?language=$language&domains=bbc.co.uk&page=$page")
                .body()
        return response
    }

    override suspend fun getArticlesByDate(
        fromDate: String,
        toDate: String,
        page: Int
    ): ArticlesDTO {
        val response: ArticlesDTO =
            httpClient.get("${BuildConfig.BASE_URL}/everything?from=$fromDate&to=$toDate&page=$page")
                .body()
        return response
    }

    override suspend fun getArticle(): ArticleDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getArticlesByKeyword(keyword: String, page: Int): ArticlesDTO {
        val response: ArticlesDTO =
            httpClient.get("${BuildConfig.BASE_URL}/everything?q=$keyword&page=$page")
                .body()
        return response
    }

    override suspend fun getArticlesByLanguage(language: String, page: Int): ArticlesDTO {
        val response: ArticlesDTO =
            httpClient.get("${BuildConfig.BASE_URL}/everything?language=$language&page=$page")
                .body()
        return response
    }
}