package pl.owiczlin.habitify.navigation.destinations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.owiczlin.habitify.ui.screens.stats.statsScreen
import pl.owiczlin.habitify.viewmodels.SharedViewModel
import pl.owiczlin.habitify.util.Constants.LIST_ARGUMENT_KEY
import pl.owiczlin.habitify.util.Constants.STATS_SCREEN
import pl.owiczlin.habitify.util.toAction

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.statsComposable(
    navigateToQuestScreen: (questId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    navigateToStatsScreen: () -> Unit,
    navigateToQuestsScreen: () -> Unit,
    navigateToCompletedList: () -> Unit

) {
    composable(
        route = STATS_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }

        val databaseAction by sharedViewModel.action

        statsScreen(
            action = databaseAction,
            navigateToQuestScreen = navigateToQuestScreen,
            sharedViewModel = sharedViewModel,
            navigateToStatsScreen = navigateToStatsScreen,
            navigateToQuestsScreen = navigateToQuestsScreen,
            navigateToCompletedList = navigateToCompletedList
        )
    }
}