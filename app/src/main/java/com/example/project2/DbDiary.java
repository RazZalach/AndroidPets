package com.example.project2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class DbDiary  extends SQLiteOpenHelper {


    //// name and version of db
    private static final String DATABASE_NAME = "Diarydb";
    private static final int DATABASE_VERSION = 2;


    /// diary page table

    private static final String TABLE_DIARY_PAGES = "diary_pages";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_BODY = "body";
    private static final String COLUMN_DATE_CREATED = "date_created";
    private static final String COLUMN_PIC_URL = "pic_url";


//    private static final String COLUMN_VOICE_RECORD = "voice_record";


    /// users table
    private static final String TABLE_USER = "users";
    private static final String COLUMN_ID_USER = "id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";



    public DbDiary(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DIARY_PAGES_TABLE = "CREATE TABLE " + TABLE_DIARY_PAGES +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_BODY + " TEXT," +
                COLUMN_DATE_CREATED + " INTEGER," +  // Storing date as UNIX timestamp
                COLUMN_PIC_URL + " TEXT " +")";
        db.execSQL(CREATE_DIARY_PAGES_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_USER_NAME + " TEXT" +
                ")";
        db.execSQL(CREATE_USER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY_PAGES);
        // Create tables again
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }



    @SuppressLint("SimpleDateFormat")
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if user with the same email already exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=?", new String[]{user.getEmail()});
        if ( cursor!= null &&  cursor.getCount() > 0) {
            // User with the same email already exists
            cursor.close();
            db.close();
            return false;
        }

        // User doesn't exist, so we can add it
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_NAME, user.getUserName());
        db.insert(TABLE_USER, null, values);

//        cursor.close();
        db.close(); // Closing database connection
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    public boolean loginUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{user.getEmail(), user.getPassword()});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }




    @SuppressLint("SimpleDateFormat")
    public void addDiaryPage(DiaryPage diaryPage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, diaryPage.getName());
        values.put(COLUMN_BODY, diaryPage.getBody());
        values.put(COLUMN_DATE_CREATED, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(diaryPage.getDateCreated()));
        values.put(COLUMN_PIC_URL, diaryPage.getPicUrl());

        db.insert(TABLE_DIARY_PAGES, null, values);
        db.close(); // Closing database connection
    }

    @SuppressLint("SimpleDateFormat")
    public List<DiaryPage> readAllDiaryPages() {
        List<DiaryPage> diaryPages = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DIARY_PAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Check if cursor is not null and has rows
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String body = cursor.getString(2);
                String dateCreatedString = cursor.getString(3);
                Date dateCreated = null;
                try {
                    dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateCreatedString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String picurl = cursor.getString(4);



                // Create DiaryPage object and add to list
                DiaryPage diaryPage = new DiaryPage(name, body, picurl);
                diaryPage.setId(id);

                diaryPages.add(diaryPage);
            } while (cursor.moveToNext());
        }

        // Closing resources
        if (cursor != null) {
            cursor.close();
        }


        return diaryPages;
    }
    public boolean deleteDiaryPageById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_DIARY_PAGES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0; // Returns true if a row was deleted
    }

    public DiaryPage getDiaryPageById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DIARY_PAGES,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String body = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BODY));
                String dateCreatedString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_CREATED));
                Date dateCreated = null;
                try {
                    dateCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateCreatedString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String picurl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PIC_URL));

                DiaryPage diaryPage = new DiaryPage(name, body, picurl);
                diaryPage.setId(id);
                diaryPage.setDateCreated(dateCreated);

                cursor.close();
                return diaryPage;
            }
            cursor.close();
        }

        return null; // Return null if no diary page is found with the given ID
    }

    public boolean updateDiaryById(int id, String newName, String newBody) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_BODY, newBody);

        int rowsAffected = db.update(TABLE_DIARY_PAGES, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();

        return rowsAffected > 0; // Returns true if one or more rows were updated
    }



}