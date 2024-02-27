package pl.owiczlin.habitify.ui.screens.quest

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import pl.owiczlin.habitify.data.models.Category
import pl.owiczlin.habitify.data.models.Difficulty
import pl.owiczlin.habitify.data.models.HabitifyQuest
import pl.owiczlin.habitify.data.models.Priority
import pl.owiczlin.habitify.viewmodels.SharedViewModel
import pl.owiczlin.habitify.util.Action
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun questScreen(
    selectedQuest: HabitifyQuest?,
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority
    val category: Category by sharedViewModel.category
    val difficulty: Difficulty by sharedViewModel.difficulty
    val isCompleted: Boolean by sharedViewModel.isCompleted

    val dueDate: LocalDate by sharedViewModel.dueDate
    val dueTime: LocalTime by sharedViewModel.dueTime

    val context = LocalContext.current

    backHandler(onBackPressed = { navigateToListScreen(Action.NO_ACTION) })


    Scaffold(
        topBar = {
            questAppBar(
                selectedQuest = selectedQuest,
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                        Log.d("QuestScreen", "Clicked back ")
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(context = context)
                        }
                    }
                }
            )
        },
        content = {
            questContent(
                title = title,
                onTitleChange = {
                    sharedViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChange = {
                    sharedViewModel.description.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                },
                category = category,
                onCategorySelected = {
                    sharedViewModel.category.value = it
                },
                difficulty = difficulty,
                onDifficultySelected = {
                    sharedViewModel.difficulty.value = it
                },
                isCompleted = isCompleted,
                onSwitchChanged = {
                    sharedViewModel.isCompleted.value = it
                },
                dueDate = dueDate,
                onDateSelected = {
                    sharedViewModel.dueDate.value = it
                },
                dueTime = dueTime
            ) {
                sharedViewModel.dueTime.value = it
            }
            Log.d("QS if sQ != null", "dueDate : $dueDate")
            Log.d("QS if sQ != null", "dueTime : $selectedQuest.dueTime")
        }
    )
}


fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Fields Empty",
        Toast.LENGTH_LONG
    ).show()
}

@Composable
fun backHandler(
    backDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallBack = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }
    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(backCallBack)

        onDispose {
            backCallBack.remove()
        }
    }
}
