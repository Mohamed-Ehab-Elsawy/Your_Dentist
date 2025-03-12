package com.nca.yourdentist.data.model

data class ItemQuestion(
    val title: String,
    var answer: String = "",
    val yesOrNoQuestion: Boolean = true
)