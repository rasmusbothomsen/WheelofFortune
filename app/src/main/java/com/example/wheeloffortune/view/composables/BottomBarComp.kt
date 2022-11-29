package com.example.wheeloffortune.view.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BottomBar(){
    BottomAppBar(modifier = Modifier.height(65.dp), cutoutShape = CircleShape,) {
        BottomNavigation() {
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Default.Menu, "") },
                label = { Text(text = "Menu") },
                selected = false,
                onClick = {})
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Default.Search, "") },
                label = { Text(text = "Søg") },
                selected = false,
                onClick = {})
        }
    }
}