package com.example.wheeloffortune.view.composables

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.commandiron.spin_wheel_compose.DefaultSpinWheel
import kotlinx.coroutines.launch


    @Composable
    fun LuckWheel(textList:List<String>, resultDegree:Float, onFinish:() -> Unit) {
        var test by remember {
            mutableStateOf(false)
        }
        DefaultSpinWheel(isSpinning = test, onClick = { test = true},
            onFinish = {
                test = false
                onFinish.invoke()},
        resultDegree = resultDegree)
        { pieIndex ->
            Text(text = textList[pieIndex])
        }
    }



@Preview
@Composable
fun WheelPrev(){
}
