package com.example.wheeloffortune.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.text.toUpperCase
import com.example.wheeloffortune.data.Category
import com.example.wheeloffortune.data.PlayerData
import com.example.wheeloffortune.data.WheelData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.log

class GamePageViewModel(wheelViewModel: WheelViewModel, category:List<Category>) {
    val category:String?
    val wordToGuess:String
    var shownLetters = mutableListOf<Char>()
    var guessedLetters = mutableListOf<Char>()
    private val alphabet = mutableListOf<Char>()

    private val _playerData:PlayerData
    var wheelViewModel = wheelViewModel
    var lives by mutableStateOf(5)
    var points by mutableStateOf(100)

    var wonGame by mutableStateOf(false)



    init {
        var randomCat = category[(0..category.size-1).random()]
        var randomWord = (0..randomCat.words?.size!!-1).random()
        this.category = randomCat.name
        wordToGuess = randomCat.words!!.get(randomWord)
        var a = 'A'
        while (a <= 'Z'){
            alphabet.add(a)
            ++a
        }
        _playerData = PlayerData(100,5)
        setShownLetterLength()
    }
    private fun setShownLetterLength(){
        for(x in 0 until wordToGuess.length){
            shownLetters.add(' ')
        }
    }
    fun getLettersToShow():List<Char>{
        return shownLetters.toList()
    }
    fun getAlphabetList():List<Char>{
        return alphabet.toList()
    }
    fun evaluateGuessedLetter(guessedLetter:Char){
        var correctGuess = false
        if (guessedLetters.contains(guessedLetter)){
            return
        }
        wheelViewModel.canSpin = true
        wordToGuess.toCharArray().forEachIndexed{index, letter ->
            if (letter == guessedLetter){
                shownLetters[index]=guessedLetter
                addPoints()
                correctGuess = true
            }
        }

        if (correctGuess){

        }else{
            loseLife()
        }

        guessedLetters.add(guessedLetter)
    }

    fun isClicked(letter:Char): Boolean{
        if (guessedLetters.contains(letter)){
            return true
        }
        return false
    }

     fun loseLife(){
         lives = lives-1
        if (lives <=0){
            wonGame
        }
         Log.d("Playerlives in vm",""+ lives)
    }
    private fun addPoints(){

        var tempPoints = wheelViewModel.lastResult.toInt()
        if (tempPoints != null){
            points+=tempPoints
        }
    }

    fun evaluatetotalGuess(guess:String){
        var cleanGuess = guess.uppercase().removeWhitespaces().replace("\n","")
        if (cleanGuess.equals(wordToGuess.uppercase())){
            wonGame = true
        }else{
            loseLife()
        }

    }
    fun String.removeWhitespaces() = replace(" ", "")

}