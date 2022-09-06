package com.example.weatherapp.data

import androidx.room.*
import com.example.weatherapp.model.Favourite
import com.example.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    //CRUD
    //Favourite Table
    @Query(value = "SELECT * from fav_tbl")
    fun getFavorites(): Flow<List<Favourite>>

    @Query(value = "SELECT * from fav_tbl where city =:city")
    suspend fun getFavById(city: String): Favourite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favourite: Favourite)

    @Query(value = "DELETE from fav_tbl")
    suspend fun deleteAllFavourites()

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    // Unit Table
    @Query(value = "SELECT * from settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Delete
    suspend fun deleteUnit(unit: Unit)

    @Query(value = "DELETE from settings_tbl")
    suspend fun deleteAllUnits()
}