package com.nennashi.action;

import com.nennashi.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		Button createbtn = (Button) findViewById(R.id.createbutton);
		createbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent(MainActivity.this,
						EditMainActivity.class);
				startActivity(it);
			}
		});

		Button viewbtn = (Button) findViewById(R.id.viewbutton);
		viewbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent(MainActivity.this, ViewActivity.class);
				startActivity(it);
			}
		});

		Button tidebtn = (Button) findViewById(R.id.wavebutton);
		tidebtn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent it = new Intent(MainActivity.this, WaveActivity.class);
				startActivity(it);
			}
		});

		Button weatherbtn = (Button) findViewById(R.id.weatherbutton);
		weatherbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent it = new Intent(MainActivity.this, WeatherActivity.class);
				startActivity(it);
			}
		});

		Button tackleboxbtn = (Button) findViewById(R.id.tackleboxbutton);
		tackleboxbtn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent it = new Intent(MainActivity.this,
						TackleBoxActivity.class);
				startActivity(it);
			}
		});
	}

	// TODO Inport Export
	/*
	 * private void Export() {
	 * 
	 * final String TAG = "copyDb2Sd"; try { // 保存先(SDカード)のディレクトリを確保 String
	 * pathSd = new StringBuilder()
	 * .append(Environment.getExternalStorageDirectory().getPath())
	 * .append("/nennashiBK").toString(); File filePathToSaved = new
	 * File(pathSd); if (!filePathToSaved.exists() && !filePathToSaved.mkdirs())
	 * { throw new IOException("FAILED_TO_CREATE_PATH_ON_SD"); }
	 * 
	 * final String fileDb = this.getDatabasePath("Nennashi.db").getPath();
	 * final String fileSd = new StringBuilder().append(pathSd)
	 * .append("/").append("NennashiBK").append(".db").toString();
	 * 
	 * Log.i(TAG, "copy from(DB): " + fileDb); Log.i(TAG, "copy to(SD) : " +
	 * fileSd);
	 * 
	 * FileChannel channelSource = new FileInputStream(fileDb) .getChannel();
	 * FileChannel channelTarget = new FileOutputStream(fileSd) .getChannel();
	 * 
	 * channelSource.transferTo(0, channelSource.size(), channelTarget);
	 * 
	 * channelSource.close(); channelTarget.close();
	 * 
	 * // export preferenceFile final String fileXML = "/data/data/" +
	 * this.getPackageName() + "/shared_prefs/" + this.getPackageName() +
	 * "_preferences.xml"; final String fileXMLSD = new
	 * StringBuilder().append(pathSd)
	 * .append("/").append("back__preferences.xml").toString();
	 * 
	 * Log.i(TAG, "copy from(XML): " + fileXML); Log.i(TAG, "copy to(SD) : " +
	 * pathSd);
	 * 
	 * channelSource = new FileInputStream(fileXML).getChannel(); channelTarget
	 * = new FileOutputStream(fileXMLSD).getChannel();
	 * 
	 * channelSource.transferTo(0, channelSource.size(), channelTarget);
	 * 
	 * channelSource.close(); channelTarget.close();
	 * 
	 * Toast.makeText(this, "エクスポートしました", Toast.LENGTH_LONG).show(); } catch
	 * (Exception e) { }
	 * 
	 * }
	 * 
	 * private void Inport() { final String TAG = "copyDb2Sd"; try { //
	 * 保存先(SDカード)のディレクトリを確保 String pathSd = new StringBuilder()
	 * .append(Environment.getExternalStorageDirectory().getPath())
	 * .append("/nennashiBK").toString(); File filePathToSaved = new
	 * File(pathSd); if (!filePathToSaved.exists() && !filePathToSaved.mkdirs())
	 * { throw new IOException("FAILED_TO_CREATE_PATH_ON_SD"); }
	 * 
	 * final String fileDb = this.getDatabasePath("Nennashi.db").getPath();
	 * final String fileSd = new StringBuilder().append(pathSd)
	 * .append("/").append("NennashiBK").append(".db").toString();
	 * 
	 * Log.i(TAG, "copy from(SD): " + fileSd); Log.i(TAG, "copy to(DB) : " +
	 * fileDb);
	 * 
	 * FileChannel channelSource = new FileInputStream(fileSd) .getChannel();
	 * FileChannel channelTarget = new FileOutputStream(fileDb) .getChannel();
	 * 
	 * channelSource.transferTo(0, channelSource.size(), channelTarget);
	 * 
	 * channelSource.close(); channelTarget.close();
	 * 
	 * // export preferenceFile final String fileXML = "/data/data/" +
	 * this.getPackageName() + "/shared_prefs/" + this.getPackageName() +
	 * "_preferences.xml"; final String fileXMLSD = new
	 * StringBuilder().append(pathSd)
	 * .append("/").append("back__preferences.xml").toString();
	 * 
	 * XmlPullParser parser = Xml.newPullParser(); File file = new
	 * File(fileXMLSD); FileInputStream is = new FileInputStream(file);
	 * parser.setInput(is, null); int eventType = parser.getEventType();
	 * 
	 * while (eventType != XmlPullParser.END_DOCUMENT) { switch (eventType) {
	 * case XmlPullParser.START_TAG: if (parser.getName().equals("string")) {
	 * for (int i = 0; i < parser.getAttributeCount(); i++) { if
	 * (parser.getAttributeValue(i).equals(
	 * Common.PREFERENCE_FAVORITE_ROD1_KEY)) { // Rod1 getSharedPreferences(
	 * Common.PREFERENCE_FILE_NAME, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE)
	 * .edit() .putString( Common.PREFERENCE_FAVORITE_ROD1_KEY,
	 * parser.nextText()).commit(); } else if
	 * (parser.getAttributeValue(i).equals(
	 * Common.PREFERENCE_FAVORITE_ROD2_KEY)) { // Rod2 getSharedPreferences(
	 * Common.PREFERENCE_FILE_NAME, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE)
	 * .edit() .putString( Common.PREFERENCE_FAVORITE_ROD2_KEY,
	 * parser.nextText()).commit(); } else if
	 * (parser.getAttributeValue(i).equals(
	 * Common.PREFERENCE_FAVORITE_REEL1_KEY)) { // Reel1 getSharedPreferences(
	 * Common.PREFERENCE_FILE_NAME, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE)
	 * .edit() .putString( Common.PREFERENCE_FAVORITE_REEL1_KEY,
	 * parser.nextText()).commit(); } else if
	 * (parser.getAttributeValue(i).equals(
	 * Common.PREFERENCE_FAVORITE_REEL2_KEY)) { getSharedPreferences(
	 * Common.PREFERENCE_FILE_NAME, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE)
	 * .edit() .putString( Common.PREFERENCE_FAVORITE_REEL2_KEY,
	 * parser.nextText()).commit(); } else if
	 * (parser.getAttributeValue(i).equals(
	 * Common.PREFERENCE_DATE_CONDITION_KEY)) { // DispDate
	 * getSharedPreferences( Common.PREFERENCE_FILE_NAME, MODE_WORLD_READABLE |
	 * MODE_WORLD_WRITEABLE) .edit() .putString(
	 * Common.PREFERENCE_DATE_CONDITION_KEY, parser.nextText()).commit(); } else
	 * if (parser.getAttributeValue(i).equals(
	 * Common.PREFERENCE_DATE_DISP_CHECK)) { getSharedPreferences(
	 * Common.PREFERENCE_FILE_NAME, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE)
	 * .edit() .putString( Common.PREFERENCE_DATE_DISP_CHECK,
	 * parser.nextText()).commit(); } } } break; case XmlPullParser.END_TAG:
	 * break; } eventType = parser.next(); }
	 * 
	 * channelSource = new FileInputStream(fileXMLSD).getChannel();
	 * channelTarget = new FileOutputStream(fileXML).getChannel();
	 * 
	 * channelSource.transferTo(0, channelSource.size(), channelTarget);
	 * 
	 * channelSource.close(); channelTarget.close();
	 * 
	 * Toast.makeText(this, "インポートしました", Toast.LENGTH_LONG).show(); } catch
	 * (Exception e) { } }
	 */
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
		menu.getItem(5).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
}