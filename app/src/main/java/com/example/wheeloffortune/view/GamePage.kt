package com.example.wheeloffortune.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.compose.WheelOfFortuneTheme
import com.example.wheeloffortune.Screen
import com.example.wheeloffortune.data.PlayerData
import com.example.wheeloffortune.view.composables.AlphabetGrid
import com.example.wheeloffortune.view.composables.BottomBar
import com.example.wheeloffortune.view.composables.LuckWheel
import com.example.wheeloffortune.view.composables.Topbar
import com.example.wheeloffortune.viewmodel.GamePageViewModel
import com.example.wheeloffortune.viewmodel.WheelViewModel



@Composable
fun GamePageScreen(viewmodel: GamePageViewModel, navController: NavController) {
    var wheelPopUpControl by remember { mutableStateOf(false) }
    var alphabetPopUpControl by remember { mutableStateOf(false) }
    var guessPopUpController by remember { mutableStateOf(false) }
    var guessText by remember {
        mutableStateOf("")
    }

    androidx.compose.material.Scaffold(
        topBar = {
        Topbar(points = "Points "+viewmodel.points)
    },
    content = {

        if (wheelPopUpControl){
            WheelPopup(onDismiss = { wheelPopUpControl = false},
                wheelViewModel = viewmodel.wheelViewModel, viewmodel )
        }
        if (alphabetPopUpControl) {
            AlphabetPopUp(
                onDismiss = {
                    alphabetPopUpControl = false

                },
                letters = viewmodel.getAlphabetList(),
                onClick = {
                    if (!viewmodel.wheelViewModel.canSpin) {
                        viewmodel.evaluateGuessedLetter(it)
                        alphabetPopUpControl = false
                    }
                },
                viewmodel
            )
        }
        if(viewmodel.wonGame){
            navController.navigate(Screen.EndScreen.route)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Box(modifier = Modifier.height(400.dp)) {
                LetterGrid(letters = viewmodel.getLettersToShow(),viewmodel.category!!)
            }
            if (guessPopUpController) {
                GuessPopUp(
                    onDismiss = { guessPopUpController = false },
                    onChange = {guessText = it},
                    guessText,
                    onClick = {viewmodel.evaluatetotalGuess(guessText)}
                )
            }
        }
    },
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AlphabetButton ({
                    if(!viewmodel.wheelViewModel.canSpin){
                        alphabetPopUpControl = true
                    }
                },
                    viewmodel.wheelViewModel.canSpin)
                SpinButton({
                    if (viewmodel.wheelViewModel.canSpin){
                        wheelPopUpControl = true
                    }
                }, !viewmodel.wheelViewModel.canSpin)
                GuessPopUpButton {
                    guessPopUpController = true
                }
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomBar(lives = ""+viewmodel.lives)
        }
    )
}


@Composable
fun WheelPopup(onDismiss: () -> Unit,wheelViewModel: WheelViewModel, viewmodel: GamePageViewModel) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismiss
    ) {
        LuckWheel(
            textList = wheelViewModel.possibleResults, resultDegree = wheelViewModel.calculateResult(viewmodel)
        )

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
        Box(modifier = Modifier.height(350.dp)) {
            AlphabetGrid(
                letters = letters, onClick = { onClick.invoke(it) },
                viewmodel
            )
        }
    }

}

@Composable
fun LetterGrid(letters: List<Char>, category:String) {
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
                    CategoryCard(category)
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
fun GuessPopUp(onDismiss: () -> Unit, onChange: (String) -> Unit, textValue: String, onClick: () -> Unit) {

        Row() {
            androidx.compose.material.TextField(
                value = textValue,
                singleLine = true,
                colors = androidx.compose.material.TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                onValueChange = onChange,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {onClick.invoke()}))
        androidx.compose.material.Button(onClick = onClick) {
            Text(text = "Guess This Word")

        }
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
    }
}

