package com.nennashi.data;

import java.io.Serializable;

public class TackleItemBean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String _gyo;
	private String _rowid;
	private String _name;
	private String _favorite;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_gyo() {
		return _gyo;
	}

	public void set_gyo(String _gyo) {
		this._gyo = _gyo;
	}

	public String get_rowid() {
		return _rowid;
	}

	public void set_rowid(String _rowid) {
		this._rowid = _rowid;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_favorite() {
		return _favorite;
	}

	public void set_favorite(String _favorite) {
		this._favorite = _favorite;
	}
}
