package com.uvenco.ui.characterDetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvenco.R
import com.uvenco.entity.Drink
import com.uvenco.room.DrinkDB
import com.uvenco.ui.home.DrawXLine
import com.uvenco.ui.home.Header
import com.uvenco.ui.home.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(viewModel: HomeViewModel) {
    val drinkReceived by viewModel.selectedDrink.collectAsState()
    val temperature by viewModel.randTemp.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    Column {
        Header(
            viewModel = null,
            navController = null,
            temperature = temperature,
            currentTime = currentTime,
            selectedDrink = null
        )
        DrawXLine()
        drinkReceived?.let { Body(drink = it, viewModel) }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
private fun CharacterItemPreview() {
    val initialDrink = DrinkDB(
        drinkId = 234,
        drinkName = stringResource(R.string.capucino_econom),
        drinkPrice = 199,
        drinkWeight = 0.33,
        withMilk = true,
        isForFree = false
    )

    Column {
        Header(
            viewModel = null,
            navController = null,
            temperature = "85.0°",
            currentTime = "00:00",
            selectedDrink = null
        )
        DrawXLine()
        Body(initialDrink, viewModel = null)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Body(drink: Drink, viewModel: HomeViewModel?) {
    val currentDrink by remember {
        mutableStateOf(
            DrinkDB(
                drinkId = drink.drinkId,
                drinkWeight = drink.drinkWeight,
                drinkPrice = drink.drinkPrice,
                drinkName = drink.drinkName,
                isForFree = drink.isForFree,
                withMilk = drink.withMilk
            )
        )
    }

    var isUnSavedState by remember { mutableStateOf(false) }
    Row {
        Column(modifier = Modifier.padding(start = 287.dp, top = 181.dp)) {
            Text(
                text = stringResource(R.string.detail_name),
                fontSize = 16.sp,
                color = colorResource(id = R.color.detail_screen_name),
            )
            Card(
                modifier = Modifier
                    .size(width = 418.dp, height = 72.dp)
                    .padding(top = 12.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                drink.drinkName?.let {
                    val containerColor = colorResource(id = R.color.detail_box_color)
                    TextField(
                        value = currentDrink.drinkName ?: "",
                        onValueChange = { newText ->
                            currentDrink.drinkName = newText
                            isUnSavedState =
                                currentDrink.isForFree != drink.isForFree ||
                                        currentDrink.drinkName != drink.drinkName ||
                                        currentDrink.drinkPrice != drink.drinkPrice ||
                                        currentDrink.withMilk != drink.withMilk
                        },
                        textStyle = LocalTextStyle
                            .current.copy(
                                color = colorResource(id = R.color.detail_box_text_color),
                                fontSize = 20.sp,
                            ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),

                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(Alignment.CenterVertically),
                    )
                }
            }
            Text(
                text = stringResource(R.string.Price),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 32.dp),
                color = colorResource(id = R.color.detail_screen_name),
            )
            Card(
                modifier = Modifier
                    .size(width = 418.dp, height = 72.dp)
                    .padding(top = 12.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Row {
                    val containerColor = colorResource(id = R.color.detail_box_color)
                    TextField(
                        value = (if (!currentDrink.isForFree) currentDrink.drinkPrice else 0).toString(),
                        onValueChange = { newText: String ->
                            currentDrink.drinkPrice = newText.toInt()
                            isUnSavedState = currentDrink.isForFree != drink.isForFree ||
                                    currentDrink.drinkName != drink.drinkName ||
                                    currentDrink.drinkPrice != drink.drinkPrice ||
                                    currentDrink.withMilk != drink.withMilk
                        },
                        textStyle = LocalTextStyle
                            .current.copy(
                                color = colorResource(id = R.color.detail_box_text_color),
                                fontSize = 20.sp,
                            ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = containerColor,
                            unfocusedContainerColor = containerColor,
                            disabledContainerColor = containerColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),

                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(Alignment.CenterVertically),
                    )
                    Text(
                        text = "₽",
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.bronze_uvenco),
                        modifier = Modifier
                            .background(colorResource(id = R.color.detail_box_color))
                            .fillMaxSize()
                            .wrapContentHeight(Alignment.CenterVertically)
                            .padding(end = 12.dp),
                        textAlign = TextAlign.End
                    )
                }
            }
            Card(
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .padding(top = 12.dp)
                    .size(width = 418.dp, height = 48.dp)
            ) {
                Row(modifier = Modifier.background(colorResource(id = R.color.detail_box_color))) {
                    Text(
                        text = stringResource(R.string.sell_for_free),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.text_color_sell_for_free),
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 24.dp)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.End
                    ) {


                        Switch(
                            modifier = Modifier.padding(end = 12.dp),
                            checked = currentDrink.isForFree,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                uncheckedBorderColor = Color.Transparent,
                                checkedTrackColor = colorResource(id = R.color.orange)
                            ),
                            onCheckedChange = {
                                currentDrink.isForFree = !currentDrink.isForFree
                                isUnSavedState =
                                    currentDrink.isForFree != drink.isForFree ||
                                            currentDrink.drinkName != drink.drinkName ||
                                            currentDrink.drinkPrice != drink.drinkPrice ||
                                            currentDrink.withMilk != drink.withMilk
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    isUnSavedState = false
                    viewModel?.saveChanges(drink = currentDrink)
                },
                enabled = isUnSavedState,
                modifier = Modifier
                    .padding(top = 64.dp)
                    .size(width = 162.dp, height = 52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(
                        id = R.color.orange
                    )
                )
            ) {
                Text(
                    text = stringResource(R.string.Save),
                    fontSize = 14.sp,
                )
            }
        }


        Column(modifier = Modifier.padding(top = 215.dp)) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.milked_cup),
                        contentDescription = null,
                        modifier = Modifier
                            .height(227.dp)
                            .width(200.dp)
                            .clickable {

                                currentDrink.withMilk = true
                                isUnSavedState =
                                    currentDrink.isForFree != drink.isForFree ||
                                            currentDrink.drinkName != drink.drinkName ||
                                            currentDrink.drinkPrice != drink.drinkPrice ||
                                            currentDrink.withMilk != drink.withMilk
                            }
                    )
                    Column {
                        Box(modifier = Modifier.height(25.dp))
                        Image(
                            painter = painterResource(id = R.drawable.cup_without_milk),
                            contentDescription = null,
                            modifier = Modifier
                                .height(210.dp)
                                .width(210.dp)
                                .clickable {
                                    currentDrink.withMilk = false
                                    isUnSavedState = currentDrink.isForFree != drink.isForFree ||
                                            currentDrink.drinkName != drink.drinkName ||
                                            currentDrink.drinkPrice != drink.drinkPrice ||
                                            currentDrink.withMilk != drink.withMilk

                                }
                        )
                    }
                }
                CircularCheckbox(
                    checked = currentDrink.withMilk,
                    onCheckedChange = {},
                    modifier = Modifier.padding(top = 170.dp, start = 88.dp)
                )
                CircularCheckbox(
                    checked = !currentDrink.withMilk,
                    onCheckedChange = {},
                    modifier = Modifier.padding(top = 170.dp, start = 295.dp)
                )
            }
        }
    }
}


@Composable
fun CircularCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(if (checked) colorResource(id = R.color.orange) else Color.Transparent)
            .clickable(onClick = { onCheckedChange(!checked) })
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}