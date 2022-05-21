package com.maker.hanger.data

data class Clothes(
    var clothesIdx: Int,
    var clothesImage: String,
    var date: String,
    var session: String,
    var kind: ArrayList<String>,
    var washingMethod: ArrayList<Int>,
    var size: Char,
    var bookmark: Boolean
)
