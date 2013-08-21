package com.nennashi.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nennashi.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingsPreference extends PreferenceActivity {

	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);

		sp = PreferenceManager.getDefaultSharedPreferences(this);

		CheckBoxPreference chk = (CheckBoxPreference) findPreference(Common.PREFERENCE_DATE_DISP_CHECK);
		chk.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				DateConditionPreference dc = (DateConditionPreference) findPreference(Common.PREFERENCE_DATE_CONDITION_KEY);
				if (Boolean.parseBoolean(newValue.toString())) {
					dc.setEnabled(false);
					dc.setSummary("");
					getSharedPreferences(Common.PREFERENCE_FILE_NAME,
							MODE_PRIVATE).edit()
							.remove(Common.PREFERENCE_DATE_CONDITION_KEY)
							.commit();

					dc.onSetInitialValue(
							false,
							new SimpleDateFormat("yyyy/MM/dd")
									.format(new Date())
									+ " "
									+ getResources().getString(
											R.string.dateconditions_before_txt));
				} else {
					dc.onSetInitialValue(
							false,
							new SimpleDateFormat("yyyy/MM/dd")
									.format(new Date())
									+ " "
									+ getResources().getString(
											R.string.dateconditions_before_txt));
					dc.setSummary(sp.getString(
							Common.PREFERENCE_DATE_CONDITION_KEY, ""));
					dc.setEnabled(true);
				}
				return true;
			}
		});

		DateConditionPreference dc = (DateConditionPreference) findPreference(Common.PREFERENCE_DATE_CONDITION_KEY);
		if (chk.isChecked()) {
			dc.setEnabled(false);
			dc.setSummary("");
		} else {
			dc.setEnabled(true);
			dc.setSummary(sp
					.getString(Common.PREFERENCE_DATE_CONDITION_KEY, ""));
		}
		dc.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object obj) {
				((DateConditionPreference) findPreference(Common.PREFERENCE_DATE_CONDITION_KEY))
						.setSummary(obj.toString());
				return true;
			}
		});

		ListPreference areaList = (ListPreference) findPreference(Common.PREFERENCE_AREA_KEY);
		final ListPreference cityList = (ListPreference) findPreference(Common.PREFERENCE_CITY_KEY);
		if (cityList.getValue() == null) {
			cityList.setEnabled(false);
		}
		cityList.setSummary(sp.getString(Common.PREFERENCE_CITY_KEY, ""));
		cityList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object obj) {
				((ListPreference) findPreference(Common.PREFERENCE_CITY_KEY))
						.setSummary(obj.toString());
				return true;
			}
		});
		areaList.setSummary(sp.getString(Common.PREFERENCE_AREA_KEY, ""));
		areaList.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object obj) {
				((ListPreference) findPreference(Common.PREFERENCE_AREA_KEY))
						.setSummary(obj.toString());
				cityList.setEnabled(true);
				cityList.setSummary("");
				String[] entries = Common.getAreaEntries(obj.toString());
				cityList.setEntries(entries);
				cityList.setEntryValues(entries);
				cityList.setValue(entries[0]);
				cityList.setSummary(entries[0]);
				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(listener);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(listener);
	}

	private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {

		public void onSharedPreferenceChanged(
				SharedPreferences sharedpreferences, String s) {
		}
	};
}
