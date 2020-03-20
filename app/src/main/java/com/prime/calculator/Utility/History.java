package com.prime.calculator.Utility;

import com.prime.calculator.ScientificActivity;
import com.prime.calculator.StandardActivity;

/**
 * This Class Serves as a Blueprint for History Object, it Holds Various Values and
 * provides different methods to Manipulate them.
 */

public class History {

    /*Note: Every Activity That makes a Table in Database, can make with his own TAG*/
    public static final String TABLE_STANDARD = StandardActivity.TAG;
    public static final String TABLE_SCIENTIFIC = ScientificActivity.TAG;
    static final String COLUMN_ID = "Id";
    static final String COLUMN_EXPRESSION = "Expression";
     static final String COLUMN_RESULT = "Result";
     static final String COLUMN_TIMESTAMP = "Timestamp";
     static final String TABLE_CURRENCY = "Currency";
     static final String COLUMN_COUNTRY = "Country";
     static final String COLUMN_RATE = "Rate";
     static final String COLUMN_TAG = "Tag";




    private int Id;
    private String Expression;
    private String Result;
    private String Timestamp;

    // Create table SQL query
     static final String CREATE_TABLE_STANDARD =
            "CREATE TABLE " + TABLE_STANDARD + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, "
                    + COLUMN_EXPRESSION + " TEXT, "
                    + COLUMN_RESULT + " TEXT, "
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

     static final String CREATE_TABLE_SCIENTIFIC =
            "CREATE TABLE " + TABLE_SCIENTIFIC + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, "
                    + COLUMN_EXPRESSION + " TEXT, "
                    + COLUMN_RESULT + " TEXT, "
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    // Create table Currency query
     static final String CREATE_TABLE_CURRENCY =
            "CREATE TABLE " + TABLE_CURRENCY + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, "
                    + COLUMN_COUNTRY + " TEXT, "
                    + COLUMN_TAG + " TEXT, "
                    + COLUMN_RATE + " REAL"
                    + ")";

     History() {
    }

     History(int id, String expression, String result, String timestamp) {
        this.Id = id;
        this.Expression = expression;
        this.Result = result;
        this.Timestamp = timestamp;
    }

     int getId() {
        return Id;
    }

    public String getExpression(){
        return Expression;
    }

    public String getResult(){
        return Result;
    }

    void setExpression(String expression) {
        this.Expression = expression;
    }

    public void setResult(String result) {
        this.Result = result;
    }

    String getTimestamp() {
        return Timestamp;
    }

    public void setId(int id) {
        this.Id = id;
    }

    void setTimestamp(String timestamp) {
        this.Timestamp = timestamp;
    }
}
