package articles.presentation.view.common.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize {
    COMPACT,
    MEDIUM,
    EXPANDED;

    companion object {
        fun getWindowSize(windowWidth: Dp): WindowSize {
            return when {
                windowWidth < 600.dp -> COMPACT
                windowWidth < 840.dp -> MEDIUM
                else -> EXPANDED
            }
        }
    }
}