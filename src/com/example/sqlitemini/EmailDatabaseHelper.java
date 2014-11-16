package com.example.sqlitemini;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmailDatabaseHelper extends SQLiteOpenHelper{
	
	  private static final String DATABASE_NAME = "emailtable.db";
	  private static final int DATABASE_VERSION = 1;

	  public EmailDatabaseHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  // Method is called during creation of the database
	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    EmailTable.onCreate(database);
	  }

	  // Method is called during an upgrade of the database,
	  // e.g. if you increase the database version
	  @Override
	  public void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    EmailTable.onUpgrade(database, oldVersion, newVersion);
	  }

	
}
