package com.example.foodmaster.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodmaster.R
import com.example.foodmaster.ui.theme.AppFontFamily
import com.example.foodmaster.ui.theme.PrimaryOrange

data class Order(val id: String, val date: String, val price: String, val status: String)

@Composable
fun ProfileScreen() {
    val userOrders = listOf(
        Order("№1254", "Вчера, 19:30", "1 500 ₽", "Доставлен"),
        Order("№1189", "12 октября", "2 300 ₽", "Готовится"),
        Order("№1050", "5 октября", "890 ₽", "Доставлен")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_icon),
                    contentDescription = "Аватар",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Анна Коваленко",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFontFamily,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Золотой клиент • 450 бонусов",
                    fontSize = 14.sp,
                    color = PrimaryOrange,
                    fontFamily = AppFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryOrange)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Ваш баланс бонусов",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            fontFamily = AppFontFamily
                        )
                        Text(
                            text = "450 ₽",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = AppFontFamily
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }

        item {
            Text(
                text = "История заказов",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AppFontFamily,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(userOrders) { order ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Заказ ${order.id}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppFontFamily,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = order.date,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = AppFontFamily
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = order.price,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppFontFamily,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        val statusColor = if (order.status == "Доставлен") Color(0xFF4CAF50) else PrimaryOrange
                        Text(
                            text = order.status,
                            fontSize = 13.sp,
                            color = statusColor,
                            fontFamily = AppFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}