package articles.presentation.view.features.articles

import articles.domain.model.map.ArticleEntity
import articles.presentation.intent.UIEffect
import articles.presentation.intent.UIEvent
import articles.presentation.intent.UIState
import articles.presentation.model.ResourceUiState

interface ArticlesContract {
    sealed interface Event : UIEvent

    data class State(val articles: ResourceUiState<List<ArticleEntity>>) : UIState

    sealed interface Effect : UIEffect
}