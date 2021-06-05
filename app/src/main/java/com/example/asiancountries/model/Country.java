package com.example.asiancountries.model;

public class Country {
    public String name;
    public String capital;
    public String flag;
    public String region;
    public String subregion;
    public int population;
    public String borders;
    public String languages;

    public Country(String name, String capital, String flag, String region, String subregion, int population, String borders, String languages) {
        this.name = name;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setBorders(String borders) {
        this.borders = borders;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getCapital() {
        return capital;
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

    public String getLanguages() {
        return languages;
    }
}
