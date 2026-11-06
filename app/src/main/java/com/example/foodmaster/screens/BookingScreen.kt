package com.example.foodmaster.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodmaster.R
import com.example.foodmaster.ui.theme.AppFontFamily
import com.example.foodmaster.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen() {
    var selectedRestaurant by remember { mutableStateOf("Тверская, 12") }
    var expandedRestaurant by remember { mutableStateOf(false) }
    val restaurants = listOf("Тверская, 12", "Арбат, 5", "Невский проспект, 40")

    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var guests by remember { mutableStateOf("") }

    val places = listOf(
        stringResource(R.string.place_window),
        stringResource(R.string.place_veranda),
        stringResource(R.string.place_bar),
        stringResource(R.string.place_lodge)
    )
    var selectedPlace by remember { mutableStateOf(places[0]) }

    // ДОБАВЛЕНО: выбор столика
    val tables = listOf(
        stringResource(R.string.table_1),
        stringResource(R.string.table_2),
        stringResource(R.string.table_3)
    )
    var selectedTable by remember { mutableStateOf(tables[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.booking_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = AppFontFamily,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(stringResource(R.string.booking_location), fontFamily = AppFontFamily, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expandedRestaurant,
            onExpandedChange = { expandedRestaurant = !expandedRestaurant }
        ) {
            OutlinedTextField(
                value = selectedRestaurant,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.booking_address), fontFamily = AppFontFamily) },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = PrimaryOrange) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRestaurant) },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryOrange,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            )
            ExposedDropdownMenu(
                expanded = expandedRestaurant,
                onDismissRequest = { expandedRestaurant = false }
            ) {
                restaurants.forEach { restaurant ->
                    DropdownMenuItem(
                        text = { Text(restaurant, fontFamily = AppFontFamily) },
                        onClick = {
                            selectedRestaurant = restaurant
                            expandedRestaurant = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text(stringResource(id = R.string.booking_date), fontFamily = AppFontFamily) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryOrange,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            )
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text(stringResource(id = R.string.booking_time), fontFamily = AppFontFamily) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryOrange,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = guests,
            onValueChange = { guests = it },
            label = { Text(stringResource(id = R.string.booking_seats), fontFamily = AppFontFamily) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryOrange,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(stringResource(R.string.booking_place), fontFamily = AppFontFamily, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            places.forEach { place ->
                val isSelected = selectedPlace == place
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedPlace = place },
                    label = {
                        Text(
                            text = place,
                            fontFamily = AppFontFamily,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryOrange,
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ДОБАВЛЕНО: Блок выбора столика
        Text(stringResource(R.string.booking_table), fontFamily = AppFontFamily, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            tables.forEach { table ->
                val isSelected = selectedTable == table
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedTable = table },
                    label = {
                        Text(
                            text = table,
                            fontFamily = AppFontFamily,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryOrange,
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
        ) {
            Text(
                text = stringResource(id = R.string.book_btn),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = AppFontFamily,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}