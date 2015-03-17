package com.weiqun.newcool.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.weiqun.newcool.model.City;

public class NewCoolDB {
	private NewCoolOpenHelper newCoolOpenHelper;
	private static NewCoolDB newCoolDB;
	private SQLiteDatabase db;
	private static final String TABLE_NAME_CITY = "city";

	private NewCoolDB(Context context) {
		newCoolOpenHelper = new NewCoolOpenHelper(context);
		db = newCoolOpenHelper.getWritableDatabase();
	}

	public synchronized static NewCoolDB getInstance(Context context) {
		if (newCoolDB == null) {
			newCoolDB = new NewCoolDB(context);
		}
		return newCoolDB;
	}
	
	public void saveCitys(ArrayList<City> cityList) {
		ContentValues values = new ContentValues();
		for(City city : cityList) {
			values.clear();
			values.put("city_id", city.getId());
			values.put("city", city.getCity());
			values.put("province", city.getProvince());
			values.put("district", city.getDistrict());
			db.insert(TABLE_NAME_CITY, null, values);
		}
	}
	
	public ArrayList<String> getCitys() {
		ArrayList<String> cityNames = new ArrayList<String>();
		Cursor cursor = db.rawQuery("select city from city group by (city) order by city_id;", null);
		while(cursor.moveToNext()) {
			cityNames.add(cursor.getString(0));
			
		}
		Log.i("weiqun12345", "cityNames.size=" + cityNames.size());
		return cityNames;
	}
}
