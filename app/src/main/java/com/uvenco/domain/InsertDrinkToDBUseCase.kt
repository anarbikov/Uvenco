package com.uvenco.domain

import com.uvenco.entity.Drink
import javax.inject.Inject

class InsertDrinkToDBUseCase @Inject constructor(
    private val repository: RepositoryInterface
){
    suspend fun execute(drink: Drink) {
        repository.insertDrinkToDB(drink = drink)
    }
}