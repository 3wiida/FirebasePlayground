package com.mahmoudibrahem.firebaseplayground.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mahmoudibrahem.firebaseplayground.R

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    searchQuery:String,
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            modifier = Modifier.weight(2f),
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            singleLine = true,
            placeholder = { Text(text = stringResource(R.string.search)) }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(2.dp)),
            onClick = onSearchClicked
        ) {
            Text(text = stringResource(R.string.search))
        }
    }
}