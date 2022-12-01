package com.example.wheeloffortune.viewmodel

import com.example.wheeloffortune.data.PlayerData

class GamePageViewModel(wheelViewModel: WheelViewModel) {
    val wordToGuess:String
    var shownLetters = mutableListOf<Char>()
    var guessedLetters = mutableListOf<Char>()
    val playerData:PlayerData
    private val alphabet = mutableListOf<Char>()

    init {
        wordToGuess = "ThIs is a Test"
        /** TODO **/
        var a = 'A'
        while (a <= 'Z'){
            alphabet.add(a)
            ++a
        }
        setShownLetterLength()
        playerData = PlayerData(100,5)
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
        playerData.lives -=1
        if (playerData.lives<=0){
            /** TODO END GAME **/
        }
    }
    private fun addPoints(){
    }

    fun getPoints():Int{
        return playerData.points
    }
    fun getLifes():Int{
        return playerData.lives
    }


}