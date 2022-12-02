package com.example.wheeloffortune.data

data class WheelData(
    val possibleResults: MutableList<String>,
    var lastResult:String?,
    var canSpin:Boolean
)
