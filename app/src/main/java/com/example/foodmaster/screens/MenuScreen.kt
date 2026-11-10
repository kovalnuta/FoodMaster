package com.example.foodmaster.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodmaster.Dish
import com.example.foodmaster.R
import com.example.foodmaster.ui.theme.AppFontFamily
import com.example.foodmaster.ui.theme.PrimaryOrange
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    onAddToCart: (Dish, Int) -> Unit,
    onViewAllPromotions: () -> Unit
) {
    val dishes = listOf(
        Dish(stringResource(R.string.dish_pepperoni), stringResource(R.string.dish_pepperoni_desc), 550, R.drawable.peperonni, stringResource(R.string.composition_pepperoni)),
        Dish(stringResource(R.string.dish_cheeseburger), stringResource(R.string.dish_cheeseburger_desc), 350, R.drawable.cheesburger, stringResource(R.string.composition_cheeseburger)),
        Dish(stringResource(R.string.dish_hotdog), stringResource(R.string.dish_hotdog_desc), 250, R.drawable.hotdog, stringResource(R.string.composition_hotdog))
    )

    val categories = listOf(
        stringResource(R.string.cat_pizza) to R.drawable.pizza_icon,
        stringResource(R.string.cat_burger) to R.drawable.burger_icon,
        stringResource(R.string.cat_hotdog) to R.drawable.hotdog,
        stringResource(R.string.cat_drink) to R.drawable.drink1
    )

    var selectedDish by remember { mutableStateOf<Dish?>(null) }
    var quantity by remember { mutableIntStateOf(1) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    // Состояния для отзыва
    var showReviewDialog by remember { mutableStateOf(false) }
    var reviewName by remember { mutableStateOf("") }
    var reviewText by remember { mutableStateOf("") }

    if (showReviewDialog) {
        AlertDialog(
            onDismissRequest = { showReviewDialog = false },
            title = { Text(stringResource(R.string.leave_review), fontFamily = AppFontFamily, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(
                        value = reviewName,
                        onValueChange = { reviewName = it },
                        label = { Text(stringResource(R.string.your_name), fontFamily = AppFontFamily) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { reviewText = it },
                        label = { Text(stringResource(R.string.your_review), fontFamily = AppFontFamily) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    // Здесь потом будет сохранение отзыва
                    showReviewDialog = false
                    reviewName = ""
                    reviewText = ""
                }) {
                    Text(stringResource(R.string.send), color = PrimaryOrange, fontFamily = AppFontFamily, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showReviewDialog = false }) {
                    Text(stringResource(R.string.cancel), color = Color.Gray, fontFamily = AppFontFamily)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface
        )
    }

    if (selectedDish != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedDish = null },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = selectedDish!!.imageRes),
                    contentDescription = selectedDish!!.name,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedDish!!.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = AppFontFamily,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${selectedDish!!.price} ₽",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryOrange,
                            fontFamily = AppFontFamily
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("★", color = PrimaryOrange, fontSize = 12.sp)
                            Text(" 4.9 (25)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = selectedDish!!.composition,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = AppFontFamily,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.total_amount),
                        fontSize = 18.sp,
                        fontFamily = AppFontFamily,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${selectedDish!!.price * quantity} ₽",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryOrange,
                        fontFamily = AppFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier
                                .size(40.dp)
                                .background(PrimaryOrange, RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Minus", tint = Color.White)
                        }

                        Text(
                            text = quantity.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppFontFamily
                        )

                        IconButton(
                            onClick = { quantity++ },
                            modifier = Modifier
                                .size(40.dp)
                                .background(PrimaryOrange, RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Plus", tint = Color.White)
                        }
                    }

                    Button(
                        onClick = {
                            onAddToCart(selectedDish!!, quantity)
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    selectedDish = null
                                    quantity = 1
                                }
                            }
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f)
                            .padding(start = 24.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                    ) {
                        Text(
                            text = stringResource(R.string.add_to_cart),
                            fontSize = 16.sp,
                            fontFamily = AppFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(stringResource(id = R.string.search_placeholder), color = MaterialTheme.colorScheme.onSurfaceVariant, fontFamily = AppFontFamily) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(modifier = Modifier.fillMaxSize().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(stringResource(id = R.string.free_delivery), fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = AppFontFamily, color = MaterialTheme.colorScheme.onSurface)
                        Text(stringResource(id = R.string.delivery_dates), fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontFamily = AppFontFamily)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange, contentColor = Color.White), shape = RoundedCornerShape(8.dp)) {
                            Text(stringResource(id = R.string.order_now), fontFamily = AppFontFamily)
                        }
                    }
                    Image(painter = painterResource(id = R.drawable.delivery2), contentDescription = "Delivery", modifier = Modifier.size(130.dp).padding(start = 8.dp), contentScale = ContentScale.Fit)
                }
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                TextButton(onClick = onViewAllPromotions) {
                    Text(
                        text = stringResource(R.string.view_all),
                        color = PrimaryOrange,
                        fontFamily = AppFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Text(stringResource(id = R.string.categories), fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = AppFontFamily, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(categories) { (name, iconRes) ->
                    Card(
                        modifier = Modifier.size(80.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.fillMaxSize().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Image(painter = painterResource(id = iconRes), contentDescription = name, modifier = Modifier.size(40.dp))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(name, fontFamily = AppFontFamily, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }

        item {
            Text(stringResource(id = R.string.popular), fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = AppFontFamily, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(dishes) { dish ->
                    Card(
                        modifier = Modifier.width(180.dp).clickable {
                            selectedDish = dish
                            quantity = 1
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(painter = painterResource(id = dish.imageRes), contentDescription = dish.name, modifier = Modifier.size(140.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(dish.name, fontFamily = AppFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                            Text("${dish.price} ₽", fontFamily = AppFontFamily, color = PrimaryOrange, fontSize = 16.sp)
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Отзывы гостей",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = AppFontFamily,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(onClick = { showReviewDialog = true }) {
                    Text(
                        text = stringResource(R.string.leave_review),
                        color = PrimaryOrange,
                        fontFamily = AppFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("★★★★★", color = PrimaryOrange, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Анна К.", fontWeight = FontWeight.Bold, fontFamily = AppFontFamily)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Очень вкусная пицца, привезли быстро и горячей!", fontSize = 13.sp, fontFamily = AppFontFamily, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("★★★★☆", color = PrimaryOrange, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Иван С.", fontWeight = FontWeight.Bold, fontFamily = AppFontFamily)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Бургеры супер, но ждал чуть дольше обычного.", fontSize = 13.sp, fontFamily = AppFontFamily, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}