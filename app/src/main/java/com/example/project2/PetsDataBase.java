package com.example.project2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PetsDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "petsDatabase.db";

    // User table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Pets table
    private static final String TABLE_PETS = "pets";
    private static final String COLUMN_PET_ID = "id";
    private static final String COLUMN_PET_TYPE = "type";
    private static final String COLUMN_PET_NAME = "name";
    private static final String COLUMN_PET_AGE = "age";
    private static final String COLUMN_PET_COLOR = "color";
    private static final String COLUMN_PET_PICURL = "picUrl";
    private static final String COLUMN_PET_CONTACTPHONE = "contactPhone";
    private static final String COLUMN_PET_ISADOPTED = "isAdopted";
    private static final String COLUMN_USER_ID_FK = "user_id";

    public PetsDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_EMAIL + " TEXT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Pets table
        String CREATE_PETS_TABLE = "CREATE TABLE " + TABLE_PETS + "("
                + COLUMN_PET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PET_TYPE + " TEXT,"
                + COLUMN_PET_NAME + " TEXT,"
                + COLUMN_PET_AGE + " REAL,"
                + COLUMN_PET_COLOR + " TEXT,"
                + COLUMN_PET_PICURL + " TEXT,"
                + COLUMN_PET_CONTACTPHONE + " TEXT,"
                + COLUMN_PET_ISADOPTED + " INTEGER,"
                + COLUMN_USER_ID_FK + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";
        db.execSQL(CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        onCreate(db);
    }

    // User Registration
    public void registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }
    // Get User by ID
    @SuppressLint("Range")
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD},
                COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                user = new User(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
            }
            cursor.close();
        }

        db.close();
        return user;
    }


    // User Login
    public User loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD},
                COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?", new String[]{email, password},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                User user = new User(cursor.getString(1), cursor.getInt(0),
                        cursor.getString(2), cursor.getString(3));
                cursor.close();
                return user;
            }
        }

        return null;
    }

    // Save Pet for User
    public void savePet(Pets pet, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_TYPE, pet.getType());
        values.put(COLUMN_PET_NAME, pet.getName());
        values.put(COLUMN_PET_AGE, pet.getAge());
        values.put(COLUMN_PET_COLOR, pet.getColor());
        values.put(COLUMN_PET_PICURL, pet.getPicUrl());
        values.put(COLUMN_PET_CONTACTPHONE, pet.getContactPhone());
        values.put(COLUMN_PET_ISADOPTED, pet.isAdopted() ? 1 : 0);
        values.put(COLUMN_USER_ID_FK, userId);

        db.insert(TABLE_PETS, null, values);
        db.close();
    }
    // Delete Pet from User
    public void deletePet(int userId, String petName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause to specify which pet to delete
        String selection = COLUMN_USER_ID_FK + " = ? AND " + COLUMN_PET_NAME + " = ?";
        String[] selectionArgs = { String.valueOf(userId), petName };

        // Perform the deletion
        db.delete(TABLE_PETS, selection, selectionArgs);

        db.close();
    }


    @SuppressLint("Range")
    public List<Pets> getAllUserPets(int userId) {
        List<Pets> petsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PETS + " WHERE " + COLUMN_USER_ID_FK + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Pets pet = new Pets();
                pet.setType(cursor.getString(cursor.getColumnIndex(COLUMN_PET_TYPE)));
                pet.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PET_NAME)));
                pet.setAge(cursor.getDouble(cursor.getColumnIndex(COLUMN_PET_AGE)));
                pet.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_PET_COLOR)));
                pet.setPicUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PET_PICURL)));
                pet.setContactPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PET_CONTACTPHONE)));
                pet.setAdopted(cursor.getInt(cursor.getColumnIndex(COLUMN_PET_ISADOPTED)) == 1);
                petsList.add(pet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return petsList;
    }
    @SuppressLint("Range")
    public Pets getPetByUserIdAndName(int userId, String petName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Pets pet = null;

        String selectQuery = "SELECT * FROM " + TABLE_PETS +
                " WHERE " + COLUMN_USER_ID_FK + " = ? AND " + COLUMN_PET_NAME + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId), petName});

        if (cursor.moveToFirst()) {
            pet = new Pets();
            pet.setType(cursor.getString(cursor.getColumnIndex(COLUMN_PET_TYPE)));
            pet.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PET_NAME)));
            pet.setAge(cursor.getDouble(cursor.getColumnIndex(COLUMN_PET_AGE)));
            pet.setColor(cursor.getString(cursor.getColumnIndex(COLUMN_PET_COLOR)));
            pet.setPicUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PET_PICURL)));
            pet.setContactPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PET_CONTACTPHONE)));
            pet.setAdopted(cursor.getInt(cursor.getColumnIndex(COLUMN_PET_ISADOPTED)) == 1);
        }

        cursor.close();
        db.close();
        return pet;
    }



}
