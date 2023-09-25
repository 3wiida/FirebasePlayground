package com.mahmoudibrahem.firebaseplayground.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mahmoudibrahem.firebaseplayground.pojo.School

@Composable
fun DataSection(
    modifier: Modifier = Modifier,
    data: List<School>,
    onEditSchoolClicked: (School) -> Unit = {},
    onDeleteSchoolClicked: (School) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = data) { school ->
            SchoolItem(
                school = school,
                onEditClicked = onEditSchoolClicked,
                onDeleteClicked = onDeleteSchoolClicked
            )
        }
    }
}