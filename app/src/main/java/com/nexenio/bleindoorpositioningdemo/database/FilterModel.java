package com.nexenio.bleindoorpositioningdemo.database;

public class FilterModel {
    public Integer id;
    public String name;
    public String uuid;
    public String minor;
    public String major;

    public FilterModel(){}

    public FilterModel(String uuid, String minor, String major, String name) {
        this.uuid = uuid;
        this.minor = minor;
        this.major = major;
        this.name = name;
    }
}
