package com.example.wheeloffortune.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose.WheelOfFortuneTheme
import com.example.compose.md_theme_dark_outline
import com.example.wheeloffortune.view.composables.BottomBar
import com.example.wheeloffortune.view.composables.Topbar
import com.example.wheeloffortune.viewmodel.GamePageViewModel


@SuppressLint("NotConstructor")
    @Composable
    fun GamePageScreen(viewmodel:GamePageViewModel){
            androidx.compose.material.Scaffold(
                topBar = { Topbar()}
                ,content = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,)
                {
                    Box(modifier = Modifier.height(400.dp)) {
                        LetterGrid(letters = viewmodel.getCharList())
                    }
                }
            }
            , bottomBar = { BottomBar()})
        }




    @Composable
    fun LetterGrid(letters: List<Char>) {
        Box(Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
            Card(modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
            , elevation = CardDefaults.cardElevation(10.dp)) {
                Spacer(modifier = Modifier.padding(5.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(6),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    letters.forEachIndexed { index, c ->
                        item { LetterBox(char = c) }
                    }

                }
            }
        }
    }

    @Composable
    fun LetterBox(char: Char){
        OutlinedCard(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .wrapContentWidth(),
            shape = MaterialTheme.shapes.medium,

            colors = CardDefaults.cardColors(
                containerColor = Color.White,

                ),
            elevation = CardDefaults.cardElevation(5.dp),
            ) {
            Text(text = char.toString(), modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .wrapContentWidth()
                .wrapContentHeight()
                .firstBaselineToTop(45.dp),
                textAlign = TextAlign.Center, style = MaterialTheme.typography.displayLarge)
        }

    }
//region $ModifierFun
/*
This fun is taken from:
https://developer.android.com/jetpack/compose/layouts/basics
 */
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}

//endregion


    @Preview
    @Composable
    fun GamePagePreview(){
        WheelOfFortuneTheme {
            GamePageScreen(GamePageViewModel())

        }
    }

