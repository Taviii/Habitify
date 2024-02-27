package pl.owiczlin.habitify.ui.screens.list

import androidx.compose.*
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.owiczlin.habitify.R
import pl.owiczlin.habitify.data.models.Category
import pl.owiczlin.habitify.data.models.Difficulty
import pl.owiczlin.habitify.data.models.HabitifyQuest
import pl.owiczlin.habitify.data.models.Priority
import pl.owiczlin.habitify.ui.theme.*
import pl.owiczlin.habitify.util.Action
import pl.owiczlin.habitify.util.RequestState
import pl.owiczlin.habitify.util.SearchAppBarState
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun listContent(
    allQuests: RequestState<List<HabitifyQuest>>,
    searchedQuests: RequestState<List<HabitifyQuest>>,
    lowPriorityQuests: List<HabitifyQuest>,
    highPriorityQuests: List<HabitifyQuest>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    onSwipeToDelete: (Action, HabitifyQuest) -> Unit,
    navigateToQuestScreen: (questId: Int) -> Unit,

    ) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedQuests is RequestState.Success) {
                    handleListContent(
                        quests = searchedQuests.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToQuestScreen = navigateToQuestScreen
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allQuests is RequestState.Success) {
                    handleListContent(
                        quests = allQuests.data,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToQuestScreen = navigateToQuestScreen
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                handleListContent(
                    quests = lowPriorityQuests,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToQuestScreen = navigateToQuestScreen
                )
            }
            sortState.data == Priority.HIGH -> {
                handleListContent(
                    quests = highPriorityQuests,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToQuestScreen = navigateToQuestScreen
                )
            }
        }
    }
}

@Composable
fun handleListContent(
    quests: List<HabitifyQuest>,
    onSwipeToDelete: (Action, HabitifyQuest) -> Unit,
    navigateToQuestScreen: (questId: Int) -> Unit
) {
    val incompleteQuests = quests.filter { !it.isCompleted }

    if (incompleteQuests.isEmpty()) {
        emptyContent()
    } else {
        displayQuests(
            quests = incompleteQuests,
            onSwipeToDelete = onSwipeToDelete,
            navigateToQuestScreen = navigateToQuestScreen
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun displayQuests(
    quests: List<HabitifyQuest>,
    onSwipeToDelete: (Action, HabitifyQuest) -> Unit,
    navigateToQuestScreen: (questId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = quests,
            key = { quest ->
                quest.id
            }
        )
        { quest ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if (isDismissed) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    if (dismissDirection == DismissDirection.EndToStart) {
                        onSwipeToDelete(Action.DELETE, quest)
                    }
                }
            }

            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default)
                    0f
                else
                    -45f
            )

            var itemAppeared by remember { mutableStateOf(false) }
            LaunchedEffect(key1 = true) {
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                    background = {
                        if (dismissDirection == DismissDirection.EndToStart) {
                            redBackground(degrees = degrees)
                        }
                    },
                    dismissContent = {
                        questItem(
                            habitifyQuest = quest,
                            navigateToQuestScreen = navigateToQuestScreen
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun redBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun questItem(
    habitifyQuest: HabitifyQuest,
    navigateToQuestScreen: (questId: Int) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = questItemBackground(habitifyQuest.category),
        shape = RectangleShape,
        elevation = QUEST_ITEM_ELEVATION,
        onClick = {
            navigateToQuestScreen(habitifyQuest.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = habitifyQuest.title,
                    color = MaterialTheme.colors.questItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    androidx.compose.foundation.Canvas(
                        modifier = Modifier
                            .size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = habitifyQuest.priority.color
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = habitifyQuest.description,
                color = MaterialTheme.colors.questItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Composable
@Preview
fun questItemPreview() {
    questItem(
        habitifyQuest = HabitifyQuest(
            id = 0,
            title = "Title",
            description = "Some random text",
            priority = Priority.MEDIUM,
            isCompleted = false,
            category = Category.Relationships,
            difficulty = Difficulty.Medium,
            dueTime = LocalTime.NOON,
            dueDate = LocalDate.now()
        ),
        navigateToQuestScreen = {}
    )
}

@Composable
@Preview
private fun redBackgroundPreview() {
    Column(modifier = Modifier.height(50.dp)) {
        redBackground(degrees = 0f)
    }
}
