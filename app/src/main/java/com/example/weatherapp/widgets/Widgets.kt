package com.example.weatherapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.utils.formatDateDay
import com.example.weatherapp.utils.formatDateTime
import com.example.weatherapp.utils.formatDecimals

@Composable
fun ForecastThisWeek(weatherWeek: Weather) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxSize(), shape = RoundedCornerShape(size = 14.dp), color = Color(0xFFEEF1EF)
    ) {

        LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            items(items = weatherWeek.list){ item: WeatherItem ->
                val imageUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"
                Card(modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(), shape = RoundedCornerShape(6.dp), backgroundColor = Color.White, elevation = 4.dp) {
                    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {
                        //Day
                        Text(text = formatDateDay(item.dt), style = MaterialTheme.typography.h3, color = MaterialTheme.colors.onSecondary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        //Icon
                        WeatherStateImage(imageUrl = imageUrl)
                        //Forecast
                        Surface(modifier = Modifier.width(IntrinsicSize.Max).padding(2.dp), shape = RoundedCornerShape(6.dp), color = Color(0xFFFFc400)) {
                            Text(
                                text = item.weather[0].description,
                                style = MaterialTheme.typography.subtitle2,
                                color = MaterialTheme.colors.onSecondary,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp
                            )
                        }
                        //High & Lows
                        Text(text = formatDecimals(item.temp.max) + "°", color = Color(0xFF394E9E))
                        Text(text = formatDecimals(item.temp.min) + "°", color = Color(0xFF8C94B4))
                    }


                }
            }
        }

    }
}




@Composable
fun SunsetAndRiseRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(top = 12.dp, bottom = 10.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription = "sunrise icon", modifier = Modifier.size(30.dp))
            Text(text = formatDateTime(weather.sunrise).uppercase(), style = MaterialTheme.typography.caption)
        }

        Row() {
            Text(text = formatDateTime(weather.sunset).uppercase(), style = MaterialTheme.typography.caption)
            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription = "sunset icon", modifier = Modifier.size(30.dp))
        }

    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "Humidity icon", modifier = Modifier.size(20.dp))
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.caption)
        }

        Row() {
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "pressure icon", modifier = Modifier.size(20.dp))
            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.caption)
        }

        Row() {
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "wind icon", modifier = Modifier.size(20.dp))
            Text(text = formatDecimals(weather.speed) + if (isImperial) "mph" else "m/s", style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter(imageUrl), contentDescription = "icon image", modifier = Modifier.size(80.dp))
}
