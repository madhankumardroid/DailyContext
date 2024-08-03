package di

import DailyContext.composeApp.BuildConfig
import articles.data.data_source.ArticlesRemoteDataSourceImpl
import articles.data.repository.ArticlesRepositoryImpl
import articles.data.repository.IArticlesRemoteDataSource
import articles.domain.IArticlesRepository
import articles.domain.interactors.GetArticlesByDateUseCase
import articles.domain.interactors.GetArticlesByKeywordUseCase
import articles.domain.interactors.GetArticlesByLanguageUseCase
import articles.domain.interactors.GetArticlesUseCase
import articles.domain.model.map.Mapper
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
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

val mapperModule = module {
    single { Mapper() }
}
val dispatcherModule = module {
    factory { Dispatchers.Default }
}
val useCasesModule: Module = module {
    single { GetArticlesUseCase(get(), get(), get()) }
    single { GetArticlesByKeywordUseCase(get(), get(), get()) }
    single { GetArticlesByLanguageUseCase(get(), get(), get()) }
    single { GetArticlesByDateUseCase(get(), get(), get()) }
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