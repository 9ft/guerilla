package com.mindy.gettjuseatinfo.dao;

import java.util.ArrayList;
import java.util.List;

import com.mindy.gettjuseatinfo.domain.TjuSeatInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TjuSeatDao {
	private static TjuSeatSQLiteOpenHelper helper;

	public TjuSeatDao(Context context) {
		helper = new TjuSeatSQLiteOpenHelper(context);
	}

	public static void add(String uid, String buildingNum, String weekNum, String className, String infos) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into seatInfo (buildingNum,weekNum,className,infos) values (?,?,?,?)", new Object[] { buildingNum, weekNum, className, infos });
		db.close();
	}

	public boolean find(String buildingNum, String weekNum) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from seatInfo where buildingNum=?", new String[] { buildingNum });
		boolean result = cursor.moveToNext();
		cursor.close();
		db.close();
		return result;
	}

	public List<TjuSeatInfo> findByBuildAndWeek(String buildingNum, String weekNum) {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<TjuSeatInfo> seatInfos = new ArrayList<TjuSeatInfo>();
		Cursor cursor = db.rawQuery("select id,buildingNum,weekNum,className,infos from seatInfo where buildingNum=?", new String[] { buildingNum });
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String buildingNumTmp = cursor.getString(cursor.getColumnIndex("buildingNum"));
			String weekNumTmp = cursor.getString(cursor.getColumnIndex("weekNum"));
			String classNameTmp = cursor.getString(cursor.getColumnIndex("className"));
			String infosTmp = cursor.getString(cursor.getColumnIndex("infos"));

			TjuSeatInfo info = new TjuSeatInfo(id, buildingNumTmp, weekNumTmp, classNameTmp, infosTmp);
			seatInfos.add(info);
		}
		cursor.close();
		db.close();
		return seatInfos;

	}
}
