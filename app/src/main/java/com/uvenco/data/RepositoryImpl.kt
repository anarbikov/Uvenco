package com.uvenco.data

import com.uvenco.domain.RepositoryInterface
import com.uvenco.entity.Drink
import com.uvenco.room.DrinkDB
import com.uvenco.room.DrinkDao
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val drinkDao: DrinkDao,
): RepositoryInterface
{

    override suspend fun insertDrinkToDB(drink: Drink) {
        drinkDao.insertDrink(drink = DrinkDB(
            drinkId = drink.drinkId,
            drinkName = drink.drinkName,
            drinkPrice = drink.drinkPrice,
            drinkWeight = drink.drinkWeight,
            withMilk = drink.withMilk,
            isForFree = false
        ))
    }

    override fun getAllDrinksFromDB(): List<Drink> = drinkDao.getAllDrinks()
    override suspend fun updateDrink(drink: Drink) {
        drinkDao.updateDrink(drink = DrinkDB(
            drinkId = drink.drinkId,
            drinkName = drink.drinkName,
            drinkPrice = drink.drinkPrice,
            drinkWeight = drink.drinkWeight,
            withMilk = drink.withMilk,
            isForFree = drink.isForFree
        )
        )
    }


}