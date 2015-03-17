package com.weiqun.newcool.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NewCoolOpenHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "newcool.db";
	private static int DB_VERSION = 1;
	private static final String TABLE_NAME_CITY = "city";

	public NewCoolOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public NewCoolOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 城市列表
		db.execSQL("create table " + TABLE_NAME_CITY + " ("
				+ " id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " city_id INTEGER," + " province char(10),"
				+ " city char(20)," + " district char(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
