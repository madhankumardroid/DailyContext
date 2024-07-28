package articles.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * To start the dependency injection
 * @param appDeclaration The KoinAppDeclaration
 */
fun startDI(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {

    }
}