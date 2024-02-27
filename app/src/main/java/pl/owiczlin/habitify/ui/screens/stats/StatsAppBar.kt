package pl.owiczlin.habitify.ui.screens.stats

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.owiczlin.habitify.R
import pl.owiczlin.habitify.ui.theme.topAppBarBackgroundColor
import pl.owiczlin.habitify.ui.theme.topAppBarContentColor

@Composable
fun defaultStatsAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.stats_screen_title),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun statsBottomNavigationBar(
    navigateToQuestsScreen: () -> Unit,
    navigateToCompletedList: () -> Unit,
    navigateToStatsScreen: () -> Unit,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        elevation = 0.dp
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = { navigateToQuestsScreen() },
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null
                )
            },
            label = { Text(stringResource(id = R.string.quests_list)) }
        )

        BottomNavigationItem(
            selected = false,
            onClick = { navigateToCompletedList() },
            icon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            },
            label = { Text(stringResource(id = R.string.completed_list)) }
        )

        BottomNavigationItem(
            selected = true,
            onClick = { navigateToStatsScreen() },
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null
                )
            },
            label = { Text(stringResource(id = R.string.stats)) }
        )
    }
}

@Composable
@Preview
private fun defaultStatsAppBarPreview() {
    defaultStatsAppBar()
}

@Composable
@Preview
private fun statsBottomNavigationBarPreview() {
    statsBottomNavigationBar(
        navigateToCompletedList = {},
        navigateToQuestsScreen = {},
        navigateToStatsScreen = {}
    )
}