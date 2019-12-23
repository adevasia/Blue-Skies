
package com.example.a6_hw3;


// class inspired by band app from zybooks chapter 5 on fragments
public class City {

    private int Id; //
    private String CityName;
    private String temperature;
    private String summary;

    // default constructor with no parameters
    public City() {}

    // constructor that takes in ID, CityName, temperature and Summary Information
    public City(int tempId, String name, String tempIn, String SumIn ) {
        Id = tempId;
        CityName = name;
        temperature = tempIn;
        summary = SumIn;
    }

    // returns ID of city object
    public int getId() {
        return Id;
    }

    // sets ID for city Object if needed
    public void setId(int id) {
        this.Id = id;
    }

    // gets Name of City Object
    public String getName() {
        return CityName;
    }

    // gets temperature of city object
    public String getTemperature() {
        return temperature;
    }

    // get summary of city Object
    public String getSummary() {return summary;}

}

