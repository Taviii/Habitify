package pl.owiczlin.habitify.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import pl.owiczlin.habitify.ui.screens.splash.splashScreen
import pl.owiczlin.habitify.util.Constants.SPLASH_SCREEN

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit,
) {
    composable(
        route = SPLASH_SCREEN,
    ) {
        splashScreen(navigateToListScreen = navigateToListScreen)
    }
}