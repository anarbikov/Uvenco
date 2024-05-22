package com.uvenco.entity

interface Drink {
    val drinkId: Int
    var drinkName: String?
    var drinkPrice: Int?
    val drinkWeight: Double?
    var withMilk: Boolean
    var isForFree: Boolean
}