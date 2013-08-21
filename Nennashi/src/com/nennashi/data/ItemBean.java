package com.nennashi.data;

import java.io.Serializable;

public class ItemBean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String catchDate;
	private String catchTime;
	private String catchPlace;
	private String catchSize;
	private String photoPath;
	private int rod;
	private int reel;
	private int floatStr;
	private int floatInt;
	private int line;
	private int fookline;
	private int fook;
	private int komase1;
	private int komase2;
	private String okiami;
	private String esa;
	private String memo;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCatchPlace() {
		return catchPlace;
	}

	public void setCatchPlace(String catchPlace) {
		this.catchPlace = catchPlace;
	}

	public String getCatchSize() {
		return catchSize;
	}

	public void setCatchSize(String catchSize) {
		this.catchSize = catchSize;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public int getRod() {
		return rod;
	}

	public void setRod(int rod) {
		this.rod = rod;
	}

	public int getReel() {
		return reel;
	}

	public void setReel(int reel) {
		this.reel = reel;
	}

	public int getFloatStr() {
		return floatStr;
	}

	public void setFloatStr(int floatStr) {
		this.floatStr = floatStr;
	}

	public int getFloatInt() {
		return floatInt;
	}

	public void setFloatInt(int floatInt) {
		this.floatInt = floatInt;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getFookline() {
		return fookline;
	}

	public void setFookline(int fookline) {
		this.fookline = fookline;
	}

	public int getFook() {
		return fook;
	}

	public void setFook(int fook) {
		this.fook = fook;
	}

	public int getKomase1() {
		return komase1;
	}

	public void setKomase1(int komase1) {
		this.komase1 = komase1;
	}

	public int getKomase2() {
		return komase2;
	}

	public void setKomase2(int komase2) {
		this.komase2 = komase2;
	}

	public String getOkiami() {
		return okiami;
	}

	public void setOkiami(String okiami) {
		this.okiami = okiami;
	}

	public String getEsa() {
		return esa;
	}

	public void setEsa(String esa) {
		this.esa = esa;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCatchDate() {
		return catchDate;
	}

	public void setCatchDate(String catchDate) {
		this.catchDate = catchDate;
	}

	public String getCatchTime() {
		return catchTime;
	}

	public void setCatchTime(String catchTime) {
		this.catchTime = catchTime;
	}

}
