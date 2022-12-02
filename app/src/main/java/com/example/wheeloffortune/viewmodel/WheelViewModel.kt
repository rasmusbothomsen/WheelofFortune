package com.example.wheeloffortune.viewmodel

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringArrayResource
import com.example.wheeloffortune.R
import com.example.wheeloffortune.data.PlayerData
import com.example.wheeloffortune.data.WheelData

class WheelViewModel(playerData: PlayerData) {
    var playerData = playerData
    private val _wheelData: MutableState<WheelData?>
    val wheelData: State<WheelData?>

    init {
        var tempAr = mutableListOf<String>("Bankrupt","Extra Turn","100","100","100","200","200","500")
        _wheelData = mutableStateOf(WheelData(tempAr,null,true))
        wheelData = _wheelData
    }

    fun calculateResult():Float{
        var randomNumber = (1..8).random()
        wheelData.value?.canSpin = false
        executeResult(randomNumber)
        return ((360/randomNumber)-10).toFloat()
    }

    private fun executeResult(result:Int){
        when(result){
            1 -> playerData.points = 0
            2 -> wheelData.value?.canSpin = true

            else -> {
                wheelData.value?.lastResult = wheelData.value?.possibleResults?.get(result-1)
            }
        }
    }


}