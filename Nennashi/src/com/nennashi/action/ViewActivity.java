package com.nennashi.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.nennashi.R;
import com.nennashi.data.ItemBean;
import com.nennashi.parts.ListAdapter;
import com.nennashi.sql.DBStore;

public class ViewActivity extends ListActivity {
	private int _sortDiv = Common.SORT_DIV_DESC;
	private Boolean _copyFlg = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view);
		setData(_sortDiv);
		_copyFlg = false;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		ItemBean item = (ItemBean) l.getItemAtPosition(position);
		if (_copyFlg) {
			toRegist(item);
		} else {
			Intent it = new Intent(ViewActivity.this, EditMainActivity.class);
			it.putExtra("SelectData", item);
			it.setAction(Intent.ACTION_VIEW);
			startActivity(it);
		}
		_copyFlg = false;
	}

	@Override
	public void onRestart() {
		super.onRestart();
		setData(_sortDiv);
	}

	private void setData(int sortDiv) {
		String where = null;

		boolean prefChk = getSharedPreferences(Common.PREFERENCE_FILE_NAME,
				MODE_PRIVATE).getBoolean(Common.PREFERENCE_DATE_DISP_CHECK,
				false);
		if (!prefChk) {
			String prefCdt = getSharedPreferences(Common.PREFERENCE_FILE_NAME,
					MODE_PRIVATE).getString(
					Common.PREFERENCE_DATE_CONDITION_KEY, "");
			if (prefCdt != "") {
				String date = prefCdt.split(" ")[0];
				String cdt = prefCdt.split(" ")[1];

				if (cdt.equals(getResources().getString(
						R.string.dateconditions_before_txt))) {
					where = "catchDate <='" + date + "'";
				} else {
					where = "catchDate >='" + date + "'";
				}
			}
		}

		DBStore store = new DBStore(this);
		ArrayList<ItemBean> data = store.loadAll(sortDiv, where);
		store.close();
		ListAdapter adapter = new ListAdapter(getApplicationContext(), data);
		setListAdapter(adapter);
	}

	private void toRegist(ItemBean item) {
		DBStore store = new DBStore(this);
		String strPhoto = "";
		if (item.getPhotoPath() != "") {
			String imgName = Environment.getExternalStorageDirectory()
					.getPath() + "/cmr/" + System.currentTimeMillis() + ".jpg";
			try {
				FileChannel srcChannel = new FileInputStream(
						item.getPhotoPath()).getChannel();
				FileChannel dstChannel = new FileOutputStream(imgName)
						.getChannel();
				srcChannel.transferTo(0, srcChannel.size(), dstChannel);
				strPhoto = imgName;
				srcChannel.close();
				dstChannel.close();
			} catch (FileNotFoundException e) {
				return;
			} catch (IOException e) {
				return;
			}
		}
		store.add(item.getCatchDate(), item.getCatchPlace(), item
				.getCatchSize().replace("cm", ""), strPhoto, String.valueOf(item.getRod()),
				String.valueOf(item.getReel()), String.valueOf(item.getFloatStr()), String.valueOf(item
						.getFloatInt()), String.valueOf(item.getLine()), String.valueOf(item.getFookline()),
				String.valueOf(item.getFook()), String.valueOf(item.getKomase1()), String.valueOf(item
						.getKomase2()), item.getOkiami(), item.getEsa(), item
						.getMemo(), item.getCatchTime());
		store.close();

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("コピー");
		alertDialog.setMessage("コピーしました。");
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						setResult(RESULT_OK);
						setData(_sortDiv);
					}
				});
		alertDialog.create();
		alertDialog.show();
	}

	private void sortDispData(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_asc_date:
			_sortDiv = Common.SORT_DIV_ASC;
			setData(_sortDiv);
			return;
		case R.id.menu_desc_date:
			_sortDiv = Common.SORT_DIV_DESC;
			setData(_sortDiv);
			return;
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
		case R.id.menu_asc_date:
			sortDispData(item);
			return true;
		case R.id.menu_desc_date:
			sortDispData(item);
			return true;
		case R.id.menu_copy:
			_copyFlg = true;
			Toast.makeText(this, "一覧からコピー元を選択して下さい", Toast.LENGTH_LONG).show();
			return true;
		case R.id.menu_preference:
			Intent it = new Intent(ViewActivity.this, SettingsPreference.class);
			it.setAction(Intent.ACTION_VIEW);
			startActivity(it);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.getItem(2).setVisible(false);
		menu.getItem(3).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
}