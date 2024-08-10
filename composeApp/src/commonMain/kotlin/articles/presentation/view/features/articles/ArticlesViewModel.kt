package articles.presentation.view.features.articles

import articles.domain.interactors.GetArticlesUseCase
import articles.presentation.intent.BaseViewModel
import articles.presentation.intent.UIEvent
import articles.presentation.model.ResourceUiState
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

class ArticlesViewModel(private val getArticlesUseCase: GetArticlesUseCase) :
    BaseViewModel<ArticlesContract.Event, ArticlesContract.State, ArticlesContract.Effect>() {
    init {
        getArticles()
    }

    private fun getArticles() {
        setState { copy(ResourceUiState.Loading) }
        screenModelScope.launch {
            getArticlesUseCase().onSuccess {
                setState {
                    copy(
                        articles = if (it.isEmpty()) ResourceUiState.Empty else ResourceUiState.Success(
                            it
                        )
                    )
                }
            }.onFailure {
                setState {
                    copy(
                        articles = ResourceUiState.Error()
                    )
                }
            }
        }
    }

    override fun createInitialState(): ArticlesContract.State {
        return ArticlesContract.State(ResourceUiState.Idle)
    }

    override fun handleEvent(uiEvent: UIEvent) {
    }
}