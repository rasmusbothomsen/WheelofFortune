package com.example.wheeloffortune.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.wheeloffortune.data.PlayerData
import com.example.wheeloffortune.data.WheelData

class GamePageViewModel(wheelViewModel: WheelViewModel) {
    val wordToGuess:String
    var shownLetters = mutableListOf<Char>()
    var guessedLetters = mutableListOf<Char>()
    private val _playerData: MutableState<PlayerData?>
    val playerData: State<PlayerData?>
    private val alphabet = mutableListOf<Char>()
    var wheelViewModel = wheelViewModel

    init {
        wordToGuess = "KARTOFFLER"
        /** TODO **/
        var a = 'A'
        while (a <= 'Z'){
            alphabet.add(a)
            ++a
        }
        _playerData = mutableStateOf(PlayerData(100,5))
        playerData = _playerData
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

    private fun loseLife(){
        playerData.value?.lives?.dec()
        if (playerData.value?.lives!! <=0){
            /** TODO END GAME **/
        }
    }
    private fun addPoints(){
        /*
        var tempPoints = wheelViewModel.wheelData.lastResult?.toInt()
        if (tempPoints != null){
            playerData.points += wheelViewModel.wheelData.lastResult?.toInt()!!
        }

         */
    }

    fun getPoints():Int?{
        return playerData.value?.points
    }


}