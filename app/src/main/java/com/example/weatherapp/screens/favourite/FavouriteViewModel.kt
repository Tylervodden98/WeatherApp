package com.example.weatherapp.screens.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.repository.WeatherDbRepository
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: WeatherDbRepository):
    ViewModel(){
        private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
            val favList = _favList.asStateFlow()
    
    init {
        viewModelScope.launch(Dispatchers.IO) { 
            repository.getFavourites().distinctUntilChanged()
                .collect { listOfFavs ->
                    if(listOfFavs.isNullOrEmpty()){
                        Log.d("Favs", "Empty Favs: ")
                    }else{
                        _favList.value = listOfFavs
                        Log.d("Listfav", "${favList.value}: ")
                    }
                }
        }    
    }

    fun insertFavourite(favourite: Favourite) = viewModelScope.launch { repository.insertFavourite(favourite) }

    fun updateFavourite(favourite: Favourite) = viewModelScope.launch { repository.updateFavourite(favourite) }

    fun deleteFavourite(favourite: Favourite) = viewModelScope.launch { repository.deleteFavourite(favourite) }

    }