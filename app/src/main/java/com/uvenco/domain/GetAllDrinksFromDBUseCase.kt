package com.uvenco.domain

import com.uvenco.entity.Drink
import com.uvenco.room.DrinkDB
import javax.inject.Inject

class GetAllDrinksFromDBUseCase @Inject constructor(
    private val repository: RepositoryInterface
){
    fun execute():List<Drink> = repository.getAllDrinksFromDB()

}