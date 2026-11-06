package com.example.foodmaster.screens

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodmaster.MainActivity
import com.example.foodmaster.R
import com.example.foodmaster.ui.theme.AppFontFamily
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var isEnglish by remember { mutableStateOf(Locale.getDefault().language == "en") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.settings_title), fontFamily = AppFontFamily, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(id = R.string.dark_theme), fontFamily = AppFontFamily, fontSize = 18.sp)
                Switch(checked = isDarkTheme, onCheckedChange = { onThemeChange(it) })
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(id = R.string.english_lang), fontFamily = AppFontFamily, fontSize = 18.sp)
                Switch(
                    checked = isEnglish,
                    onCheckedChange = { checked ->
                        isEnglish = checked
                        val language = if (checked) "en" else "ru"
                        val locale = Locale(language)
                        Locale.setDefault(locale)
                        val config = Configuration(configuration)
                        config.setLocale(locale)

                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        (context as? MainActivity)?.finish()
                    }
                )
            }
        }
    }
}