package pl.owiczlin.habitify.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.owiczlin.habitify.data.models.Category
import pl.owiczlin.habitify.ui.theme.LARGE_PADDING
import pl.owiczlin.habitify.ui.theme.PRIORITY_DROP_DOWN_HEIGHT

@Composable
fun categoryDropDown(
    category: Category,
    onCategorySelected: (Category) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    Row(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { expanded = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = ContentAlpha.disabled
                ),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(
            modifier = androidx.compose.ui.Modifier
                .weight(8f)
                .padding(LARGE_PADDING),
            text = category.name,
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(
            modifier = androidx.compose.ui.Modifier
                .alpha(ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(weight = 1.5f),
            onClick = { expanded = true }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(id = pl.owiczlin.habitify.R.string.drop_down_arrow)
            )
        }
        DropdownMenu(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth(fraction = 0.94f),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.Health)
            }
            ) {
                categoryItem(category = Category.Health)
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.MentalHealth)
            }
            ) {
                categoryItem(category = Category.MentalHealth)
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.PersonalDevelopment)
            }
            ) {
                categoryItem(category = Category.PersonalDevelopment)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.HouseChores)
            }
            ) {
                categoryItem(category = Category.HouseChores)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.Work)
            }
            ) {
                categoryItem(category = Category.Work)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.School)
            }
            ) {
                categoryItem(category = Category.School)
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.Relationships)
            }
            ) {
                categoryItem(category = Category.Relationships)
            }

            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.Finances)
            }
            ) {
                categoryItem(category = Category.Finances)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onCategorySelected(Category.Other)
            }
            ) {
                categoryItem(category = Category.Other)
            }

        }
    }
}


@Composable
@Preview
fun categoryDropDownPreview() {
    categoryDropDown(
        category = Category.Health,
        onCategorySelected = {}
    )
}