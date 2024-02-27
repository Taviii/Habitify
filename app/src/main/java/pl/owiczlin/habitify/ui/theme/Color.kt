package pl.owiczlin.habitify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import pl.owiczlin.habitify.data.models.Category

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFFCFCFC)
val MediumGray = Color(0xFF9C9C9C)
val DarkGray = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = MediumGray

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else Purple700

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black

@Composable
fun questItemBackground(category: Category): Color {

    val pastelGreen = Color(0xFFC0F0B9)
    val pastelBlue = Color(0xFFC6E7F0)
    val pastelYellow = Color(0xFFF0E8B1)
    val pastelPink = Color(0xFFF0C0E1)
    val pastelGrey = Color(0xFFE0E7F7)
    val pastelLavender = Color(0xFFD4C0FA)
    val pastelOrange = Color(0xFFFAD0C5)
    val pastelBeige = Color(0xFFFAE6D2)
    val pastelSea = Color(0xFFCAFAEB)

    val darkGreen = Color(0xFF458544)
    val darkBlue = Color(0xFF4693BF)
    val darkYellow = Color(0xFFB3A93B)
    val darkPink = Color(0xFFAB4498)
    val darkGrey = Color(0xFF747780)
    val darkLavender = Color(0xFF695494)
    val darkOrange = Color(0xFFB87759)
    val darkBeige = Color(0xFF9E8558)
    val darkSea = Color(0xFF3F9E80)

    val isLightTheme = isSystemInDarkTheme()
    return when (category) {
        Category.Work -> (if (isLightTheme) darkBlue else pastelBlue)
        Category.HouseChores -> (if (isLightTheme) darkYellow else pastelYellow)
        Category.School -> (if (isLightTheme) darkOrange else pastelOrange)
        Category.Health -> (if (isLightTheme) darkSea else pastelSea)
        Category.MentalHealth -> (if (isLightTheme) darkGreen else pastelGreen)
        Category.Finances -> (if (isLightTheme) darkGrey else pastelGrey)
        Category.PersonalDevelopment -> (if (isLightTheme) darkLavender else pastelLavender)
        Category.Relationships -> (if (isLightTheme) darkPink else pastelPink)
        Category.Other -> (if (isLightTheme) darkBeige else pastelBeige)
    }
}


val Colors.questItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray


val Colors.questItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGray else LightGray

val Colors.splashScreenBackground: Color
    @Composable
    get() = if (isLight) Purple700 else Color.Black