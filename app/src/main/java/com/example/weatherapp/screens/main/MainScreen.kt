package com.example.weatherapp.screens.main

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.settings.SettingsViewModel
import com.example.weatherapp.utils.formatDate
import com.example.weatherapp.utils.formatDecimals
import com.example.weatherapp.widgets.*

@Composable
fun MainScreen (
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val curCity: String = if (city!!.isNullOrBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if(!unitFromDb.isNullOrEmpty()){
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(initialValue = DataOrException(loading = true)){
            value = mainViewModel.getWeatherData(city = curCity, units = unit)
        }.value

        if (weatherData.loading == true){
            CircularProgressIndicator()
        }
        else if( weatherData.data != null){
            MainScaffold(weather = weatherData.data!!, navController, isImperial = isImperial)
        }
    }

}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city.name + ", ${weather.city.country}", navController = navController, onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name) }, elevation = 5.dp){
            Log.d("Click", "MainScaffold: Button Clicked")
        }
    }) {
        MainContent(data = weather, weatherItem = weather.list[0], isImperial = isImperial)
    }
}

@Composable
fun MainContent(data: Weather, weatherItem: WeatherItem, isImperial: Boolean) {
    val imageUrl = "https://openweathermap.org/img/wn/${data!!.list[0].weather[0].icon}.png"

    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


        Text(text = formatDate(weatherItem.dt) , style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSecondary, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(6.dp))

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFc400)
        ) {

            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                //Image
                WeatherStateImage(imageUrl = imageUrl)

                Text(text = formatDecimals(weatherItem.temp.day) + "°", style = MaterialTheme.typography.h4, fontWeight = FontWeight.ExtraBold)
                Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)
        Divider()
        SunsetAndRiseRow(weather = weatherItem)
        Text(text = "This Week's Forecast", modifier = Modifier.padding(top = 6.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
        ForecastThisWeek(weatherWeek = data)
    }

}

