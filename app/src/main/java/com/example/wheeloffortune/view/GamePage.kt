package com.example.wheeloffortune.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.FabPosition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.compose.WheelOfFortuneTheme
import com.example.wheeloffortune.data.PlayerData
import com.example.wheeloffortune.view.composables.AlphabetGrid
import com.example.wheeloffortune.view.composables.BottomBar
import com.example.wheeloffortune.view.composables.LuckWheel
import com.example.wheeloffortune.view.composables.Topbar
import com.example.wheeloffortune.viewmodel.GamePageViewModel
import com.example.wheeloffortune.viewmodel.WheelViewModel


@Composable
fun GamePageScreen(viewmodel: GamePageViewModel) {
    var wheelPopUpControl by remember { mutableStateOf(false) }
    var alphabetPopUpControl by remember { mutableStateOf(false) }
    var guessPopUpController by remember { mutableStateOf(false) }

    var wheelViewModel = viewmodel.wheelViewModel
    var canSpinWheel by remember { mutableStateOf(wheelViewModel.wheelData.value!!.canSpin)}
    var guessText by remember { mutableStateOf("Your Guess") }
    var playerPoints by remember { mutableStateOf(viewmodel.playerData.points)}

    Log.d("canSpin local var", canSpinWheel.toString())
    androidx.compose.material.Scaffold(
        topBar = { Topbar("points: " + playerPoints) },
        content = {
            Log.d("canSpin local var", canSpinWheel.toString())

            if (wheelPopUpControl) {
                WheelPopup ({
                    wheelPopUpControl = false
                    canSpinWheel = false
                },wheelViewModel
                )
            }

            if (alphabetPopUpControl) {
                AlphabetPopUp(
                    onDismiss = {
                        alphabetPopUpControl = false
                        canSpinWheel = true

                    },
                    letters = viewmodel.getAlphabetList(),
                    onClick = {
                        if (!canSpinWheel) {
                            viewmodel.evaluateGuessedLetter(it)
                            alphabetPopUpControl = false
                        }
                    },
                    viewmodel
                )
            }
            if (guessPopUpController) {
                GuessPopUp(
                    onDismiss = { guessPopUpController = false },
                    onChange = {guessText = it},
                    guessText
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Box(modifier = Modifier.height(400.dp)) {
                    LetterGrid(letters = viewmodel.getLettersToShow())
                }
            }
        },
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AlphabetButton ({
                    if(!wheelViewModel.wheelData.value!!.canSpin){
                        alphabetPopUpControl = true
                    }
                },
                canSpinWheel)
                SpinButton({
                    wheelPopUpControl = true
                }, canSpinWheel)
                GuessPopUpButton {
                    guessPopUpController = true
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = { BottomBar(viewmodel.getLifes().toString()) })

}


@Composable
fun WheelPopup(onDismiss: () -> Unit,wheelViewModel: WheelViewModel) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismiss
    ) {
        LuckWheel(
            textList = wheelViewModel.wheelData.value?.possibleResults!!, resultDegree = wheelViewModel.calculateResult()
        ) {
        }

    }
}

@Composable
fun AlphabetPopUp(
    onDismiss: () -> Unit,
    letters: List<Char>,
    onClick: (Char) -> Unit,
    viewmodel: GamePageViewModel
) {
    Popup(
        alignment = Alignment.BottomCenter,
        onDismissRequest = onDismiss
    ) {
        Box(modifier = Modifier.height(300.dp)) {
            AlphabetGrid(
                letters = letters, onClick = { onClick.invoke(it) },
                viewmodel
            )
        }
    }

}

@Composable
fun LetterGrid(letters: List<Char>) {
    Box(Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(), elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Spacer(modifier = Modifier.padding(5.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(40.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                letters.forEachIndexed { index, c ->
                    item { LetterBox(char = c) }
                }
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    CategoryCard("test")
                }

            }
        }
    }
}

@Composable
fun CategoryCard(text: String) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ), elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, style = MaterialTheme.typography.displayMedium)
        }
    }
}

@Composable
fun LetterBox(char: Char) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .wrapContentWidth(),
        shape = MaterialTheme.shapes.medium,

        colors = CardDefaults.cardColors(
            containerColor = Color.White,

            ),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Text(
            text = char.toString(), modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .wrapContentWidth()
                .wrapContentHeight()
                .firstBaselineToTop(45.dp),
            textAlign = TextAlign.Center, style = MaterialTheme.typography.displayLarge
        )
    }

}

@Composable
fun SpinButton(onClick: () -> Unit, disable: Boolean) {
    FloatingActionButton(
        onClick = onClick, modifier = Modifier.size(70.dp),
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
        containerColor = if (disable) {
            Color.Gray
        } else {
            MaterialTheme.colorScheme.primary
        }
    ) {
        Icon(Icons.Filled.PlayArrow, "Local description")
    }
}

@Composable
fun AlphabetButton(onClick: () -> Unit, disable: Boolean) {
    FloatingActionButton(
        onClick = onClick, modifier = Modifier.size(60.dp),
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
        containerColor = if(disable){Color.Gray} else {MaterialTheme.colorScheme.primaryContainer}
    ) {
        Icon(Icons.Filled.Edit, "Local description")
    }
}

@Composable
fun GuessPopUpButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick, modifier = Modifier.size(60.dp),
        elevation = FloatingActionButtonDefaults.elevation(10.dp),
    ) {
        Icon(Icons.Filled.Info, "Local description")
    }
}

@Composable
fun GuessPopUp(onDismiss: () -> Unit, onChange: (String) -> Unit, textValue: String) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismiss
    ) {
        androidx.compose.material.OutlinedTextField(
            value = textValue,
            singleLine = true,
            colors = androidx.compose.material.TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            onValueChange = { onChange.invoke(it) })
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
fun GamePagePreview() {
    WheelOfFortuneTheme {
        GamePageScreen(GamePageViewModel(WheelViewModel( PlayerData(100,5))))
    }
}

