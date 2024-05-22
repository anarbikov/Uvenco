package com.uvenco.room

import androidx.room.*
import com.uvenco.entity.Drink

@Dao
interface DrinkDao {

    @Insert (entity = DrinkDB::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrink (drink: DrinkDB)

    @Transaction
    @Query("SELECT * FROM drink")
    fun getAllDrinks (): List<DrinkDB>

 //   @Query ("UPDATE drink SET isForFree =:isForFree WHERE drinkId = :drinkId")
//    fun updateDrink(isForFree:Boolean, drinkId:Int, drinkName:String, drinkPrice:Int,withMilk:Boolean)

    @Update (DrinkDB::class,onConflict = OnConflictStrategy.REPLACE)
    fun updateDrink(drink: DrinkDB )
}