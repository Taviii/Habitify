package pl.owiczlin.habitify.ui.components

import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun checkCompleteSwitch(
    isCompleted: Boolean,
    onSwitchChanged: (Boolean) -> Unit
) {
    Text(
        String()
    )
    Switch(
        checked = isCompleted,
        onCheckedChange = { newCheckedState ->
            onSwitchChanged(newCheckedState)
        }
    )
}