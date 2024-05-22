package com.uvenco.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvenco.domain.GetAllDrinksFromDBUseCase
import com.uvenco.domain.InsertDrinkToDBUseCase
import com.uvenco.domain.UpdateDrinkUseCase
import com.uvenco.entity.Drink
import com.uvenco.room.DrinkDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.round
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllDrinksFromDBUseCase: GetAllDrinksFromDBUseCase,
    private val insertDrinkToDBUseCase: InsertDrinkToDBUseCase,
    private val updateDrinkUseCase: UpdateDrinkUseCase
) : ViewModel() {

    private val _randTemp = MutableStateFlow("85°")
    val randTemp: StateFlow<String> = _randTemp.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _randTemp.value
    )
    private val _currentTime = MutableStateFlow("00:00")
    val currentTime: StateFlow<String> = _currentTime.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _currentTime.value
    )
    private val _drinks = MutableStateFlow<List<Drink>>(emptyList())
    val drinks: StateFlow<List<Drink>> = _drinks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _drinks.value
    )
    private var allDrinks: List<Drink> = emptyList()

    val _selectedDrink = MutableStateFlow<Drink?>(null)
    val selectedDrink: StateFlow<Drink?> = _selectedDrink.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _selectedDrink.value
    )


    init {
        checkDB()
        getRandTemperature()
        getCurrentTime()
        getDrinksFromDB()

    }

    private fun getRandTemperature() {
        viewModelScope.launch {
            while (true) {
                val randTemp = round(
                    (Random.nextDouble(from = 85.0, until = 95.0)) * 10.0
                ) / 10.0
                _randTemp.value = "$randTemp°"
                delay(1000)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTime() {
        viewModelScope.launch {
            while (true) {
                val currentTime = LocalTime.now()
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                val formattedTime = currentTime.format(formatter)
                _currentTime.value = formattedTime
                delay(60000)
            }
        }
    }

    private fun checkDB() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                allDrinks = getAllDrinksFromDBUseCase.execute()
            }.fold(
                onSuccess = {
                    if (allDrinks.isEmpty()) {
                        for (item in 1..20) {
                            insertDrinkToDBUseCase.execute(
                                DrinkDB(
                                    drinkId = item,
                                    drinkName = "Капучино эконом",
                                    drinkPrice = Random.nextInt(from = 100, until = 200),
                                    drinkWeight = 0.33,
                                    withMilk = true,
                                    isForFree = false
                                )
                            )
                        }

                    }
                }, onFailure = { Log.d("mytag", it.message.toString()) }
            )
        }
    }

    private fun getDrinksFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                allDrinks = getAllDrinksFromDBUseCase.execute()
            }.fold(
                onSuccess = {
                    if (allDrinks.isNotEmpty()) {
                        _drinks.value = allDrinks
                    } else {//when database initially launched
                        delay(500)
                        getDrinksFromDB()
                    }

                }, onFailure = { Log.d("mytag", it.message.toString()) }
            )
        }
    }

    fun saveChanges(drink: Drink){
        Log.d("mytag","saveCalled")
        viewModelScope.launch (Dispatchers.IO){
            kotlin.runCatching {
                updateDrinkUseCase.execute(drink = drink)
            }.fold(
                onSuccess = {getDrinksFromDB()},
                onFailure = {Log.d("mytag", it.message.toString())}
            )
        }
    }
}