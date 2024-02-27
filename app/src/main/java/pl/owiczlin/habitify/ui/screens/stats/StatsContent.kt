package pl.owiczlin.habitify.ui.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pl.owiczlin.habitify.data.models.HabitifyQuest
import pl.owiczlin.habitify.data.models.Priority
import pl.owiczlin.habitify.data.models.Stats
import pl.owiczlin.habitify.ui.theme.*
import pl.owiczlin.habitify.util.RequestState
import java.time.LocalDateTime

@Composable
fun statsContent(
    allCompletedQuests: RequestState<List<HabitifyQuest>>,

    ) {
    val allQuests = when (allCompletedQuests) {
        is RequestState.Success -> allCompletedQuests.data
        else -> emptyList()
    }

    val stats = calculateStats(allQuests)


    Column {
        handleStatsContent(stats = stats)
        Divider(modifier = Modifier.fillMaxWidth().height(5.dp))
        questPointsList(quests = allQuests)
    }
}

@Composable
fun questPointsList(quests: List<HabitifyQuest>) {
    val completedQuests = quests.filter { it.isCompleted }

    LazyColumn {
        items(completedQuests) { quest ->
            questPointsItem(quest = quest)
        }
    }
}

@Composable
fun questPointsItem(quest: HabitifyQuest) {
    val points = calculatePointsForQuest(quest)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = SMALL_PADDING)
            .height(30.dp),
        color = MaterialTheme.colors.questItemBackgroundColor,
        shape = RectangleShape,
        elevation = QUEST_ITEM_ELEVATION,
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
                .padding(start = MEDIUM_PADDING, end = MEDIUM_PADDING),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = quest.title,
                color = MaterialTheme.colors.questItemTextColor,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "+ $points Exp",
                color = MaterialTheme.colors.questItemTextColor,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun handleStatsContent(
    stats: Stats

) {
    displayStats(
        stats = stats
    )
}

@Composable
fun displayStats(
    stats: Stats
) {
    statsItem(
        stats = stats
    )
}


@Composable
fun statsItem(
    stats: Stats
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.questItemBackgroundColor,
        shape = RectangleShape,
        elevation = QUEST_ITEM_ELEVATION,
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            statsRow("Completed Quests", stats.completedQuestCount.toString())
            statsRow("Exp", String.format("%.2f", stats.sumExp.toDouble()))
            statsRow("Average Exp per completed Quest", String.format("%.2f", stats.avExp))
            statsRow("Most Completed Difficulty", stats.mostCompletedDifficulty?.name ?: "N/A")
            statsRow("Most Completed Category", stats.mostCompletedCategory?.name ?: "N/A")
            statsRow("Most Completed Priority", stats.mostCompletedPriority?.name ?: "N/A")
            statsRow("Quests Completed", String.format("%.2f%%", stats.completionPercentage))
        }
    }
}

@Composable
fun statsRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(3f)
                .padding(end = MEDIUM_PADDING)
                .wrapContentWidth(Alignment.Start),
            text = label,
            color = MaterialTheme.colors.questItemTextColor,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = MEDIUM_PADDING)
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = ":",
            color = MaterialTheme.colors.questItemTextColor,
            style = MaterialTheme.typography.body1
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            text = value,
            color = MaterialTheme.colors.questItemTextColor,
            style = MaterialTheme.typography.body1,
            maxLines = 2, // Dodane, aby tekst mógł przejść do drugiej linii
            overflow = TextOverflow.Ellipsis
        )
    }
}

fun calculatePointsForQuest(quest: HabitifyQuest): Int {
    val priorityMultiplier = when (quest.priority) {
        Priority.LOW -> 1.0
        Priority.MEDIUM -> 3.0
        Priority.HIGH -> 6.0
        Priority.NONE -> 0
    }

    val difficultyExpMultiplier = quest.difficulty.exp.toDouble()
    val completionDateTime = LocalDateTime.now()

    // Jeśli zadanie jest ukończone i zmiana statusu z false na true nastąpiła przed dueDate i dueTime
    return if (quest.isCompleted && completionDateTime.isBefore(
            LocalDateTime.of(
                quest.dueDate,
                quest.dueTime
            )
        )
    ) {
        ((difficultyExpMultiplier + priorityMultiplier.toDouble()) * 2).toInt()
    } else
    // Kalkulacja punktów na podstawie trudności, priorytetu i czasu
        (difficultyExpMultiplier + priorityMultiplier.toDouble()).toInt()
}


fun calculateStats(quests: List<HabitifyQuest>): Stats {
    val completedQuests = quests.filter { it.isCompleted }

    val completedQuestCount = completedQuests.size

    //Suma uzyskanych Exp za ukończone Questy
    val sumExp = if (completedQuestCount > 0) {
        completedQuests.sumOf { calculatePointsForQuest(it) }
    } else {
        0.0
    }

    //Średnia wartość uzyskanych Exp za ukończone Questy
    val avExp = if (completedQuestCount > 0) {
        sumExp.toDouble() / completedQuestCount.toDouble()
    } else {
        0.0
    }
    //Najczęstszy poziom trudności ukończonych Questów
    val mostCompletedDifficulty = if (completedQuestCount > 0) {
        completedQuests.groupBy { it.difficulty }
            .maxByOrNull { it.value.size }
            ?.key
    } else {
        null
    }
    //Najczęstsza kategoria ukończonych Questów
    val mostCompletedCategory = if (completedQuestCount > 0) {
        completedQuests.groupBy { it.category }
            .maxByOrNull { it.value.size }
            ?.key
    } else {
        null
    }
    //Najczęstszy priorytet ukończonych Questów
    val mostCompletedPriority = if (completedQuestCount > 0) {
        completedQuests.groupBy { it.priority }
            .maxByOrNull { it.value.size }
            ?.key
    } else {
        null
    }
    //Procent ukończonych Questów
    val completionPercentage = if (quests.isNotEmpty()) {
        (completedQuestCount.toDouble() / quests.size.toDouble()) * 100.0
    } else {
        0.0
    }

    return Stats(
        sumExp = sumExp,
        completedQuestCount = completedQuestCount,
        avExp = avExp,
        mostCompletedDifficulty = mostCompletedDifficulty,
        mostCompletedCategory = mostCompletedCategory,
        mostCompletedPriority = mostCompletedPriority,
        completionPercentage = completionPercentage
    )
}
