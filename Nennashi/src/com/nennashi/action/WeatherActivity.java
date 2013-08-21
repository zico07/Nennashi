package com.nennashi.action;

import java.util.ArrayList;

import com.nennashi.R;
import com.nennashi.data.WeatherItemBean;
import com.nennashi.parts.RssParserTask;
import com.nennashi.parts.WeatherRSSAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class WeatherActivity extends Activity {
	public static final String RSS_FEED_URL = "http://feedproxy.google.com/";
	private ArrayList<WeatherItemBean> mItems;
	private WeatherRSSAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather);

		((Spinner) findViewById(R.id.areaSpinner))
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						setDetailAreaCmb(position);
						if (blInitFlg) {
							String prefArea = getSharedPreferences(
									Common.PREFERENCE_FILE_NAME, MODE_PRIVATE)
									.getString(Common.PREFERENCE_AREA_KEY, "");
							String prefCity = getSharedPreferences(
									Common.PREFERENCE_FILE_NAME, MODE_PRIVATE)
									.getString(Common.PREFERENCE_CITY_KEY, "");
							((Spinner) findViewById(R.id.areaDetailSpinner))
									.setSelection(getDetailAreaIdx(prefArea,
											prefCity));
							blInitFlg = false;
						}
					}

					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		((Spinner) findViewById(R.id.areaDetailSpinner))
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterview,
							View view, int i, long l) {
						ListView _listview = (ListView) findViewById(R.id.weatherlistView);
						mItems = new ArrayList<WeatherItemBean>();
						mAdapter = new WeatherRSSAdapter(WeatherActivity.this,
								mItems);
						RssParserTask task = new RssParserTask(
								WeatherActivity.this, mAdapter, _listview);
						task.execute(RSS_FEED_URL + getSelectURL());
					}

					public void onNothingSelected(AdapterView<?> adapterview) {

					}
				});

	}

	private boolean blInitFlg = false;

	@Override
	public void onStart() {
		super.onStart();
		String prefArea = getSharedPreferences(Common.PREFERENCE_FILE_NAME,
				MODE_PRIVATE).getString(Common.PREFERENCE_AREA_KEY, "");
		if (prefArea.equals("")) {
			((Spinner) findViewById(R.id.areaSpinner)).setSelection(0);
			setDetailAreaSpinner(R.array.area_hokkaido_spinner);
			((Spinner) findViewById(R.id.areaDetailSpinner)).setSelection(0);
		} else {
			blInitFlg = true;
			((Spinner) findViewById(R.id.areaSpinner)).setSelection(Common
					.getAreaEntriesIdx(prefArea));

			// setDetailAreaCmb(Common.getAreaEntriesIdx(prefArea));
			// ((Spinner)
			// findViewById(R.id.areaDetailSpinner)).setSelection(getDetailAreaIdx(prefArea,prefCity));
		}
	}

	private void setDetailAreaCmb(int position) {
		switch (position) {
		case 0:
			setDetailAreaSpinner(R.array.area_hokkaido_spinner);
			break;
		case 1:
			setDetailAreaSpinner(R.array.area_aomori_spinner);
			break;
		case 2:
			setDetailAreaSpinner(R.array.area_akita_spinner);
			break;
		case 3:
			setDetailAreaSpinner(R.array.area_iwate_spinner);
			break;
		case 4:
			setDetailAreaSpinner(R.array.area_yamagata_spinner);
			break;
		case 5:
			setDetailAreaSpinner(R.array.area_miyagi_spinner);
			break;
		case 6:
			setDetailAreaSpinner(R.array.area_fukushima_spinner);
			break;
		case 7:
			setDetailAreaSpinner(R.array.area_ibaraki_spinner);
			break;
		case 8:
			setDetailAreaSpinner(R.array.area_tochigi_spinner);
			break;
		case 9:
			setDetailAreaSpinner(R.array.area_gunma_spinner);
			break;
		case 10:
			setDetailAreaSpinner(R.array.area_saitama_spinner);
			break;
		case 11:
			setDetailAreaSpinner(R.array.area_tokyo_spinner);
			break;
		case 12:
			setDetailAreaSpinner(R.array.area_kanagawa_spinner);
			break;
		case 13:
			setDetailAreaSpinner(R.array.area_chiba_spinner);
			break;
		case 14:
			setDetailAreaSpinner(R.array.area_shizuoka_spinner);
			break;
		case 15:
			setDetailAreaSpinner(R.array.area_yamanashi_spinner);
			break;
		case 16:
			setDetailAreaSpinner(R.array.area_niigata_spinner);
			break;
		case 17:
			setDetailAreaSpinner(R.array.area_nagano_spinner);
			break;
		case 18:
			setDetailAreaSpinner(R.array.area_toyama_spinner);
			break;
		case 19:
			setDetailAreaSpinner(R.array.area_ishikawa_spinner);
			break;
		case 20:
			setDetailAreaSpinner(R.array.area_fukui_spinner);
			break;
		case 21:
			setDetailAreaSpinner(R.array.area_gifu_spinner);
			break;
		case 22:
			setDetailAreaSpinner(R.array.area_aichi_spinner);
			break;
		case 23:
			setDetailAreaSpinner(R.array.area_mie_spinner);
			break;
		case 24:
			setDetailAreaSpinner(R.array.area_shiga_spinner);
			break;
		case 25:
			setDetailAreaSpinner(R.array.area_kyoto_spinner);
			break;
		case 26:
			setDetailAreaSpinner(R.array.area_osaka_spinner);
			break;
		case 27:
			setDetailAreaSpinner(R.array.area_nara_spinner);
			break;
		case 28:
			setDetailAreaSpinner(R.array.area_wakayama_spinner);
			break;
		case 29:
			setDetailAreaSpinner(R.array.area_hyogo_spinner);
			break;
		case 30:
			setDetailAreaSpinner(R.array.area_okayama_spinner);
			break;
		case 31:
			setDetailAreaSpinner(R.array.area_tottori_spinner);
			break;
		case 32:
			setDetailAreaSpinner(R.array.area_hiroshima_spinner);
			break;
		case 33:
			setDetailAreaSpinner(R.array.area_shimane_spinner);
			break;
		case 34:
			setDetailAreaSpinner(R.array.area_yamaguchi_spinner);
			break;
		case 35:
			setDetailAreaSpinner(R.array.area_kagawa_spinner);
			break;
		case 36:
			setDetailAreaSpinner(R.array.area_tokushima_spinner);
			break;
		case 37:
			setDetailAreaSpinner(R.array.area_ehime_spinner);
			break;
		case 38:
			setDetailAreaSpinner(R.array.area_kochi_spinner);
			break;
		case 39:
			setDetailAreaSpinner(R.array.area_fukuoka_spinner);
			break;
		case 40:
			setDetailAreaSpinner(R.array.area_saga_spinner);
			break;
		case 41:
			setDetailAreaSpinner(R.array.area_nagasaki_spinner);
			break;
		case 42:
			setDetailAreaSpinner(R.array.area_oita_spinner);
			break;
		case 43:
			setDetailAreaSpinner(R.array.area_kumamoto_spinner);
			break;
		case 44:
			setDetailAreaSpinner(R.array.area_miyazaki_spinner);
			break;
		case 45:
			setDetailAreaSpinner(R.array.area_kagoshima_spinner);
			break;
		case 46:
			setDetailAreaSpinner(R.array.area_okinawa_spinner);
			break;
		}
	}

	private int getDetailAreaIdx(String areaName, String cityName) {
		int ret = 0;
		String[] strs = Common.getAreaEntries(areaName);
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(cityName)) {
				ret = i;
			}
		}
		return ret;
	}

	private void setDetailAreaSpinner(int areaCode) {
		ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this,
				areaCode, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		((Spinner) findViewById(R.id.areaDetailSpinner)).setAdapter(adapter);
	}

	private String getSelectURL() {
		switch (((Spinner) findViewById(R.id.areaSpinner))
				.getSelectedItemPosition()) {
		case 0:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_1100";
			case 1:
				return "hitokuchi_1200";
			case 2:
				return "hitokuchi_1300";
			case 3:
				return "hitokuchi_1400";
			case 4:
				return "hitokuchi_1500";
			case 5:
				return "hitokuchi_1600";
			case 6:
				return "hitokuchi_1710";
			case 7:
				return "hitokuchi_1720";
			case 8:
				return "hitokuchi_1730";
			case 9:
				return "hitokuchi_1800";
			case 10:
				return "hitokuchi_1900";
			case 11:
				return "hitokuchi_2000";
			case 12:
				return "hitokuchi_2100";
			case 13:
				return "hitokuchi_2200";
			case 14:
				return "hitokuchi_2300";
			case 15:
				return "hitokuchi_2400";
			}
			return "";
		case 1:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_3110";
			case 1:
				return "hitokuchi_3120";
			case 2:
				return "hitokuchi_3130";
			}
			return "";
		case 2:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_3210";
			case 1:
				return "hitokuchi_3220";
			}
			return "";
		case 3:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_3310";
			case 1:
				return "hitokuchi_3320";
			case 2:
				return "hitokuchi_3330";
			}
			return "";
		case 4:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_3510";
			case 1:
				return "hitokuchi_3520";
			case 2:
				return "hitokuchi_3530";
			case 3:
				return "hitokuchi_3540";
			}
			return "";
		case 5:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_3410";
			case 1:
				return "hitokuchi_3420";
			}
			return "";
		case 6:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_3620";
			case 1:
				return "hitokuchi_3610";
			case 2:
				return "hitokuchi_3630";
			}
			return "";
		case 7:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4010";
			case 1:
				return "hitokuchi_4020";
			}
			return "";
		case 8:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4110";
			case 1:
				return "hitokuchi_4120";
			}
			return "";
		case 9:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4210";
			case 1:
				return "hitokuchi_4220";
			}
			return "";
		case 10:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4310";
			case 1:
				return "hitokuchi_4320";
			case 2:
				return "hitokuchi_4330";
			}
			return "";
		case 11:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4410";
			case 1:
				return "hitokuchi_4420";
			case 2:
				return "hitokuchi_4430";
			case 3:
				return "hitokuchi_9600";
			}
			return "";
		case 12:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4610";
			case 1:
				return "hitokuchi_4620";
			}
			return "";
		case 13:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4510";
			case 1:
				return "hitokuchi_4520";
			case 2:
				return "hitokuchi_4530";
			}
			return "";
		case 14:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_5010";
			case 1:
				return "hitokuchi_5020";
			case 2:
				return "hitokuchi_5030";
			case 3:
				return "hitokuchi_5040";
			}
			return "";
		case 15:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4910";
			case 1:
				return "hitokuchi_4920";
			}
			return "";
		case 16:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_5410";
			case 1:
				return "hitokuchi_5420";
			case 2:
				return "hitokuchi_5430";
			case 3:
				return "hitokuchi_5440";
			}
			return "";
		case 17:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_4810";
			case 1:
				return "hitokuchi_4820";
			case 2:
				return "hitokuchi_4830";
			}
			return "";
		case 18:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_5510";
			case 1:
				return "hitokuchi_5520";
			}
			return "";
		case 19:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_5610";
			case 1:
				return "hitokuchi_5620";
			}
			return "";
		case 20:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_5710";
			case 1:
				return "hitokuchi_5720";
			}
			return "";
		case 21:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_5210";
			case 1:
				return "hitokuchi_5220";
			}
			return "";
		case 22:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_5110";
			case 1:
				return "hitokuchi_5120";
			}
			return "";
		case 23:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_5310";
			case 1:
				return "hitokuchi_5320";
			}
			return "";
		case 24:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6010";
			case 1:
				return "hitokuchi_6020";
			}
			return "";
		case 25:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_400";
			case 1:
				return "hitokuchi_6100";
			}
			return "";
		case 26:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6200";
			}
			return "";
		case 27:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6410";
			case 1:
				return "hitokuchi_6420";
			}
			return "";
		case 28:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6510";
			case 1:
				return "hitokuchi_6520";
			}
			return "";
		case 29:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6310";
			case 1:
				return "hitokuchi_6320";
			}
			return "";
		case 30:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6610";
			case 1:
				return "hitokuchi_6620";
			}
			return "";
		case 31:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6910";
			case 1:
				return "hitokuchi_6920";
			}
			return "";
		case 32:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6710";
			case 1:
				return "hitokuchi_6720";
			}
			return "";
		case 33:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_6810";
			case 1:
				return "hitokuchi_6820";
			case 2:
				return "hitokuchi_6830";
			}
			return "";
		case 34:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_8110";
			case 1:
				return "hitokuchi_8120";
			case 2:
				return "hitokuchi_8130";
			case 3:
				return "hitokuchi_8140";
			}
			return "";
		case 35:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_7200";
			}
			return "";
		case 36:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_7110";
			case 1:
				return "hitokuchi_7120";
			}
			return "";
		case 37:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_7310";
			case 1:
				return "hitokuchi_7320";
			case 2:
				return "hitokuchi_7330";
			}
			return "";
		case 38:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_7410";
			case 1:
				return "hitokuchi_7420";
			case 2:
				return "hitokuchi_7430";
			}
			return "";
		case 39:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_8210";
			case 1:
				return "hitokuchi_8220";
			case 2:
				return "hitokuchi_8230";
			case 3:
				return "hitokuchi_8240";
			}
			return "";
		case 40:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_8510";
			case 1:
				return "hitokuchi_8520";
			}
			return "";
		case 41:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_8410";
			case 1:
				return "hitokuchi_8420";
			case 2:
				return "hitokuchi_700";
			case 3:
				return "hitokuchi_800";
			}
			return "";
		case 42:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_8310";
			case 1:
				return "hitokuchi_8320";
			case 2:
				return "hitokuchi_8330";
			case 3:
				return "hitokuchi_8340";
			}
			return "";
		case 43:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_8610";
			case 1:
				return "hitokuchi_8620";
			case 2:
				return "hitokuchi_8630";
			case 3:
				return "hitokuchi_8640";
			}
			return "";
		case 44:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_8710";
			case 1:
				return "hitokuchi_8720";
			case 2:
				return "hitokuchi_8730";
			case 3:
				return "hitokuchi_8740";
			}
			return "";
		case 45:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_8810";
			case 1:
				return "hitokuchi_8820";
			case 2:
				return "hitokuchi_8830";
			case 3:
				return "hitokuchi_1000";
			}
			return "";
		case 46:
			switch (((Spinner) findViewById(R.id.areaDetailSpinner))
					.getSelectedItemPosition()) {
			case 0:
				return "hitokuchi_9110";
			case 1:
				return "hitokuchi_9120";
			case 2:
				return "hitokuchi_9130";
			case 3:
				return "hitokuchi_9200";
			case 4:
				return "hitokuchi_9300";
			case 5:
				return "hitokuchi_9400";
			case 6:
				return "hitokuchi_9500";
			}
			return "";
		default:
			return "";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_exit:
			moveTaskToBack(true);
			return true;
		case R.id.menu_preference:
			Intent itp = new Intent(WeatherActivity.this,
					SettingsPreference.class);
			itp.setAction(Intent.ACTION_VIEW);
			startActivity(itp);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.getItem(0).setVisible(false);
		menu.getItem(1).setVisible(false);
		menu.getItem(2).setVisible(false);
		menu.getItem(3).setVisible(false);
		menu.getItem(4).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
}