package di

import DailyContext.composeApp.BuildConfig
import articles.data.data_source.ArticlesRemoteDataSourceImpl
import articles.data.repository.ArticlesRepositoryImpl
import articles.data.repository.IArticlesRemoteDataSource
import articles.domain.IArticlesRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * To start the dependency injection
 * @param appDeclaration The KoinAppDeclaration
 */
fun startDI(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            repositoryModule,
            ktorModule,
            platformModule()
        )
    }
}

val repositoryModule = module {
    single<IArticlesRepository> { ArticlesRepositoryImpl(get()) }
    single<IArticlesRemoteDataSource> { ArticlesRemoteDataSourceImpl(get()) }
}
val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            headers {
                append(DIConstants.X_API_KEY_HEADER_NAME, BuildConfig.API_KEY)
            }
        }
    }
}