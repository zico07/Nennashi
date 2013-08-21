package com.nennashi.data;

public class WeatherItemBean {
	private CharSequence mTitle;
	private CharSequence mDescription;

	public WeatherItemBean() {
		mTitle = "";
		mDescription = "";
	}

	public CharSequence getDescription() {
		return mDescription;
	}

	public void setDescription(CharSequence description) {
		mDescription = description;
	}

	public CharSequence getTitle() {
		return mTitle;
	}

	public void setTitle(CharSequence title) {
		mTitle = title;
	}
}
