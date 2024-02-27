package pl.owiczlin.habitify.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.owiczlin.habitify.data.models.Difficulty
import pl.owiczlin.habitify.ui.theme.LARGE_PADDING
import pl.owiczlin.habitify.ui.theme.PRIORITY_DROP_DOWN_HEIGHT

@Composable
fun difficultyDropDown(
    difficulty: Difficulty,
    onDifficultySelected: (Difficulty) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    Row(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { expanded = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = ContentAlpha.disabled
                ),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(
            modifier = androidx.compose.ui.Modifier
                .weight(8f)
                .padding(LARGE_PADDING),
            text = difficulty.name,
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(
            modifier = androidx.compose.ui.Modifier
                .alpha(ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(weight = 1.5f),
            onClick = { expanded = true }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = pl.owiczlin.habitify.R.string.drop_down_arrow)
            )
        }
        DropdownMenu(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth(fraction = 0.94f),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onDifficultySelected(Difficulty.SuperEasy)
            }
            ) {
                difficultyItem(difficulty = Difficulty.SuperEasy)
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onDifficultySelected(Difficulty.Easy)
            }
            ) {
                difficultyItem(difficulty = Difficulty.Easy)
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onDifficultySelected(Difficulty.Medium)
            }
            ) {
                difficultyItem(difficulty = Difficulty.Medium)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onDifficultySelected(Difficulty.Hard)
            }
            ) {
                difficultyItem(difficulty = Difficulty.Hard)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onDifficultySelected(Difficulty.Challenge)
            }
            ) {
                difficultyItem(difficulty = Difficulty.Challenge)
            }
        }
    }
}


@Composable
@Preview
fun difficultyDropDownPreview() {
    difficultyDropDown(
        difficulty = Difficulty.Challenge
    ) {}
}