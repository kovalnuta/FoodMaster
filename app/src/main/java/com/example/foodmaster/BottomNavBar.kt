package com.example.foodmaster

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.foodmaster.ui.theme.AppFontFamily
import com.example.foodmaster.ui.theme.PrimaryOrange

@Composable
fun CustomBottomNavBar(
    navController: NavHostController,
    currentRoute: String?
) {
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
                BottomNavItem(
                    icon = painterResource(id = R.drawable.menu_food),
                    label = stringResource(id = R.string.menu),
                    isSelected = currentRoute == "menu",
                    onClick = { navController.navigate("menu") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } }
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                BottomNavItem(
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
                BottomNavItem(
                    icon = painterResource(id = R.drawable.user_icon),
                    label = stringResource(id = R.string.profile),
                    isSelected = currentRoute == "profile",
                    onClick = { navController.navigate("profile") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } }
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                BottomNavItem(
                    icon = painterResource(id = R.drawable.settings),
                    label = stringResource(id = R.string.settings),
                    isSelected = currentRoute == "settings",
                    onClick = { navController.navigate("settings") { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true } }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(icon: Painter, label: String, isSelected: Boolean, onClick: () -> Unit) {
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