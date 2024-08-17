import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import articles.presentation.view.common.util.WindowSize
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import di.startDI
import okio.Path.Companion.toOkioPath
import java.io.File

fun main() = application {
    startDI { }
    val windowState = rememberWindowState(size = DpSize(850.dp, 650.dp))

    Window(
        onCloseRequest = ::exitApplication,
        title = "DailyContext",
        state = windowState
    ) {
        App(windowSize = WindowSize.getWindowSize(windowState.size.width))
    }
}

fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {
            // cache 100 success image result, without bitmap
            defaultImageResultMemoryCache()
            memoryCacheConfig {
                maxSizeBytes(32 * 1024 * 1024) // 32MB
            }
            diskCacheConfig {
                directory(getCacheDir().toOkioPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

enum class OperatingSystem {
    Windows, Linux, MacOS, Unknown
}

private val currentOperatingSystem: OperatingSystem
    get() {
        val operSys = System.getProperty("os.name").lowercase()
        return if (operSys.contains("win")) {
            OperatingSystem.Windows
        } else if (operSys.contains("nix") || operSys.contains("nux") ||
            operSys.contains("aix")
        ) {
            OperatingSystem.Linux
        } else if (operSys.contains("mac")) {
            OperatingSystem.MacOS
        } else {
            OperatingSystem.Unknown
        }
    }

// about currentOperatingSystem, see app
private fun getCacheDir() = when (currentOperatingSystem) {
    OperatingSystem.Windows -> File(System.getenv("AppData"), "$ApplicationName/cache")
    OperatingSystem.Linux -> File(System.getProperty("user.home"), ".cache/$ApplicationName")
    OperatingSystem.MacOS -> File(
        System.getProperty("user.home"),
        "Library/Caches/$ApplicationName"
    )

    else -> throw IllegalStateException("Unsupported operating system")
}

private const val ApplicationName = "DailyContext"
