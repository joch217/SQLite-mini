package com.example.sqlitemini;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EmailTable {
	
	 // Database table
	  public static final String TABLE_EMAIL = "email";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_SUBJECT = "subject";
	  public static final String COLUMN_SENDER = "sender";
	  public static final String COLUMN_PREVIEW = "preview";
	  public static final String COLUMN_TIME = "time";


	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "create table " 
	      + TABLE_EMAIL
	      + "(" 
	      + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_SUBJECT + " text not null, " 
	      + COLUMN_SENDER + " text not null," 
	      + COLUMN_PREVIEW + " text not null,"
	      + COLUMN_TIME 
	      + " text not null" 
	      + ");";

	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    Log.w(EmailTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_EMAIL);
	    onCreate(database);
	  }


}
