package com.example.project2;

import java.util.ArrayList;
import java.util.List;

public class Farm {

    private long id;
    private String nameFarm;
    private List<Horse> horsesList;
    private int limitHorsesNum;

    public Farm(long id, String nameFarm, int limitHorsesNum) {
        this.id = id;
        this.nameFarm = nameFarm;
        this.horsesList = new ArrayList<>();
        this.limitHorsesNum = limitHorsesNum;
    }

    public Farm(String nameFarm, int limitHorsesNum) {
        this(-1, nameFarm, limitHorsesNum);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameFarm() {
        return nameFarm;
    }

    public void setNameFarm(String nameFarm) {
        this.nameFarm = nameFarm;
    }

    public List<Horse> getHorsesList() {
        return horsesList;
    }

    public void setHorsesList(List<Horse> horsesList) {
        this.horsesList = horsesList;
    }

    public int getLimitHorsesNum() {
        return limitHorsesNum;
    }

    public void setLimitHorsesNum(int limitHorsesNum) {
        this.limitHorsesNum = limitHorsesNum;
    }
}
