package com.example.foodmaster

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodmaster.screens.*

@Composable
fun AppNavHost(
    navController: NavHostController,
    cartItems: MutableList<Dish>,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onCheckout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "auth",
        modifier = modifier
    ) {
        composable("auth") {
            AuthScreen(onLoginSuccess = {
                navController.navigate("menu") { popUpTo("auth") { inclusive = true } }
            })
        }
        composable("menu") {
            MenuScreen(
                onAddToCart = { dish, quantity ->
                    repeat(quantity) { cartItems.add(dish) }
                },
                onViewAllPromotions = { navController.navigate("promotions") }
            )
        }
        composable("cart") {
            CartScreen(
                cartItems = cartItems,
                onRemoveItem = { cartItems.remove(it) },
                onCheckout = onCheckout
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
        composable("promotions") {
            PromotionsScreen(onBackClick = { navController.popBackStack() })
        }
    }
}