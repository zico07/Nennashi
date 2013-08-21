package com.nennashi.sql;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nennashi.action.Common;
import com.nennashi.data.ItemBean;
import com.nennashi.data.TackleItemBean;
import com.nennashi.parts.KeyValuePair;

public class DBStore {
	private DBOpenHelper m_helper;

	private SQLiteDatabase m_db;
	private static final String TBL_NAME = "Nennashi";
	private static final String TACKLE_TBL_NAME = "TackleBox";

	public DBStore(Context context) {
		m_helper = DBOpenHelper.getInstance(context);
		if (m_helper != null)
			m_db = m_helper.getWritableDatabase();
		else
			m_db = null;
	}

	public void close() {
		m_db.close();
	}

	public void add(String catchDate, String catchPlace, String catchSize,
			String photoPath, String rod, String reel, String floatStr,
			String floatInt, String line, String fookline, String fook,
			String komase1, String komase2, String okiami, String esa,
			String memo, String catchTime) {
		ContentValues val = new ContentValues();

		val.put("catchDate", catchDate);
		val.put("catchPlace", catchPlace);
		val.put("catchSize", catchSize);
		val.put("photoPath", photoPath);
		val.put("rod", rod);
		val.put("reel", reel);
		val.put("floatStr", floatStr);
		val.put("floatInt", floatInt);
		val.put("line", line);
		val.put("fookline", fookline);
		val.put("fook", fook);
		val.put("komase1", komase1);
		val.put("komase2", komase2);
		val.put("okiami", okiami);
		val.put("esa", esa);
		val.put("memo", memo);
		val.put("catchTime", catchTime);
		m_db.insert(TBL_NAME, null, val);
	}

	public void update(int id, String catchDateTime, String catchPlace,
			String catchSize, String photoPath, String rod, String reel,
			String floatStr, String floatInt, String line, String fookline,
			String fook, String komase1, String komase2, String okiami,
			String esa, String memo, String catchTime) {
		ContentValues val = new ContentValues();

		val.put("catchDate", catchDateTime);
		val.put("catchPlace", catchPlace);
		val.put("catchSize", catchSize);
		val.put("photoPath", photoPath);
		val.put("rod", rod);
		val.put("reel", reel);
		val.put("floatStr", floatStr);
		val.put("floatInt", floatInt);
		val.put("line", line);
		val.put("fookline", fookline);
		val.put("fook", fook);
		val.put("komase1", komase1);
		val.put("komase2", komase2);
		val.put("okiami", okiami);
		val.put("esa", esa);
		val.put("memo", memo);
		val.put("catchTime", catchTime);

		m_db.update(TBL_NAME, val, "_id=?",
				new String[] { Integer.toString(id) });

	}

	public void delete(int id) {
		m_db.delete(TBL_NAME, "_id=?", new String[] { Integer.toString(id) });
	}

	public ArrayList<ItemBean> loadAll(int sortDiv, String where) {
		int i;
		Cursor c;
		// String[] entries;
		ArrayList<ItemBean> rt = new ArrayList<ItemBean>();

		if (m_db == null) {
			return null;
		}

		String strSortDiv;
		if (sortDiv == Common.SORT_DIV_ASC) {
			strSortDiv = "catchDate ASC,catchTime ASC";
		} else {
			strSortDiv = "catchDate DESC,catchTime DESC";
		}
		c = m_db.query(TBL_NAME, new String[] { "_id", "catchDate",
				"catchPlace", "catchSize", "photoPath", "rod", "reel",
				"floatStr", "floatInt", "line", "fookline", "fook", "komase1",
				"komase2", "okiami", "esa", "memo", "catchTime" }, where, null,
				null, null, strSortDiv);

		int numRows = c.getCount();

		c.moveToFirst();

		// entries = new String[numRows];
		for (i = 0; i < numRows; i++) {
			ItemBean bean = new ItemBean();
			bean.set_id(c.getString(0));
			bean.setCatchDate(c.getString(1));
			bean.setCatchPlace(c.getString(2));
			if (c.getString(3).equals("")) {
				bean.setCatchSize(c.getString(3));
			} else {
				bean.setCatchSize(c.getString(3) + "cm");
			}
			bean.setPhotoPath(c.getString(4));
			if (c.getString(5) == null) {
				bean.setRod(0);
			} else {
				try {
					bean.setRod(Integer.parseInt(c.getString(5)));
				} catch (Exception e) {
					bean.setRod(0);
				}
			}
			if (c.getString(6) == null) {
				bean.setReel(0);
			} else {
				try {
					bean.setReel(Integer.parseInt(c.getString(6)));
				} catch (Exception e) {
					bean.setReel(0);
				}
			}
			if (c.getString(7) == null) {
				bean.setFloatStr(0);
			} else {
				try {
					bean.setFloatStr(Integer.parseInt(c.getString(7)));
				} catch (Exception e) {
					bean.setFloatStr(0);
				}
			}
			if (c.getString(8) == null) {
				bean.setFloatInt(0);
			} else {
				try {
					bean.setFloatInt(Integer.parseInt(c.getString(8)));
				} catch (Exception e) {
					bean.setFloatInt(0);
				}
			}
			if (c.getString(9) == null) {
				bean.setLine(0);
			} else {
				try {
					bean.setLine(Integer.parseInt(c.getString(9)));
				} catch (Exception e) {
					bean.setLine(0);
				}
			}
			if (c.getString(10) == null) {
				bean.setFookline(0);
			} else {
				try {
					bean.setFookline(Integer.parseInt(c.getString(10)));
				} catch (Exception e) {
					bean.setFookline(0);
				}
			}
			if (c.getString(11) == null) {
				bean.setFook(0);
			} else {
				try {
					bean.setFook(Integer.parseInt(c.getString(11)));
				} catch (Exception e) {
					bean.setFook(0);
				}
			}
			if (c.getString(12) == null) {
				bean.setKomase1(0);
			} else {
				try {
					bean.setKomase1(Integer.parseInt(c.getString(12)));
				} catch (Exception e) {
					bean.setKomase1(0);
				}
			}
			if (c.getString(13) == null) {
				bean.setKomase2(0);
			} else {
				try {
					bean.setKomase2(Integer.parseInt(c.getString(13)));
				} catch (Exception e) {
					bean.setKomase2(0);
				}
			}
			bean.setOkiami(c.getString(14));
			bean.setEsa(c.getString(15));
			bean.setMemo(c.getString(16));
			bean.setCatchTime(c.getString(17));
			c.moveToNext();
			rt.add(bean);
		}
		c.close();

		return rt;
	}

