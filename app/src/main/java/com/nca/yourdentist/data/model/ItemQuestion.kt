package com.nca.yourdentist.data.model

data class ItemQuestion(
    val title: String,
    val answer: String? = null,
    val yesOrNoQuestion: Boolean = true
)