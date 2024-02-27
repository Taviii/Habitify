package pl.owiczlin.habitify.ui.screens.quest

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.owiczlin.habitify.R
import pl.owiczlin.habitify.data.models.Category
import pl.owiczlin.habitify.data.models.Difficulty
import pl.owiczlin.habitify.data.models.Priority
import pl.owiczlin.habitify.ui.components.*
import pl.owiczlin.habitify.ui.theme.LARGE_PADDING
import pl.owiczlin.habitify.ui.theme.MEDIUM_PADDING
import pl.owiczlin.habitify.util.Constants.DESCRIPTION_MAX_HEIGHT
import java.time.LocalDate
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun questContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    category: Category,
    onCategorySelected: (Category) -> Unit,
    difficulty: Difficulty,
    onDifficultySelected: (Difficulty) -> Unit,
    isCompleted: Boolean,
    onSwitchChanged: (Boolean) -> Unit,

    dueDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    dueTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,

    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = R.string.title)) },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        priorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        Log.d("QC", "Priority selected $priority ")
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )

        dateTimePicker(
            dueDate = dueDate,
            dueTime = dueTime,
            onDateSelected = onDateSelected,
            onTimeSelected = onTimeSelected,
        )
        Log.d("QC", "QC date:  $dueDate ")
        Log.d("QC", "QC time: $dueTime ")

        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )

        categoryDropDown(
            category = category,
            onCategorySelected = onCategorySelected
        )
        Log.d("QC", "Category selected: $category")
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        difficultyDropDown(
            difficulty = difficulty,
            onDifficultySelected = onDifficultySelected,
        )
        Log.d("QC", "Difficulty selected: $difficulty")
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = DESCRIPTION_MAX_HEIGHT),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.body1
        )
        Divider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = MaterialTheme.colors.background
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(id = R.string.left_label))
            checkCompleteSwitch(
                isCompleted = isCompleted,
                onSwitchChanged = { isChecked ->
                    onSwitchChanged(isChecked)
                }
            )
            Text(stringResource(id = R.string.right_label))
        }
    }
}