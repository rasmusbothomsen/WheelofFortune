package com.example.wheeloffortune.view.composables

import androidx.compose.foundation.layout.height
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Topbar(){

    TopAppBar( modifier = Modifier.height(65.dp),
        title = { Text("title", textAlign = TextAlign.Center) },

        navigationIcon = {
            IconButton(onClick = {
                TODO()
            })
            {
            }
        },


        backgroundColor = Color.LightGray
    )
}
