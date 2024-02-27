package pl.owiczlin.habitify.navigation.destinations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.owiczlin.habitify.ui.screens.quest.questScreen
import pl.owiczlin.habitify.viewmodels.SharedViewModel
import pl.owiczlin.habitify.util.Action
import pl.owiczlin.habitify.util.Constants.QUEST_ARGUMENT_KEY
import pl.owiczlin.habitify.util.Constants.QUEST_SCREEN

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.questComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = QUEST_SCREEN,
        arguments = listOf(navArgument(QUEST_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val questId = navBackStackEntry.arguments!!.getInt(QUEST_ARGUMENT_KEY)
        LaunchedEffect(key1 = questId) {
            sharedViewModel.getSelectedQuest(questId = questId)
        }
        val selectedQuest by sharedViewModel.selectedQuest.collectAsState()

        LaunchedEffect(key1 = selectedQuest) {
            if (selectedQuest != null || questId == -1) {
                sharedViewModel.updateQuestFields(selectedQuest = selectedQuest)
            }
        }

        questScreen(
            selectedQuest = selectedQuest,
            sharedViewModel = sharedViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}