package com.example.wheeloffortune

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wheeloffortune.data.Category
import com.example.wheeloffortune.data.PlayerData
import com.example.wheeloffortune.view.EndPage
import com.example.wheeloffortune.view.GamePageScreen
import com.example.wheeloffortune.viewmodel.GamePageViewModel
import com.example.wheeloffortune.viewmodel.WheelViewModel

@Composable
fun NavController() {
    val navController = rememberNavController()

    var w1 = "POTATOS"
    var w2 = "CARROT"
    var w3 = "FISH"
    var cat1 = Category("Food", listOf(w1,w2,w3).toMutableList())
    var w4 = "BERLIN"
    var w5 = "ROSKILDE"
    var w6 = "BORUP"
    var cat2 = Category("Cities", listOf(w4,w5,w6).toMutableList())


    val category = listOf(cat1,cat2)

    NavHost(navController = navController, startDestination = Screen.GameScreen.route) {
        composable(route = Screen.GameScreen.route) {
            GamePageScreen(viewmodel = GamePageViewModel(WheelViewModel(PlayerData(100,5)),category), navController)
        }
        composable(route = Screen.EndScreen.route) {
            EndPage(navController)
        }
    }


}
