package com.example.wheeloffortune.view.composables

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun BottomBar(lives:String){
    BottomAppBar(modifier = Modifier.height(65.dp), backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.onTertiaryContainer) {
        Row(horizontalArrangement = Arrangement.Start) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)){
                Icon(Icons.Default.Favorite,"Lives", modifier = Modifier.matchParentSize(), tint = androidx.compose.ui.graphics.Color.Red)
                Text(text = lives, fontWeight = FontWeight.Bold)
            }
        }

    }
}