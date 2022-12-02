package com.example.wheeloffortune

sealed class Screen(val route: String) {
    object GameScreen : Screen("gamepage")
    object EndScreen : Screen ("endpage")
}
