package articles.presentation.view.common.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun Error(
    modifier: Modifier = Modifier,
    errorMessage: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = errorMessage, style = MaterialTheme.typography.h4)
        }
    }
}