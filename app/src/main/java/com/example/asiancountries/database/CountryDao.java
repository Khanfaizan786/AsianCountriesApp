package com.example.asiancountries.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert
    void insertCountry(CountryEntity countryEntity);

    @Query("DELETE FROM countries")
    void deleteCountry();

    @Query("SELECT * FROM countries")
    List<CountryEntity> getAllRestaurants();
}
