import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import articles.presentation.view.common.util.WindowSize
import articles.presentation.view.features.articles.ArticlesScreen
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
internal fun App(windowSize: WindowSize) {
    MaterialTheme {
        CompositionLocalProvider(LocalWindowSize provides windowSize) {
            Navigator(ArticlesScreen())
        }
    }
}

val LocalWindowSize = compositionLocalOf { WindowSize.COMPACT }