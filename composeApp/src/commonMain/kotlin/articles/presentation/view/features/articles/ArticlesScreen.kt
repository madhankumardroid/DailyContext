package articles.presentation.view.features.articles

import LocalWindowSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import articles.domain.model.map.ArticleEntity
import articles.presentation.view.common.state.ManageUiState
import articles.presentation.view.common.util.WindowSize
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.seiko.imageloader.rememberImagePainter
import kotlinx.coroutines.flow.collectLatest

class ArticlesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator= LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ArticlesViewModel>()
        val state by viewModel.uiState.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.uiEffect.collectLatest { effect ->
                when(effect) {
                    is ArticlesContract.Effect.NavigateToArticleDetail -> {
                        // TODO: Navigate to article detail screen.
//                        navigator.push()
                    }
                }
            }
        }
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Daily Context") })
            }
        ) { padding ->
            ManageUiState(
                modifier = Modifier.padding(padding).fillMaxSize(),
                resourceUiState = state.articles,
                successView = { articles ->
                    ArticlesList(articles,
                        onArticleClick = { articleId ->
                            viewModel.setEvent(ArticlesContract.Event.OnArticleClick(articleId))
                        })
                },
                emptyMessage = "No Articles Found"
            )
        }
    }

    @Composable
    fun ArticlesList(
        articles: List<ArticleEntity>,
        onArticleClick: (String) -> Unit,
    ) {
        if (LocalWindowSize.current == WindowSize.COMPACT) {
            LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
                items(articles) { article ->
                    ArticleItem(article, onClick = {
                        article.id?.let {
                            onArticleClick(it)
                        }
                    }
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                GridCells.Adaptive(320.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                items(articles) { article ->
                    ArticleItem(article, onClick = {
                        article.id?.let {
                            onArticleClick(it)
                        }
                    })
                }
            }
        }
    }

    @Composable
    fun ArticleItem(article: ArticleEntity, onClick: () -> Unit) {
        Card(
            modifier = Modifier.padding(8.dp).fillMaxWidth().wrapContentHeight().clickable(onClick = onClick),
            shape = MaterialTheme.shapes.medium,
            elevation = 5.dp,
            backgroundColor = MaterialTheme.colors.surface,
        ) {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp)) {
                val imagePainter = article.urlToImage?.let { rememberImagePainter(it) }
                if (imagePainter != null) {
                    Image(
                        imagePainter,
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        contentDescription = article.title,
                        contentScale = ContentScale.FillWidth
                    )
                }
                Text(
                    text = article.title ?: "",
                    modifier = Modifier.padding(vertical = 4.dp),
                    maxLines = 1,
                    style = TextStyle(
                        color = MaterialTheme.colors.primary,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = if (!article.author.isNullOrEmpty()) "Author : ${article.author}" else "",
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        style = MaterialTheme.typography.body1.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = article.publishedAt ?: "",
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        style = MaterialTheme.typography.body1.copy(fontSize = 12.sp)
                    )
                }
                Text(
                    text = article.description ?: "",
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(vertical = 4.dp),
                    style = MaterialTheme.typography.body1,
                    maxLines = 2
                )
            }
        }
    }
}