package com.example.wheeloffortune.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wheeloffortune.view.firstBaselineToTop
import com.example.wheeloffortune.viewmodel.GamePageViewModel

@Composable
fun LetterClick(char:Char, onClick: (Char) -> Unit, clicked:Boolean){
    OutlinedCard(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .wrapContentWidth().clickable {
            if (!clicked){
                onClick.invoke(char)
            } },
        shape = MaterialTheme.shapes.medium,

        colors = CardDefaults.cardColors(
            containerColor = if (!clicked){ Color.White} else {Color.Gray},

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
@Composable
fun AlphabetGrid(letters: List<Char>,onClick: (Char) -> Unit , viewModel: GamePageViewModel) {
    Box(Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
        Card(modifier = Modifier
            .align(Alignment.Center)
            .fillMaxSize()
            , elevation = CardDefaults.cardElevation(10.dp)) {
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
                    item { LetterClick(char = c,onClick, viewModel.isClicked(c)) }
                }
            }
        }
    }
}