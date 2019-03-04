package com.tzy.tzydemo.sidebar.entity

/**
 * Created by tanzy on 2019/3/2 0002.
 */
data class WordEntity(
        var letter: String,
        var word: String) :Comparable<WordEntity>{

    override fun compareTo(other: WordEntity): Int {
        return letter.compareTo(other.letter)
    }

}