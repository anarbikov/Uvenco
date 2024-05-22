package com.uvenco.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uvenco.entity.Drink
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "drink")
data class DrinkDB(
    @PrimaryKey
    override val drinkId: Int,
    override var drinkName: String?,
    override var drinkPrice: Int?,
    override val drinkWeight: Double?,
    override var withMilk: Boolean,
    override var isForFree: Boolean

):Parcelable, Drink
