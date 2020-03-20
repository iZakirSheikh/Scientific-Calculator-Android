package com.prime.calculator.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prime.calculator.UnitConverter.Conversions;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2; /*Increase Version from 1 to 2 Because Database
                                                         Table Name Changed*/
    // Database Name
    private static final String DATABASE_NAME = "Calculator";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create History Table
        db.execSQL(History.CREATE_TABLE_STANDARD);
        db.execSQL(History.CREATE_TABLE_SCIENTIFIC);
        db.execSQL(History.CREATE_TABLE_CURRENCY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + History.TABLE_STANDARD);
        db.execSQL("DROP TABLE IF EXISTS " + History.TABLE_SCIENTIFIC);
        db.execSQL("DROP TABLE IF EXISTS " + History.TABLE_CURRENCY);
        // Create tables again
        onCreate(db);
    }

    public void insertData(String tableName, String expression, String result) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(History.COLUMN_EXPRESSION, expression);
        values.put(History.COLUMN_RESULT, result);

        db.insert(tableName, null, values);

        // close db connection
        db.close();
    }

    public void updateCurrencyData(String[] name, double[] rate, String[] tag) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        //on Every update delete contents of old table
        db.execSQL("DELETE FROM " + History.TABLE_CURRENCY);
        db.execSQL("VACUUM");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + History.TABLE_CURRENCY + "'");

        ContentValues values = new ContentValues();

        for (int i = 0; i < name.length; i++) {
            values.put(History.COLUMN_COUNTRY, name[i]);
            values.put(History.COLUMN_RATE, rate[i]);
            values.put(History.COLUMN_TAG, tag[i]);
            db.insert(History.TABLE_CURRENCY, null, values);
        }
        // close db connection
        db.close();
    }

    public String[] getCurrencyNames() {
        String[] names = new String[Conversions.CURRENCY_COUNT];
        int i = 0;
        String selectQuery = "SELECT " + History.COLUMN_COUNTRY + " FROM " + History.TABLE_CURRENCY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
            do {
                names[i] = cursor.getString(cursor.getColumnIndex(History.COLUMN_COUNTRY));
                i++;
            } while (cursor.moveToNext());
        db.close();
        cursor.close();
        return names;
    }


    public String[] getCurrencyTags(){
        String[] tags = new String[Conversions.CURRENCY_COUNT];
        int i = 0;
        String selectQuery = "SELECT " + History.COLUMN_TAG + " FROM " + History.TABLE_CURRENCY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
            do {
                tags[i] = cursor.getString(cursor.getColumnIndex(History.COLUMN_TAG));
                i++;
            } while (cursor.moveToNext());
        cursor.close();
        return tags;
    }

    public double[] getCurrencyRates() {
        double[] rates = new double[Conversions.CURRENCY_COUNT];
        int i = 0;
        String selectQuery = "SELECT " + History.COLUMN_RATE + " FROM " + History.TABLE_CURRENCY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
            do {
                rates[i] = cursor.getDouble(cursor.getColumnIndex(History.COLUMN_RATE));
                i++;
            } while (cursor.moveToNext());
        cursor.close();
        return rates;

    }

    public List<History> getAllHistory(String tableName) {
        List<History> histories = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + tableName + " ORDER BY " +
                History.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setId(cursor.getInt(cursor.getColumnIndex(History.COLUMN_ID)));
                history.setExpression(cursor.getString(cursor.getColumnIndex(History.COLUMN_EXPRESSION)));
                history.setResult(cursor.getString(cursor.getColumnIndex(History.COLUMN_RESULT)));
                history.setTimestamp(cursor.getString(cursor.getColumnIndex(History.COLUMN_TIMESTAMP)));
                histories.add(history);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        // return notes list
        return histories;
    }

    public int getHistoryCount(String tableName) {
        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void deleteHistory(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*db.delete(History.TABLE_STANDARD, null, null);*/
        db.execSQL("DELETE FROM " + tableName);
        db.execSQL("VACUUM");
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + tableName + "'");
        db.close();
    }
}