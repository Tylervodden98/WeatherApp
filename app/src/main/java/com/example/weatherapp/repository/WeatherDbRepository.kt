package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavourites(): Flow<List<Favourite>> = weatherDao.getFavorites()

    suspend fun insertFavourite(favourite: Favourite) = weatherDao.insertFavourite(favourite)

    suspend fun updateFavourite(favourite: Favourite) = weatherDao.updateFavourite(favourite)

    suspend fun deleteAllFavourites() = weatherDao.deleteAllFavourites()

    suspend fun deleteFavourite(favourite: Favourite) = weatherDao.deleteFavourite(favourite)

    suspend fun getFavById(city: String): Favourite = weatherDao.getFavById(city)

    //Unit DB

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()

    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)

    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)

    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)

    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
}