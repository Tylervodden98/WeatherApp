package com.example.weatherapp.screens.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.widgets.WeatherAppBar
import kotlinx.coroutines.flow.forEach

@Composable
fun FavouriteScreen(navController: NavController, favouriteViewModel: FavouriteViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favourite Cities",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false){
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()) {
            Column(modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(), horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.Center) {
                val list = favouriteViewModel.favList.collectAsState().value

                LazyColumn(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                    items(items = list){
                        CityRow(it, navController, favouriteViewModel)
                    }
                }
            }
        }
    }

}

@Composable
fun CityRow(favourite: Favourite, navController: NavController, favouriteViewModel: FavouriteViewModel) {
    Surface(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth().height(50.dp).clickable {
            navController.navigate(WeatherScreens.MainScreen.name + "/${favourite.city}")
        }, shape = RoundedCornerShape(10.dp), color = Color(
        0xFF75A3BB), elevation = 3.dp) {
        Row(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(), verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {

            Text(text = favourite.city, fontSize = 16.sp, fontWeight = FontWeight.Medium)

            Surface(modifier = Modifier.padding(0.dp), shape = CircleShape, color = Color(
                0xFFBFCFDA)) {

                Text(text = favourite.country, modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.caption)
            }

            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete Icon", modifier = Modifier.clickable {
                favouriteViewModel.deleteFavourite(favourite)
            }, tint = Color.Red.copy(alpha = 0.3f))
        }
    }
}
