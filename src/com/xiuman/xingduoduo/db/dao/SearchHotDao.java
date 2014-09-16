package com.xiuman.xingduoduo.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiuman.xingduoduo.db.helper.SearchHotDBHelper;

/**
 * 
 * 名称：SearchRecentlyDao.java 描述：对热门搜索数据库数据进行操作
 * 
 * @author danding
 * @version 2014-6-12
 */
public class SearchHotDao {

	private Context context;
	private SearchHotDBHelper dbHelper;

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param dbHelper
	 */
	public SearchHotDao(Context context) {
		super();
		this.context = context;
		this.dbHelper = new SearchHotDBHelper(context);
	}

	/**
	 * 
	 * 描述：判断关键字是否存在于数据库中
	 * 
	 * @param keyword
	 * @return
	 */
	public boolean isExist(String keyword) {
		boolean result = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select keyword from keywords where keyword=?",
					new String[] { keyword });
			if (cursor.moveToFirst()) {
				result = true;
			}
			cursor.close();
			db.close();
		}
		return result;
	}

	/**
	 * 
	 * 描述：插入新的关键字
	 * 
	 * @param keyword
	 */
	public void insert(String keyword) {
		if (isExist(keyword)) {// 关键字已经存在于数据库中
			return;
		} else {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			if (db.isOpen()) {
				db.execSQL("insert into keywords (keyword) values (?)",
						new Object[] { keyword });

				db.close();
			}
		}
	}

	/**
	 * 
	 * 描述：删除关键字
	 * 
	 * @param keyword
	 */
	public void delete(String keyword) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (db.isOpen()) {
			db.execSQL("delete from keywords where keyword=(?)",
					new Object[] { keyword });

			db.close();
		}
	}
	/**
	 * 
	 * 描述：获取数据库中的所有数据
	 * @return
	 */
	public List<String> getKeywords(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<String> keywords = new ArrayList<String>();
		
		if(db.isOpen()){
			Cursor cursor = db.rawQuery("select keyword from keywords", null);
			
			while(cursor.moveToNext()){
				String keyword = cursor.getString(cursor.getColumnIndex("keyword"));
				keywords.add(keyword);
			}
			cursor.close();
			db.close();
		}
		return keywords;
	}
}
