package com.example.wheeloffortune.view.composables

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Topbar(points:String){

    TopAppBar( modifier = Modifier.height(65.dp),
        title = { Text(text= points, textAlign = TextAlign.Center, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium) },

        navigationIcon = {
            IconButton(onClick = {
                TODO()
            })
            {
                Icon(Icons.Default.Close, "Close app")
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )
}
