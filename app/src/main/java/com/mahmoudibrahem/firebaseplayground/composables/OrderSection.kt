package com.mahmoudibrahem.firebaseplayground.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mahmoudibrahem.firebaseplayground.R

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    onOrderAscClicked: () -> Unit = {},
    onOrderDesClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { onOrderAscClicked() }
        ) {
            Text(text = stringResource(R.string.order_asc))
        }
        Button(
            onClick = { onOrderDesClicked() }
        ) {
            Text(text = stringResource(R.string.order_des))
        }
    }
}