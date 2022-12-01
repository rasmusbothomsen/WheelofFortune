package com.example.wheeloffortune.viewmodel

import com.example.wheeloffortune.data.WheelData

class WheelViewModel {
    val wheelData:WheelData

    init {
        wheelData = WheelData(mutableListOf(),null)
    }

    fun calculateResult():Float{
        var randomNumber = (1..8).random()
        wheelData.lastResult = wheelData.possibleResults.get(randomNumber-1)
        return (360/randomNumber).toFloat()
    }
}