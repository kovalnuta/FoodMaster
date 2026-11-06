package com.example.foodmaster.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.foodmaster.R

val AppFontFamily = FontFamily(
    Font(R.font.caacupeoneregular, FontWeight.Normal)
)

val Typography = Typography(
    bodyLarge = TextStyle(fontFamily = AppFontFamily, fontSize = 16.sp),
    titleLarge = TextStyle(fontFamily = AppFontFamily, fontSize = 22.sp),
    labelSmall = TextStyle(fontFamily = AppFontFamily, fontSize = 11.sp)
)