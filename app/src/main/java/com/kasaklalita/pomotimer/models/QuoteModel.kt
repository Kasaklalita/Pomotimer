package com.kasaklalita.pomotimer.models

data class QuoteModel (
    val id: Int,
    val quote: String,
    val author: String
) : java.io.Serializable