package com.example.wheeloffortune.viewmodel

import android.content.res.Resources
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringArrayResource
import com.example.wheeloffortune.R
import com.example.wheeloffortune.data.PlayerData
import com.example.wheeloffortune.data.WheelData

class WheelViewModel(playerData: PlayerData) {
    var lastResult by mutableStateOf("")
    var canSpin by mutableStateOf(true)
    var possibleResults: List<String>

    init {
        var tempAr = listOf<String>("Bankrupt","Extra Turn","100","100","100","200","200","500")
        possibleResults = tempAr
    }

    fun calculateResult(viewModel: GamePageViewModel):Float{
        var randomNumber = (1..8).random()
        canSpin = false
        executeResult(randomNumber, viewModel)
        Log.d("number: "+randomNumber, "Effect"+ possibleResults[randomNumber-1])
        var indexToEnd = (360/(possibleResults.size+1))*randomNumber
        return (indexToEnd-10).toFloat()
    }

    private fun executeResult(result:Int, viewModel: GamePageViewModel){
        when(result){
            1 -> viewModel.points = 0
            2 -> canSpin = true
            else -> {
                lastResult = possibleResults[result-1]
            }
        }

    }


}