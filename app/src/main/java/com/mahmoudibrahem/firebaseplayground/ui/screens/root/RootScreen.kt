package com.mahmoudibrahem.firebaseplayground.ui.screens.root

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.pojo.FirebaseFeature
import com.mahmoudibrahem.firebaseplayground.ui.theme.MainColor
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay
import com.mahmoudibrahem.firebaseplayground.util.shadow

@Composable
fun RootScreen(
    viewModel: RootViewModel = hiltViewModel(),
    onNavigateToAuth: () -> Unit,
    onNavigateToRemoteConfig: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Firebase PlayGround 🤸‍♀️️",
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(1.dp))
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(items = viewModel.getFeaturesList()) { item ->
                FeatureItem(
                    feature = item,
                    onFeatureClicked = { id ->
                        when (id) {
                            1 -> onNavigateToAuth()
                            2 ->onNavigateToRemoteConfig()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FeatureItem(
    feature: FirebaseFeature,
    onFeatureClicked: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier

            .size(width = 125.dp, height = 180.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = MainColor, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onFeatureClicked(feature.id)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = feature.image),
            contentDescription = null,
            modifier = Modifier.size(50.dp, 50.dp)
        )
        Text(
            text = feature.name,
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}