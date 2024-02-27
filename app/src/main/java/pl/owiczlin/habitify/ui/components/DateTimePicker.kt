package pl.owiczlin.habitify.ui.components

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import pl.owiczlin.habitify.ui.theme.MEDIUM_PADDING
import pl.owiczlin.habitify.ui.theme.SMALL_PADDING
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun dateTimePicker(
    dueDate: LocalDate?,
    dueTime: LocalTime?,
    onDateSelected: (LocalDate) -> Unit,
    onTimeSelected: (LocalTime) -> Unit,

    ) {

    val initialDate = LocalDate.now()
    val initialTime = LocalTime.now()

    var pickedDate by remember {
        mutableStateOf(dueDate ?: initialDate)
    }
    Log.d("DTP", "pickedDate by remember: $pickedDate")

    var pickedTime by remember {
        mutableStateOf(dueTime ?: initialTime)
    }
    Log.d("DTP", "pickedTime by remember: $pickedTime")


    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MMM yyyy").format(pickedDate)
        }
    }


    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm").format(pickedTime)
        }
    }
    Log.d("DTP", "formattedTime : $formattedTime")

    Log.d("DTP", "formattedDate : $formattedDate")


    val context = LocalContext.current

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()


    LaunchedEffect(dueDate, dueTime) {
        // Aktualizacja wartości pickedDate i pickedTime po zmianie dueDate lub dueTime
        pickedDate = dueDate ?: initialDate
        pickedTime = dueTime ?: initialTime
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = MEDIUM_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(modifier = Modifier.weight(1f).padding(SMALL_PADDING), onClick = {
            dateDialogState.show()
        }) {
            Text(
                text = if (dueDate != null) "Deadline date:\n $formattedDate" else "Pick Date",
                style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )
            )
        }
        Spacer(modifier = Modifier.width(MEDIUM_PADDING))

        Button(modifier = Modifier.weight(1f).padding(SMALL_PADDING), onClick = {
            timeDialogState.show()
        }) {
            Text(
                text = if (dueTime != null) "Deadline time:\n $formattedTime" else "Pick Time",
                style = TextStyle(
                    fontSize = 16.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )
            )
        }
    }

    Log.d("DateTimePicker", "Selected formatted time: $formattedTime")


    MaterialDialog(dialogState = dateDialogState, properties = DialogProperties(
        dismissOnBackPress = true,
    ), buttons = {
        positiveButton(text = "Save") {
            onDateSelected(pickedDate)
            Toast.makeText(
                context, "Date picked succesfully", Toast.LENGTH_LONG
            ).show()
        }
        negativeButton(text = "Cancel")
    }) {
        datepicker(
            initialDate = pickedDate,
            title = "Pick a date",
        ) {
            pickedDate = it
        }
    }


    MaterialDialog(dialogState = timeDialogState, properties = DialogProperties(
        dismissOnBackPress = true
    ), buttons = {
        positiveButton(text = "Save") {
            onTimeSelected(pickedTime)
            Toast.makeText(
                context, "Time picked succesfully", Toast.LENGTH_LONG
            ).show()
        }
        negativeButton(text = "Cancel")
    }) {
        timepicker(
            initialTime = pickedTime,
            title = "Pick a time",
        ) {
            pickedTime = it
        }
    }
    Log.d("DateTimePicker", "Selected date: $formattedDate")
}
