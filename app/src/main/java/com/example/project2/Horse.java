package com.example.project2;

public class Horse {
    private long id;
    private String race;
    private String horseName;
    private double horseAge;
    private boolean isHungry;

    public Horse(long id, String race, String horseName, double horseAge, boolean isHungry) {
        this.id = id;
        this.race = race;
        this.horseName = horseName;
        this.horseAge = horseAge;
        this.isHungry = isHungry;
    }

    public Horse(String race, String horseName, double horseAge, boolean isHungry) {
        this(-1, race, horseName, horseAge, isHungry);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public double getHorseAge() {
        return horseAge;
    }

    public void setHorseAge(double horseAge) {
        this.horseAge = horseAge;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }
}
