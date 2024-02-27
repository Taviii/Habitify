package pl.owiczlin.habitify.navigation

import androidx.navigation.NavHostController
import pl.owiczlin.habitify.util.Action
import pl.owiczlin.habitify.util.Constants.COMPLETED_SCREEN
import pl.owiczlin.habitify.util.Constants.LIST_SCREEN
import pl.owiczlin.habitify.util.Constants.SPLASH_SCREEN
import pl.owiczlin.habitify.util.Constants.STATS_SCREEN

class Screens(navController: NavHostController) {
    val splash: () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    val list: (Int) -> Unit = { questId ->
        navController.navigate("quest/$questId")
    }

    val quest: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val toQuestsList: () -> Unit = {
        navController.navigate("list/${Action.NO_ACTION.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    val toCompletedList: () -> Unit = {
        navController.navigate("completed/${Action.NO_ACTION.name}") {
            popUpTo(COMPLETED_SCREEN) { inclusive = true }
        }
    }

    val toStatsScreen: () -> Unit = {
        navController.navigate("stats/${Action.NO_ACTION.name}") {
            popUpTo(STATS_SCREEN) { inclusive = true }
        }
    }
}