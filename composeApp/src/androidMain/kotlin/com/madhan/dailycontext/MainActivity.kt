package com.madhan.dailycontext

import App
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.layout.WindowMetricsCalculator
import articles.presentation.view.common.util.WindowSize
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import com.seiko.imageloader.option.androidContext
import okio.Path.Companion.toOkioPath

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalImageLoader provides remember { generateImageLoader() },
            ) {
                App(rememberWindowSize())
            }
        }
    }

    fun generateImageLoader(): ImageLoader {
        return ImageLoader {
            options {
                androidContext(applicationContext)
            }
            components {
                setupDefaultComponents()
            }
            interceptor {
                defaultImageResultMemoryCache()
                memoryCacheConfig {
                    // Set the max size to 25% of the app's available memory.
                    maxSizePercent(applicationContext, 0.25)
                }
                diskCacheConfig {
                    directory(applicationContext.cacheDir.resolve("image_cache").toOkioPath())
                    maxSizeBytes(512L * 1024 * 1024) // 512MB
                }
            }
        }
    }
}

@Composable
private fun Activity.rememberWindowSize() : WindowSize {
        val localConfiguration = LocalConfiguration.current
        val windowMetrics = remember(localConfiguration) {
            WindowMetricsCalculator.getOrCreate()
                .computeCurrentWindowMetrics(this)
        }
    val windowSizeInDp = with(LocalDensity.current) {
        windowMetrics.bounds.toComposeRect().size.toDpSize()
    }
    return WindowSize.getWindowSize(windowSizeInDp.width)
}