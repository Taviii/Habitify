package pl.owiczlin.habitify.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.owiczlin.habitify.util.Constants
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = Constants.DATABASE_TABLE)
data class HabitifyQuest(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val isCompleted: Boolean,
    val category: Category,
    val difficulty: Difficulty,
    val dueDate: LocalDate,
    val dueTime: LocalTime
)



