import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import articles.presentation.view.features.articles.ArticlesScreen
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
internal fun App() {
    MaterialTheme { Navigator(ArticlesScreen()) }
}