	public void Tackleadd(Integer _id, Integer _gyo, String _name,
			Integer _favorite) {
		ContentValues val = new ContentValues();

		val.put("_id", _id);
		val.put("_gyo", _gyo);
		val.put("_name", _name);
		val.put("_favorite", _favorite);
		m_db.insert(TACKLE_TBL_NAME, null, val);

	}

	public void TackleUpdate(int id, int gyo, int rowid, String name,
			int favorite) {
		ContentValues val = new ContentValues();

		val.put("_name", name);
		val.put("_favorite", favorite);

		m_db.update(TACKLE_TBL_NAME, val, "_id=? AND _gyo=?  AND _rowid=?",
				new String[] { Integer.toString(id), Integer.toString(gyo),
						Integer.toString(rowid) });
	}

	public void TackleUpdateForNo(int id, int oldgyo, int newgyo, int rowid) {
		ContentValues val = new ContentValues();

		val.put("_gyo", newgyo);

		m_db.update(TACKLE_TBL_NAME, val, "_id=? AND _gyo=? AND _rowid=?",
				new String[] { Integer.toString(id), Integer.toString(oldgyo),
						Integer.toString(rowid) });
	}

	public void TackleDelete(int id, int gyo, int rowid) {
		m_db.delete(TACKLE_TBL_NAME, "_id=? AND _gyo=? AND _rowid=?",
				new String[] { Integer.toString(id), Integer.toString(gyo),
						Integer.toString(rowid) });
	}

	public ArrayList<TackleItemBean> tackleLoadAll(String where) {
		int i;
		Cursor c;
		// String[] entries;
		ArrayList<TackleItemBean> rt = new ArrayList<TackleItemBean>();

		if (m_db == null) {
			return null;
		}

		c = m_db.query(TACKLE_TBL_NAME, new String[] { "_id", "_gyo", "_rowid",
				"_name", "_favorite" }, where, null, null, null, "_gyo ASC");

		int numRows = c.getCount();

		c.moveToFirst();

		// entries = new String[numRows];
		for (i = 0; i < numRows; i++) {
			TackleItemBean bean = new TackleItemBean();
			bean.set_id(c.getString(0));
			bean.set_gyo(c.getString(1));
			bean.set_rowid(c.getString(2));
			bean.set_name(c.getString(3));
			bean.set_favorite(c.getString(4));
			c.moveToNext();
			rt.add(bean);
		}
		c.close();

		return rt;
	}

	public List<KeyValuePair> getSpinnerData(int id) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT _rowid, _name");
		sql.append(" FROM " + TACKLE_TBL_NAME);
		sql.append(" WHERE _id=" + String.valueOf(id));
		sql.append(" ORDER BY _gyo");

		List<KeyValuePair> list = new ArrayList<KeyValuePair>();

		if (m_db == null) {
			return null;
		}

		Cursor cursor = m_db.rawQuery(sql.toString(), null);
		cursor.moveToFirst();
		try {
			list.add(new KeyValuePair(0, ""));
			if (cursor.getCount() != 0) {
				do {
					list.add(new KeyValuePair(cursor.getInt(0), cursor
							.getString(1)));
				} while (cursor.moveToNext());
			}
		} finally {
			cursor.close();
		}
		return list;
	}

	public int getFavoritePosition(int id, int type) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT _rowid ");
		sql.append(" FROM " + TACKLE_TBL_NAME);
		sql.append(" WHERE _id=" + String.valueOf(id));
		sql.append(" AND _favorite=" + String.valueOf(type));

		if (m_db == null) {
			return 0;
		}
		Cursor cursor = m_db.rawQuery(sql.toString(), null);
		cursor.moveToFirst();
		try {

			if (cursor.getCount() != 0) {
				return cursor.getInt(0);
			} else {
				return 0;
			}
		} finally {
			cursor.close();
		}
	}
}
