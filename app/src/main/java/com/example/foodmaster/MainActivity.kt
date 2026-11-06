package com.example.foodmaster

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodmaster.screens.*
import com.example.foodmaster.ui.theme.AppFontFamily
import com.example.foodmaster.ui.theme.FoodMasterTheme
import com.example.foodmaster.ui.theme.PrimaryOrange
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val systemLang = Locale.getDefault().language
        val locale = Locale(systemLang)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        createConfigurationContext(config)

        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

            val systemDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember {
                mutableStateOf(prefs.getBoolean("is_dark_theme", systemDarkTheme))
            }

            FoodMasterTheme(darkTheme = isDarkTheme) {
                MainAppScreen(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = {
                        isDarkTheme = it
                        prefs.edit().putBoolean("is_dark_theme", it).apply()
                    }
                )
            }
        }
    }
}

@Composable
fun MainAppScreen(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    val navController = rememberNavController()
    val cartItems = remember { mutableStateListOf<Dish>() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "auth" && currentRoute != "settings") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            BottomNavItemPainter(
                                icon = painterResource(id = R.drawable.menu_food),
                                label = stringResource(id = R.string.menu),
                                isSelected = currentRoute == "menu",
                                onClick = { navController.navigate("menu") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } }
                            )
                        }

                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            BottomNavItemPainter(
                                icon = painterResource(id = R.drawable.table4),
                                label = stringResource(id = R.string.booking),
                                isSelected = currentRoute == "booking",
                                onClick = { navController.navigate("booking") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } }
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .offset(y = (-16).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryOrange)
                                    .clickable { navController.navigate("cart") { launchSingleTop = true } },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.basket),
                                    contentDescription = "Cart",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            BottomNavItemPainter(
                                icon = painterResource(id = R.drawable.user_icon),
                                label = stringResource(id = R.string.profile),
                                isSelected = currentRoute == "profile",
                                onClick = { navController.navigate("profile") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } }
                            )
                        }

                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            BottomNavItemPainter(
                                icon = painterResource(id = R.drawable.settings),
                                label = stringResource(id = R.string.settings),
                                isSelected = currentRoute == "settings",
                                onClick = { navController.navigate("settings") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } }
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "auth", modifier = Modifier.padding(innerPadding)) {
            composable("auth") {
                AuthScreen(onLoginSuccess = {
                    navController.navigate("menu") { popUpTo("auth") { inclusive = true } }
                })
            }
            composable("menu") {
                MenuScreen(onAddToCart = { dish, quantity ->
                    repeat(quantity) {
                        cartItems.add(dish)
                    }
                })
            }
            composable("cart") {
                CartScreen(
                    cartItems = cartItems,
                    onRemoveItem = { cartItems.remove(it) },
                    onCheckout = { cartItems.clear() }
                )
            }
            composable("booking") { BookingScreen() }
            composable("profile") { ProfileScreen() }
            composable("settings") {
                SettingsScreen(
                    onBackClick = { navController.popBackStack() },
                    isDarkTheme = isDarkTheme,
                    onThemeChange = onThemeChange
                )
            }
        }
    }
}

@Composable
fun BottomNavItemPainter(icon: Painter, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = label,
            tint = if (isSelected) PrimaryOrange else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = if (isSelected) PrimaryOrange else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp,
            fontFamily = AppFontFamily
        )
    }
}