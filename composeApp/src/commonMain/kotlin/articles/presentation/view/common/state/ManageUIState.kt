package articles.presentation.view.common.state

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import articles.presentation.model.ResourceUiState

@Composable
fun <T> ManageUiState(
    modifier: Modifier = Modifier,
    resourceUiState: ResourceUiState<T>,
    successView : @Composable (data : T) -> Unit,
    loadingView : @Composable () -> Unit = { Loading() },
    emptyMessage : String = "No data to Show",
    errorMessage : String = "An error occurred"
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when(resourceUiState) {
            ResourceUiState.Empty -> Empty(modifier, emptyMessage)
            is ResourceUiState.Error -> Error(modifier, errorMessage)
            ResourceUiState.Idle -> Unit
            ResourceUiState.Loading -> loadingView()
            is ResourceUiState.Success -> successView(resourceUiState.data)
        }
    }
}