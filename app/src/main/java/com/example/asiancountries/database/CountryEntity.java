package com.example.asiancountries.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class CountryEntity {
    @PrimaryKey int countryId;
    @ColumnInfo(name="countryName") String countryName;
    @ColumnInfo(name="capital") public String capital;
    @ColumnInfo(name="flag") public String flag;
    @ColumnInfo(name="region") public String region;
    @ColumnInfo(name="subregion") public String subregion;
    @ColumnInfo(name="population") public int population;
    @ColumnInfo(name="borders") public String borders;
    @ColumnInfo(name="languages") public String languages;

    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public int getPopulation() {
        return population;
    }

    public String getBorders() {
        return borders;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getLanguages() {
        return languages;
    }

    public CountryEntity(int countryId, String countryName, String capital, String flag, String region, String subregion, int population, String borders, String languages) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
    }
}
