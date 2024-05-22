package com.uvenco.domain

import com.uvenco.entity.Drink

interface RepositoryInterface {

    suspend fun insertDrinkToDB (drink: Drink)
    fun getAllDrinksFromDB():List<Drink>
    suspend fun updateDrink(drink: Drink)


}