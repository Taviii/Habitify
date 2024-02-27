package pl.owiczlin.habitify.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.owiczlin.habitify.navigation.destinations.*
import pl.owiczlin.habitify.viewmodels.SharedViewModel
import pl.owiczlin.habitify.util.Constants.SPLASH_SCREEN

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun setupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToQuestScreen = screen.list,
            sharedViewModel = sharedViewModel,
            navigateToQuestsScreen = screen.toQuestsList,
            navigateToCompletedList = screen.toCompletedList,
            navigateToStatsScreen = screen.toStatsScreen
        )
        completedListComposable(
            navigateToQuestScreen = screen.list,
            sharedViewModel = sharedViewModel,
            navigateToQuestsScreen = screen.toQuestsList,
            navigateToCompletedList = screen.toCompletedList,
            navigateToStatsScreen = screen.toStatsScreen
        )
        statsComposable(
            navigateToQuestScreen = screen.list,
            sharedViewModel = sharedViewModel,
            navigateToQuestsScreen = screen.toQuestsList,
            navigateToCompletedList = screen.toCompletedList,
            navigateToStatsScreen = screen.toStatsScreen
        )
        questComposable(
            navigateToListScreen = screen.quest,
            sharedViewModel = sharedViewModel
        )
    }
}