package com.nca.yourdentist.data.model

data class City(val nameEn: String, val nameAr: String) {
    companion object{
        val citiesMap = hashMapOf(
            1 to City("Cairo", "القاهرة"),
            2 to City("Giza", "الجيزة")
        )
    }
}