package com.example.wheeloffortune.view.composables

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.commandiron.spin_wheel_compose.SpinWheel
import com.commandiron.spin_wheel_compose.SpinWheelDefaults
import com.commandiron.spin_wheel_compose.state.SpinWheelState
import com.commandiron.spin_wheel_compose.state.rememberSpinWheelState
import kotlinx.coroutines.launch
import kotlin.math.sign


/*
The spinning wheel comp is taken from a lib.
changed it abit
 */
@Composable
 fun LuckWheel(textList:List<String>, resultDegree:Float){
    val state = rememberSpinWheelState(resultDegree = resultDegree, autoSpinDelay = 2)
    Log.d("Postiion",""+resultDegree)
    val scope = rememberCoroutineScope()
    SpinWheel(
        state = state,
        onClick = { scope.launch { state.animate {pieIndex -> } } }
    ){ pieIndex ->
        Text(text = textList[pieIndex])
    }
 }



@Preview
@Composable
fun WheelPrev(){
    var tempAr = listOf<String>("Bankrupt","Extra Turn","100","100","100","200","200","500")
    LuckWheel(textList = tempAr, resultDegree = 80f)
}
