package com.example.wheeloffortune.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.wheeloffortune.Screen
import com.example.wheeloffortune.view.composables.BottomBar
import com.example.wheeloffortune.view.composables.Topbar

@Composable
fun EndPage(navController: NavController, lives:Int){
    
    Scaffold(topBar = {Topbar(points = "")},
    content = {
        Column() {
            Text(text = if (lives>0){"You Won!"}else{"Game Over"}, style = androidx.compose.material3.MaterialTheme.typography.displayLarge)
            Button(onClick = { navController.navigate(Screen.GameScreen.route)}) {
                Text(text = "New game")
            }
        }
    },
    bottomBar = { BottomBar(lives ="" )})
}