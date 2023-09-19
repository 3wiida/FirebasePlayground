package com.mahmoudibrahem.firebaseplayground.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mahmoudibrahem.firebaseplayground.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val SfDisplay = FontFamily(
    Font(resId = R.font.sf_pro_display_bold, weight = FontWeight.Bold),
    Font(resId = R.font.sf_pro_display_regular, weight = FontWeight.Normal),
    Font(resId = R.font.sf_pro_display_medium, weight = FontWeight.Medium),
)