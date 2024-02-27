package pl.owiczlin.habitify.data.models

data class Stats(
    val sumExp: Number,
    val completedQuestCount: Int,
    val avExp: Double,
    val mostCompletedDifficulty: Difficulty?,
    val mostCompletedCategory: Category?,
    val mostCompletedPriority: Priority?,
    val completionPercentage: Double
)