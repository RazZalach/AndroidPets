package com.example.project2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DbFarm extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "farmManager";

    private static final String TABLE_EMPLOYEES = "employees";
    private static final String TABLE_FARMS = "farms";
    private static final String TABLE_HORSES = "horses";
    private static final String TABLE_EMPLOYEE_FARMS = "employee_farms";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_JOB = "job";
    private static final String KEY_PHONE_NUM = "phone_num";

    private static final String KEY_FARM_ID = "farm_id";
    private static final String KEY_FARM_NAME = "farm_name";
    private static final String KEY_LIMIT_HORSES_NUM = "limit_horses_num";

    private static final String KEY_HORSE_ID = "horse_id";
    private static final String KEY_HORSE_NAME = "horse_name";
    private static final String KEY_HORSE_RACE = "horse_race";
    private static final String KEY_HORSE_AGE = "horse_age";
    private static final String KEY_HORSE_IS_HUNGRY = "horse_is_hungry";

    private static final String KEY_EMPLOYEE_ID = "employee_id";

    public DbFarm(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_EMPLOYEES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_JOB + " TEXT,"
                + KEY_PHONE_NUM + " TEXT" + ")";

        String CREATE_FARMS_TABLE = "CREATE TABLE " + TABLE_FARMS + "("
                + KEY_FARM_ID + " INTEGER PRIMARY KEY,"
                + KEY_FARM_NAME + " TEXT,"
                + KEY_LIMIT_HORSES_NUM + " INTEGER" + ")";

        String CREATE_HORSES_TABLE = "CREATE TABLE " + TABLE_HORSES + "("
                + KEY_HORSE_ID + " INTEGER PRIMARY KEY,"
                + KEY_FARM_ID + " INTEGER,"
                + KEY_HORSE_NAME + " TEXT,"
                + KEY_HORSE_RACE + " TEXT,"
                + KEY_HORSE_AGE + " REAL,"
                + KEY_HORSE_IS_HUNGRY + " INTEGER,"
                + "FOREIGN KEY(" + KEY_FARM_ID + ") REFERENCES " + TABLE_FARMS + "(" + KEY_FARM_ID + "))";

        String CREATE_EMPLOYEE_FARMS_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE_FARMS + "("
                + KEY_EMPLOYEE_ID + " INTEGER,"
                + KEY_FARM_ID + " INTEGER,"
                + "PRIMARY KEY (" + KEY_EMPLOYEE_ID + ", " + KEY_FARM_ID + "),"
                + "FOREIGN KEY(" + KEY_EMPLOYEE_ID + ") REFERENCES " + TABLE_EMPLOYEES + "(" + KEY_ID + "),"
                + "FOREIGN KEY(" + KEY_FARM_ID + ") REFERENCES " + TABLE_FARMS + "(" + KEY_FARM_ID + "))";

        db.execSQL(CREATE_EMPLOYEES_TABLE);
        db.execSQL(CREATE_FARMS_TABLE);
        db.execSQL(CREATE_HORSES_TABLE);
        db.execSQL(CREATE_EMPLOYEE_FARMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HORSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_FARMS);
        onCreate(db);
    }

    public long createFarm(Farm farm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FARM_NAME, farm.getNameFarm());
        values.put(KEY_LIMIT_HORSES_NUM, farm.getLimitHorsesNum());

        long farmId = db.insert(TABLE_FARMS, null, values);

        for (Horse horse : farm.getHorsesList()) {
            ContentValues horseValues = new ContentValues();
            horseValues.put(KEY_FARM_ID, farmId);
            horseValues.put(KEY_HORSE_NAME, horse.getHorseName());
            horseValues.put(KEY_HORSE_RACE, horse.getRace());
            horseValues.put(KEY_HORSE_AGE, horse.getHorseAge());
            horseValues.put(KEY_HORSE_IS_HUNGRY, horse.isHungry() ? 1 : 0);
            db.insert(TABLE_HORSES, null, horseValues);
        }

        return farmId;
    }

    public void addFarmForEmployee(long employeeId, long farmId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_ID, employeeId);
        values.put(KEY_FARM_ID, farmId);
        db.insert(TABLE_EMPLOYEE_FARMS, null, values);
    }

    public List<Farm> getFarmsForEmployee(long employeeId) {
        List<Farm> farms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT f." + KEY_FARM_ID + ", f." + KEY_FARM_NAME + ", f." + KEY_LIMIT_HORSES_NUM + " FROM "
                + TABLE_FARMS + " f, " + TABLE_EMPLOYEE_FARMS + " ef WHERE ef." + KEY_EMPLOYEE_ID + " = ? AND ef."
                + KEY_FARM_ID + " = f." + KEY_FARM_ID;
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(employeeId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long farmId = cursor.getLong(cursor.getColumnIndex(KEY_FARM_ID));
                @SuppressLint("Range") String farmName = cursor.getString(cursor.getColumnIndex(KEY_FARM_NAME));
                @SuppressLint("Range") int limitHorsesNum = cursor.getInt(cursor.getColumnIndex(KEY_LIMIT_HORSES_NUM));

                Farm farm = new Farm(farmId, farmName, limitHorsesNum);
                farm.setHorsesList(getHorsesForFarm(farmId));

                farms.add(farm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return farms;
    }

    public List<Horse> getHorsesForFarm(long farmId) {
        List<Horse> horses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_HORSES + " WHERE " + KEY_FARM_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(farmId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long horseId = cursor.getLong(cursor.getColumnIndex(KEY_HORSE_ID));
                @SuppressLint("Range") String horseName = cursor.getString(cursor.getColumnIndex(KEY_HORSE_NAME));
                @SuppressLint("Range") String horseRace = cursor.getString(cursor.getColumnIndex(KEY_HORSE_RACE));
                @SuppressLint("Range") double horseAge = cursor.getDouble(cursor.getColumnIndex(KEY_HORSE_AGE));
                @SuppressLint("Range") boolean isHungry = cursor.getInt(cursor.getColumnIndex(KEY_HORSE_IS_HUNGRY)) == 1;

                Horse horse = new Horse(horseId, horseRace, horseName, horseAge, isHungry);
                horses.add(horse);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return horses;
    }

    public Employee loginEmployee(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEES + " WHERE " + KEY_EMAIL + " = ? AND " + KEY_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(KEY_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            @SuppressLint("Range") String emailEmp = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
            @SuppressLint("Range") String job = cursor.getString(cursor.getColumnIndex(KEY_JOB));
            @SuppressLint("Range") String phoneNum = cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUM));

            cursor.close();

            return new Employee(id, name, password, emailEmp, job, phoneNum);
        } else {
            cursor.close();
            return null;
        }
    }

    public long registerEmployee(String name, String password, String email, String job, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if email already exists
        String query = "SELECT * FROM " + TABLE_EMPLOYEES + " WHERE " + KEY_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            cursor.close();
            return -1; // Email already exists
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_EMAIL, email);
        values.put(KEY_JOB, job);
        values.put(KEY_PHONE_NUM, phone);

        return db.insert(TABLE_EMPLOYEES, null, values);
    }
    public void setHorseNotHungry(long farmId, int position) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get the list of horses for the farm
        List<Horse> horsesList = getHorsesForFarm(farmId);

        // Check if the position is valid
        if (position < 0 || position >= horsesList.size()) {
            throw new IllegalArgumentException("Invalid position");
        }

        // Get the horse at the specified position
        Horse horse = horsesList.get(position);

        // Update the horse's isHungry attribute
        horse.setHungry(false);

        // Update the horse's isHungry attribute in the database
        ContentValues values = new ContentValues();
        values.put(KEY_HORSE_IS_HUNGRY, 0); // Set isHungry to false (0)
        db.update(TABLE_HORSES, values, KEY_HORSE_ID + " = ?", new String[]{String.valueOf(horse.getId())});
    }


    public void setRandomHungerForHorses(long farmId) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Horse> horsesList = getHorsesForFarm(farmId);
        Random random = new Random();

        for (Horse horse : horsesList) {
            boolean isHungry = random.nextBoolean();
            horse.setHungry(isHungry);

            ContentValues values = new ContentValues();
            values.put(KEY_HORSE_IS_HUNGRY, isHungry ? 1 : 0);
            db.update(TABLE_HORSES, values, KEY_HORSE_ID + " = ?", new String[]{String.valueOf(horse.getId())});
        }
    }




}
