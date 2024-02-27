package pl.owiczlin.habitify.util

import androidx.compose.ui.unit.dp

object Constants {
    const val DATABASE_TABLE = "habitify_table"
    const val DATABASE_NAME = "habitify_database"

    const val LIST_SCREEN = "list/{action}"
    const val QUEST_SCREEN = "quest/{questId}"
    const val COMPLETED_SCREEN = "completed/{action}"
    const val STATS_SCREEN = "stats/{action}"

    const val LIST_ARGUMENT_KEY = "action"
    const val QUEST_ARGUMENT_KEY = "questId"

    const val MAX_TITLE_LENGTH = 30
    val DESCRIPTION_MAX_HEIGHT = 300.dp

    const val PREFERENCE_NAME = "habitify_preferences"
    const val PREFERENCE_KEY = "sort_state"

    const val SPLASH_SCREEN = "splash"
    const val SPLASH_SCREEN_DELAY = 2000L

}