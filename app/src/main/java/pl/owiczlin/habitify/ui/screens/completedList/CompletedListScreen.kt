package pl.owiczlin.habitify.ui.screens.completedList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import pl.owiczlin.habitify.R
import pl.owiczlin.habitify.ui.screens.list.*
import pl.owiczlin.habitify.ui.theme.fabBackgroundColor
import pl.owiczlin.habitify.viewmodels.SharedViewModel
import pl.owiczlin.habitify.util.Action
import pl.owiczlin.habitify.util.SearchAppBarState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun completedListScreen(
    action: Action,
    navigateToQuestScreen: (questId: Int) -> Unit,
    navigateToQuestsScreen: () -> Unit,
    navigateToStatsScreen: () -> Unit,
    navigateToCompletedList: () -> Unit,

    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllQuests()
        sharedViewModel.readSortState()
    }

    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseActions(action = action)
    }

    val allQuests by sharedViewModel.allQuests.collectAsState()
    val searchedQuests by sharedViewModel.searchedQuests.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityQuests by sharedViewModel.lowPriorityQuests.collectAsState()
    val highPriorityQuests by sharedViewModel.highPriorityQuests.collectAsState()

    val searchAppBarState: SearchAppBarState
            by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    displaySnackBar(
        scaffoldState = scaffoldState,
        onComplete = { sharedViewModel.action.value = it },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        questTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            completedListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = {
            completedListContent(
                allCompletedQuests = allQuests,
                searchedQuests = searchedQuests,
                lowPriorityQuests = lowPriorityQuests,
                highPriorityQuests = highPriorityQuests,
                sortState = sortState,
                searchAppBarState = searchAppBarState,
                onSwipeToDelete = { action, quest ->
                    sharedViewModel.action.value = action
                    sharedViewModel.updateQuestFields(selectedQuest = quest)
                },
                navigateToQuestScreen = navigateToQuestScreen
            )
        },
        floatingActionButton = {
            listFab(onFabClicked = navigateToQuestScreen)
        },
        bottomBar = {
            completedBottomNavigationBar(
                navigateToStatsScreen = navigateToStatsScreen,
                navigateToQuestsScreen = navigateToQuestsScreen,
                navigateToCompletedList = navigateToCompletedList
            )
        }
    )
}

@Composable
fun listFab(
    onFabClicked: (questId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(
                id = R.string.add_button
            ),
            tint = Color.White
        )
    }
}

@Composable
fun displaySnackBar(
    scaffoldState: ScaffoldState,
    onUndoClicked: (Action) -> Unit,
    onComplete: (Action) -> Unit,
    questTitle: String,
    action: Action
) {

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, questTitle = questTitle),
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedQuest(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
            onComplete(Action.NO_ACTION)
        }
    }
}

private fun setMessage(action: Action, questTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Quests Removed"
        else -> "${action.name}: $questTitle"
    }

}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedQuest(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}