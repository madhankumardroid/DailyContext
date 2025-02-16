import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import articles.presentation.view.common.util.WindowSize
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import di.startDI
import okio.FileSystem
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main()  {
    startDI {  }
    onWasmReady {
        CanvasBasedWindow("DailyContext") {
            CompositionLocalProvider(
                LocalImageLoader provides remember { generateImageLoader() }
            ) {
                App(WindowSize.EXPANDED)
            }
        }
    }
}

private fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {
          defaultImageResultMemoryCache()
            memoryCacheConfig {
                maxSizeBytes(32*1024*1024)
            }
            diskCacheConfig {
                directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
                maxSizeBytes(256L * 1024 * 1024) // 256MB
            }
        }
    }
}
