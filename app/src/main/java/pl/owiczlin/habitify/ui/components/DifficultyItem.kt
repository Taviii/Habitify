package pl.owiczlin.habitify.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.owiczlin.habitify.data.models.Difficulty
import pl.owiczlin.habitify.ui.theme.SMALL_PADDING
import pl.owiczlin.habitify.ui.theme.Typography

@Composable
fun difficultyItem(difficulty: Difficulty) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = SMALL_PADDING, end = SMALL_PADDING),
            text = difficulty.name,
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
@Preview
fun difficultyItemPreview() {
    difficultyItem(difficulty = Difficulty.Medium)
}