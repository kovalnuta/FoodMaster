package com.example.foodmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodmaster.Dish
import com.example.foodmaster.R
import com.example.foodmaster.ui.theme.AppFontFamily
import com.example.foodmaster.ui.theme.PrimaryOrange

@Composable
fun CartScreen(
    cartItems: MutableList<Dish>,
    onRemoveItem: (Dish) -> Unit,
    onCheckout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.cart_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = AppFontFamily,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(stringResource(id = R.string.cart_empty), fontFamily = AppFontFamily, fontSize = 18.sp)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { dish ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(dish.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = AppFontFamily)
                                Text(dish.price, fontSize = 16.sp, color = PrimaryOrange, fontFamily = AppFontFamily)
                            }
                            IconButton(onClick = { onRemoveItem(dish) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = stringResource(id = R.string.cart_delete),
                                    tint = PrimaryOrange
                                )
                            }
                        }
                    }
                }
            }

            val total = cartItems.sumOf { it.price.replace(" ₽", "").toIntOrNull() ?: 0 }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(id = R.string.cart_total), fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = AppFontFamily)
                Text("$total ₽", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryOrange, fontFamily = AppFontFamily)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onCheckout,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text(stringResource(id = R.string.cart_checkout), fontFamily = AppFontFamily, fontSize = 18.sp)
            }
        }
    }
}