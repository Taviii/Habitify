package pl.owiczlin.habitify.ui.screens.quest

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import pl.owiczlin.habitify.R
import pl.owiczlin.habitify.ui.components.displayAlertDialog
import pl.owiczlin.habitify.data.models.Category
import pl.owiczlin.habitify.data.models.Difficulty
import pl.owiczlin.habitify.data.models.HabitifyQuest
import pl.owiczlin.habitify.data.models.Priority
import pl.owiczlin.habitify.ui.theme.topAppBarBackgroundColor
import pl.owiczlin.habitify.ui.theme.topAppBarContentColor
import pl.owiczlin.habitify.util.Action
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun questAppBar(
    selectedQuest: HabitifyQuest?,
    navigateToListScreen: (Action) -> Unit
) {
    if (selectedQuest == null) {
        newQuestAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        existingQuestAppBar(
            selectedQuest = selectedQuest,
            navigateToListScreen = navigateToListScreen
        )
    }
}

@Composable
fun newQuestAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            backAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(id = R.string.add_quest),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            addAction(onAddClicked = navigateToListScreen)
        }
    )
}

@Composable
fun backAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_ARR),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun addAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_quest),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun existingQuestAppBar(
    selectedQuest: HabitifyQuest,
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            closeAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = selectedQuest.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            existingQuestAppBarActions(
                selectedQuest = selectedQuest,
                navigateToListScreen = navigateToListScreen
            )
        }
    )
}

@Composable
fun existingQuestAppBarActions(
    selectedQuest: HabitifyQuest,
    navigateToListScreen: (Action) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    displayAlertDialog(
        title = stringResource(
            id = R.string.delete_quest,
            selectedQuest.title
        ),
        message = stringResource(
            id = R.string.delete_quest_confirmation,
            selectedQuest.title
        ),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToListScreen(Action.DELETE) }
    )
    deleteAction(onDeleteClicked = {
        openDialog = true
    })
    updateAction(onUpdateClicked = navigateToListScreen)
}

@Composable
fun updateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun closeAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun deleteAction(
    onDeleteClicked: () -> Unit
) {
    IconButton(onClick = { onDeleteClicked() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
@Preview
private fun newQuestAppBarPreview() {
    newQuestAppBar(
        navigateToListScreen = {}
    )
}

@Composable
@Preview
private fun existingQuestAppBarPreview() {
    existingQuestAppBar(
        selectedQuest = HabitifyQuest(
            id = 0,
            title = "Buba",
            description = "Random text",
            priority = Priority.LOW,
            isCompleted = false,
            category = Category.Finances,
            difficulty = Difficulty.Hard,
            dueTime = LocalTime.NOON,
            dueDate = LocalDate.now()
        ),
        navigateToListScreen = {}
    )
}
