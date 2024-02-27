package pl.owiczlin.habitify.data.models

import androidx.compose.ui.graphics.Color
import pl.owiczlin.habitify.ui.theme.HighPriorityColor
import pl.owiczlin.habitify.ui.theme.LowPriorityColor
import pl.owiczlin.habitify.ui.theme.MediumPriorityColor
import pl.owiczlin.habitify.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor),
}