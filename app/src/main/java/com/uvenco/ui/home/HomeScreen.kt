package com.uvenco.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvenco.R
import com.uvenco.entity.Drink
import com.uvenco.navigation.Screens
import com.uvenco.room.DrinkDB

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val temperature by viewModel.randTemp.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val drinksData by viewModel.drinks.collectAsState()
    var selectedItem by remember { mutableStateOf<Drink?>(null) }

    Column {
        Header(
            viewModel = viewModel,
            navController = navController,
            temperature = temperature,
            currentTime = currentTime,
            selectedDrink = selectedItem
        )
        DrawXLine()
        Body(
            drinks = drinksData,
            onItemSelected = { drink -> selectedItem = drink },
            selectedDrink = selectedItem
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Header(
    viewModel: HomeViewModel?,
    navController: NavController?,
    temperature: String,
    currentTime: String,
    selectedDrink: Drink?
) {
    Row(
        modifier = Modifier
            .padding(start = 26.dp, end = 26.dp, top = 16.dp)
            .height(38.dp)
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.runero_logo),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.runero_touch_logo),
            fontSize = 16.sp,
            color = colorResource(R.color.bronze_uvenco),
            modifier = Modifier
                .padding(start = 12.dp)
                .clickable {
                    selectedDrink?.let {
                        viewModel?._selectedDrink!!.value = it
                        navController?.navigate(Screens.Detail.route)
                    }
                },
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,

            ) {
            Text(
                text = currentTime,
                fontSize = 16.sp,
                color = colorResource(R.color.bronze_uvenco),
                modifier = Modifier.padding(horizontal = 24.dp),
            )
            DrawYLine()
            Text(
                text = temperature,
                fontSize = 16.sp,
                color = colorResource(R.color.bronze_uvenco),
                modifier = Modifier.padding(start = 24.dp, end = 1.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.drop),
                modifier = Modifier.padding(top = 5.dp, end = 24.dp),
                contentDescription = null
            )
            DrawYLine()
            Image(
                painter = painterResource(id = R.drawable.ru_country_logo),
                modifier = Modifier.padding(top = 1.dp, start = 24.dp, end = 4.dp),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.ru_country),
                fontSize = 16.sp,
                color = colorResource(R.color.bronze_uvenco),
                modifier = Modifier.padding(end = 24.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
private fun CharacterItemPreview() {
    val drinks = mutableListOf<DrinkDB>()
    for (i in 1 until 20)
        drinks.add(
            DrinkDB(
                drinkId = 234,
                drinkName = stringResource(R.string.capucino_econom),
                drinkPrice = 199,
                drinkWeight = 0.33,
                withMilk = true,
                isForFree = false
            )
        )
    drinks[2].withMilk = false

    Column {
        Header(
            viewModel = null,
            navController = null,
            temperature = "85.0°",
            currentTime = "00:00",
            selectedDrink = null
        )
        DrawXLine()
        Body(drinks = drinks, onItemSelected = {}, selectedDrink = null)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Body(
    drinks: List<Drink>,
    onItemSelected: (Drink) -> Unit,
    selectedDrink: Drink?
) {
    val gradBrushMainFrame = Brush.linearGradient(
        colors = listOf(
            colorResource(R.color.frame_gradient_1),
            colorResource(R.color.frame_gradient_2)
        )
    )
    val gradBrushBottomFrame = Brush.linearGradient(
        colors = listOf(
            colorResource(R.color.bottom_frame_grad_1),
            colorResource(R.color.bottom_frame_grad_2),
            colorResource(R.color.bottom_frame_grad_3)
        )
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 227.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 24.dp)
    ) {
        items(drinks.size) { index ->
            val item = drinks[index]
            Card(
                modifier = Modifier
                    .size(width = 227.dp, height = 313.dp)
                    .padding(start = 24.dp, top = 24.dp)
                    .clickable { onItemSelected(item) },
                shape = RoundedCornerShape(6.dp),
            ) {
                Column(modifier = selectedDrink?.let {
                    if (it.drinkId != item.drinkId)
                        Modifier.background(brush = gradBrushMainFrame)
                    else Modifier.background(color = Color.Red)
                } ?: Modifier.background(brush = gradBrushMainFrame))
                {
                    Image(
                        painter = painterResource(
                            id = if (item.withMilk)
                                R.drawable.milked_cup else R.drawable.cup_without_milk
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(166.dp)
                            .padding(top = if (item.withMilk) 10.dp else 30.dp),
                        contentDescription = null
                    )
                    item.drinkName?.let {
                        Text(
                            text = it,
                            fontSize = 17.sp,
                            color = colorResource(R.color.text_color_drink),
                            modifier = Modifier
                                .padding(top = 6.dp, bottom = 18.dp)
                                .fillMaxWidth()
                                .height(57.dp)
                                .wrapContentHeight(Alignment.CenterVertically),
                            textAlign = TextAlign.Center
                        )
                    }
                    Row(modifier = Modifier.background(brush = gradBrushBottomFrame)) {
                        if (!item.isForFree) {
                            Text(
                                text = item.drinkWeight.toString(),
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.drink_weight_text),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = 16.dp)
                                    .wrapContentHeight(Alignment.CenterVertically)
                            )
                        } else {
                            Text(
                                text = item.drinkWeight.toString(),
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.drink_weight_text),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentHeight(Alignment.CenterVertically),
                                textAlign = TextAlign.Center
                            )
                        }
                        item.drinkPrice?.let {
                            Text(
                                text = "$it ₽",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 16.dp)

                                    .wrapContentHeight(Alignment.CenterVertically),
                                fontSize = 18.sp,
                                color = colorResource(id = R.color.drink_price_text),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DrawXLine() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Divider(
            thickness = 1.dp,
            color = colorResource(R.color.line_uvenco),
        )
    }
}

@Composable
fun DrawYLine() {
    Box(modifier = Modifier.height(54.dp)) {
        val colorRes = colorResource(R.color.line_uvenco)
        Canvas(
            modifier = Modifier.height(54.dp)
        ) {
            drawLine(
                color = colorRes,
                start = center.copy(y = -68f),
                end = center.copy(y = size.height),
                strokeWidth = 2f
            )
        }
    }
}
