package com.nennashi.sql;

import com.nennashi.action.Common;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 4;
	private int m_writableDatabaseCount = 0;

	private static DBOpenHelper m_instance = null;

	synchronized static public DBOpenHelper getInstance(Context context) {
		if (m_instance == null) {
			m_instance = new DBOpenHelper(context.getApplicationContext());
		}

		return m_instance;
	}

	public DBOpenHelper(Context context) {
		super(context, Common.DB_NAME, null, DB_VERSION);
	}

	@Override
	synchronized public SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase db = super.getWritableDatabase();
		if (db != null) {
			++m_writableDatabaseCount;
		}

		return db;
	}

	synchronized public void closeWritableDatabase(SQLiteDatabase database) {
		if (m_writableDatabaseCount > 0 && database != null) {
			--m_writableDatabaseCount;
			if (m_writableDatabaseCount == 0) {
				database.close();
			}
		}
	}

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {
		sqlitedatabase
				.execSQL("CREATE TABLE IF NOT EXISTS Nennashi ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , catchDate TEXT,catchPlace TEXT,catchSize TEXT,photoPath "
						+ "TEXT,rod TEXT,reel TEXT,floatStr TEXT,floatInt INTEGER,"
						+ "line TEXT,fookline TEXT,fook INTEGER,"
						+ "komase1 TEXT,komase2 TEXT,okiami TEXT,esa TEXT,memo TEXT,catchTime TEXT  )");
		sqlitedatabase
		.execSQL("CREATE TABLE IF NOT EXISTS TackleBox ( _id INTEGER NOT NULL , _gyo INTEGER NOT NULL,"
				+ "_rowid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , _name TEXT,_favorite INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion,
			int newVersion) {
		if (oldVersion == 1) {
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD rod TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD reel TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD floatStr TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD floatInt INTEGER");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD line TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD fookline TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD fook INTEGER");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD komase1 TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD komase2 TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD okiami TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD esa TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD memo TEXT");
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD catchTime TEXT");
		} else if (oldVersion == 2) {
			sqlitedatabase.execSQL("ALTER TABLE Nennashi ADD catchTime TEXT");
			Cursor c = sqlitedatabase.query("Nennashi", new String[] { "_id",
					"catchDate" }, null, null, null, null, null);

			int numRows = c.getCount();

			c.moveToFirst();

			for (int i = 0; i < numRows; i++) {
				ContentValues val = new ContentValues();
				String[] splt = c.getString(1).split(" ")[0].split("/");
				if (splt.length != 3) {
					continue;
				}
				String newDate = splt[0];
				if (splt[1].length() == 1) {
					newDate += "/0" + splt[1];
				} else {
					newDate += "/" + splt[1];
				}
				if (splt[2].length() == 1) {
					newDate += "/0" + splt[2];
				} else {
					newDate += "/" + splt[2];
				}

				String[] splt2 = c.getString(1).split(" ")[1].split(":");
				String newTime = "";
				if (splt2[0].length() == 1) {
					newTime += "0" + splt2[0];
				} else {
					newTime += splt2[0];
				}
				if (splt2[1].length() == 1) {
					newTime += ":0" + splt2[1];
				} else {
					newTime += ":" + splt2[1];
				}

				val.put("catchDate", newDate);
				val.put("catchTime", newTime);
				sqlitedatabase.update("Nennashi", val, "_id=?",
						new String[] { c.getString(0) });

				c.moveToNext();
			}
			c.close();

		} else if (oldVersion == 3) {
			sqlitedatabase
			.execSQL("CREATE TABLE IF NOT EXISTS TackleBox ( _id INTEGER NOT NULL , _gyo INTEGER NOT NULL,"
					+ "_rowid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , _name TEXT,_favorite INTEGER)");

			Cursor c = sqlitedatabase.query("Nennashi", new String[] { "_id",
					"rod" ,"reel","floatStr","line","fookline","komase1","komase2"}, null, null, null, null, null);

			int numRows = c.getCount();

			c.moveToFirst();

			int cnt = 1;

			int rodcnt = 1;
			int reelcnt = 1;
			int floatcnt = 1;
			int linecnt = 1;
			int fookcnt = 1;
			int komasecnt = 1;

			for (int i = 0; i < numRows; i++) {

				ContentValues val = new ContentValues();
				ContentValues val2 = new ContentValues();
				if (!(c.getString(1).equals(""))){
					val.put("_id", Common.TACKLE_ID_ROD);
					val.put("_gyo", rodcnt);
					rodcnt++;
					val.put("_name", c.getString(1));
					val.put("_favorite", 0);
					sqlitedatabase.insert("TackleBox", null, val);
					val2.put("rod", cnt);
					cnt++;
					sqlitedatabase.update("Nennashi", val2, "_id=?", new String[] { c.getString(0) });
				}

				if (!(c.getString(2).equals(""))){
					val = new ContentValues();
					val.put("_id", Common.TACKLE_ID_REEL);
					val.put("_gyo", reelcnt);
					reelcnt++;
					val.put("_name", c.getString(2));
					val.put("_favorite", 0);
					sqlitedatabase.insert("TackleBox", null, val);
					val2 = new ContentValues();
					val2.put("rod", cnt);
					cnt++;
					sqlitedatabase.update("Nennashi", val2, "_id=?", new String[] { c.getString(0) });
				}

				if (!(c.getString(3).equals(""))){
					val = new ContentValues();
					val.put("_id", Common.TACKLE_ID_FLOAT);
					val.put("_gyo", floatcnt);
					floatcnt++;
					val.put("_name", c.getString(3));
					val.put("_favorite", 0);
					sqlitedatabase.insert("TackleBox", null, val);
					val2 = new ContentValues();
					val2.put("rod", cnt);
					cnt++;
					sqlitedatabase.update("Nennashi", val2, "_id=?", new String[] { c.getString(0) });
				}

				if (!(c.getString(4).equals(""))){
					val = new ContentValues();
					val.put("_id", Common.TACKLE_ID_LINE);
					val.put("_gyo", linecnt);
					linecnt++;
					val.put("_name", c.getString(4));
					val.put("_favorite", 0);
					sqlitedatabase.insert("TackleBox", null, val);
					val2 = new ContentValues();
					val2.put("rod", cnt);
					cnt++;
					sqlitedatabase.update("Nennashi", val2, "_id=?", new String[] { c.getString(0) });
				}

				if (!(c.getString(5).equals(""))){
					val = new ContentValues();
					val.put("_id", Common.TACKLE_ID_FOOKLINE);
					val.put("_gyo", fookcnt);
					fookcnt++;
					val.put("_name", c.getString(5));
					val.put("_favorite", 0);
					sqlitedatabase.insert("TackleBox", null, val);
					val2 = new ContentValues();
					val2.put("rod", cnt);
					cnt++;
					sqlitedatabase.update("Nennashi", val2, "_id=?", new String[] { c.getString(0) });
				}

				if (!(c.getString(6).equals(""))){
					val = new ContentValues();
					val.put("_id", Common.TACKLE_ID_KOMASE);
					val.put("_gyo", komasecnt);
					komasecnt++;
					val.put("_name", c.getString(6));
					val.put("_favorite", 0);
					sqlitedatabase.insert("TackleBox", null, val);
					val2 = new ContentValues();
					val2.put("rod", cnt);
					cnt++;
					sqlitedatabase.update("Nennashi", val2, "_id=?", new String[] { c.getString(0) });
					c.moveToNext();
				}

				if (!(c.getString(7).equals(""))){
					val = new ContentValues();
					val.put("_id", Common.TACKLE_ID_KOMASE);
					val.put("_gyo", komasecnt);
					komasecnt++;
					val.put("_name", c.getString(7));
					val.put("_favorite", 0);
					sqlitedatabase.insert("TackleBox", null, val);
					val2 = new ContentValues();
					val2.put("rod", cnt);
					cnt++;
					sqlitedatabase.update("Nennashi", val2, "_id=?", new String[] { c.getString(0) });
				}

				c.moveToNext();

			}
			c.close();

		}
	}
}
