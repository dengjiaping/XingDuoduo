package com.xiuman.xingduoduo.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
 * 名称：SearchRecentlyDBHelper.java
 * 描述：最近搜索的关键字数据库
 * @author danding
 * @version
 * 2014-6-12
 */
public class SearchRecentlyDBHelper extends SQLiteOpenHelper {


	
	public SearchRecentlyDBHelper(Context context){
		super(context, "search_keywords_recently.db", null, 1);
	}
	public SearchRecentlyDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, "search_keywords_recently.db", null, 1);
	}

	/**
	 * 表名 keywords
	 * 字段名 keyword
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table keywords (_id integer primary key autoincrement,keyword varchar(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
