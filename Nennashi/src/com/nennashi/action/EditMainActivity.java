package com.nennashi.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.nennashi.R;
import com.nennashi.data.ItemBean;
import com.nennashi.parts.KeyValuePairArrayAdapter;
import com.nennashi.sql.DBStore;

import android.R.drawable;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;

public class EditMainActivity extends TabActivity {
	private DBStore store;
	private TabHost tabHost;
	private Intent tab1Intent;
	private Intent tab2Intent;
	private TabHost.TabSpec spec1;
	private TabHost.TabSpec spec2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit);

		final Intent it = getIntent();
		final ItemBean item = (ItemBean) it.getSerializableExtra("SelectData");

		tabHost = getTabHost();

		tab1Intent = new Intent().setClass(this, Edit1Activity.class);
		tab1Intent.putExtra("SelectData", item);
		spec1 = tabHost
				.newTabSpec("場所、サイズ")
				.setIndicator("",
						getResources().getDrawable(R.drawable.tab_tetorapot))
				.setContent(tab1Intent);
		tabHost.addTab(spec1);

		tab2Intent = new Intent().setClass(this, Edit2Activity.class);
		tab2Intent.putExtra("SelectData", item);
		spec2 = tabHost
				.newTabSpec("タックル")
				.setIndicator("",
						getResources().getDrawable(R.drawable.tab_rod))
				.setContent(tab2Intent);
		tabHost.addTab(spec2);
		tabHost.setCurrentTab(1);
		tabHost.setCurrentTab(0);

		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			public void onTabChanged(String tabId) {
				if(tabId == "タックル") {
					View tab2 = tabHost.getTabContentView().getChildAt(1);
					final ItemBean item = (ItemBean) it.getSerializableExtra("SelectData");

					if (item == null){return;}
					// Rod
					((Spinner) tab2.findViewById(R.id.rodspinner))
							.setSelection(getAdapter(Common.TACKLE_ID_ROD).getPosition(item.getRod()));

					// Reel
					((Spinner) tab2.findViewById(R.id.reelspinner)).setSelection(getAdapter(Common.TACKLE_ID_REEL).getPosition(item.getReel()));

					// Float Text
					((Spinner) tab2.findViewById(R.id.floatnamespinner)).setSelection(getAdapter(Common.TACKLE_ID_FLOAT).getPosition(item.getFloatStr()));

					// Float Spinner
					((Spinner) tab2.findViewById(R.id.floatspinner)).setSelection(item.getFloatInt());

					// Line
					((Spinner) tab2.findViewById(R.id.linespinner)).setSelection(getAdapter(Common.TACKLE_ID_LINE).getPosition(item.getLine()));

					// Fook Line
					((Spinner) tab2.findViewById(R.id.fooklinespinner)).setSelection(getAdapter(Common.TACKLE_ID_FOOKLINE).getPosition(item.getFookline()));

					// Fook
					((Spinner) tab2.findViewById(R.id.fookspinner)).setSelection(item.getFook());

					// Komase1
					((Spinner) tab2.findViewById(R.id.komase1spinner)).setSelection(getAdapter(Common.TACKLE_ID_KOMASE).getPosition(item.getKomase1()));

					// Komase2
					((Spinner) tab2.findViewById(R.id.komase2spinner)).setSelection(getAdapter(Common.TACKLE_ID_KOMASE).getPosition(item.getKomase2()));

					// Okiami
					((EditText) tab2.findViewById(R.id.okiamiText)).setText(item.getOkiami());

					// Esa
					((EditText) tab2.findViewById(R.id.esaText)).setText(item.getEsa());

					// Memo
					((EditText) tab2.findViewById(R.id.memoText)).setText(item.getMemo());
				}
			}
		});
	}

	private void toRegist() {
		View tab1 = tabHost.getTabContentView().getChildAt(0);
		View tab2 = tabHost.getTabContentView().getChildAt(1);

		KeyValuePairArrayAdapter rodAdpt = getAdapter(Common.TACKLE_ID_ROD);
		KeyValuePairArrayAdapter reelAdpt = getAdapter(Common.TACKLE_ID_REEL);
		KeyValuePairArrayAdapter floatAdpt = getAdapter(Common.TACKLE_ID_FLOAT);
		KeyValuePairArrayAdapter lineAdpt = getAdapter(Common.TACKLE_ID_LINE);
		KeyValuePairArrayAdapter fooklineAdpt = getAdapter(Common.TACKLE_ID_FOOKLINE);
		KeyValuePairArrayAdapter komaseAdpt = getAdapter(Common.TACKLE_ID_KOMASE);

		store = new DBStore(this);
		store.add(((Button) tab1.findViewById(R.id.datebutton)).getText()
				.toString(), ((EditText) tab1.findViewById(R.id.placeText))
				.getText().toString(), ((EditText) tab1
				.findViewById(R.id.sizeText)).getText().toString(),
				createImageFile(), String.valueOf(rodAdpt
						.getKey(((Spinner) tab2.findViewById(R.id.rodspinner))
								.getSelectedItemPosition())),

				String.valueOf(reelAdpt.getKey(((Spinner) tab2.findViewById(R.id.reelspinner))
						.getSelectedItemPosition())),
				String.valueOf(floatAdpt.getKey(((Spinner) tab2.findViewById(R.id.floatnamespinner)).getSelectedItemPosition())),
				String.valueOf(((Spinner) tab2.findViewById(R.id.floatspinner)).getSelectedItemPosition()),
				String.valueOf(lineAdpt.getKey(((Spinner) tab2.findViewById(R.id.linespinner)).getSelectedItemPosition())),
				String.valueOf(fooklineAdpt.getKey(((Spinner)tab2.findViewById(R.id.fooklinespinner)).getSelectedItemPosition())),
				String.valueOf(((Spinner) tab2.findViewById(R.id.fookspinner)).getSelectedItemPosition()),
				String.valueOf(komaseAdpt.getKey(((Spinner) tab2.findViewById(R.id.komase1spinner)).getSelectedItemPosition())),
				String.valueOf(komaseAdpt.getKey(((Spinner) tab2.findViewById(R.id.komase2spinner)).getSelectedItemPosition())),
				((EditText) tab2
						.findViewById(R.id.okiamiText)).getText().toString(),
				((EditText) tab2.findViewById(R.id.esaText)).getText()
						.toString(), ((EditText) tab2
						.findViewById(R.id.memoText)).getText().toString(),
				((Button) tab1.findViewById(R.id.timebutton)).getText()
						.toString());
		store.close();

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				EditMainActivity.this);
		alertDialog.setTitle("保存");
		alertDialog.setMessage("保存しました。");
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						setResult(RESULT_OK);
						finish();
					}
				});
		alertDialog.create();
		alertDialog.show();
	}

	private String createImageFile() {
		String ret = "";
		View tab1 = tabHost.getTabContentView().getChildAt(0);
		ImageView iv = (ImageView) tab1.findViewById(R.id.image);
		if (!(iv.getDrawable() instanceof BitmapDrawable)) {
			return ret;
		}
		FileOutputStream foStream = null;
		// Get Path
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/cmr/");
		// Make Folder
		if (!file.exists()) {
			file.mkdir();
		}

		String imgName = Environment.getExternalStorageDirectory().getPath()
				+ "/cmr/" + System.currentTimeMillis() + ".jpg";

		try {
			foStream = new FileOutputStream(imgName);

			BitmapDrawable a = (BitmapDrawable) iv.getDrawable();
			Bitmap b = a.getBitmap();
			b.compress(CompressFormat.JPEG, 100, foStream);
			foStream.close();
			ret = imgName;
		} catch (FileNotFoundException e) {
			Common.showMessage(EditMainActivity.this, e.getStackTrace()
					.toString());
		} catch (IOException e) {
			Common.showMessage(EditMainActivity.this, e.getStackTrace()
					.toString());
		}
		return ret;
	}

	private void toUpdate(int id) {

		Intent it = getIntent();
		final ItemBean item = (ItemBean) it.getSerializableExtra("SelectData");
		if (item != null) {
			File file = new File(item.getPhotoPath());
			file.delete();
		}
		View tab1 = tabHost.getTabContentView().getChildAt(0);
		View tab2 = tabHost.getTabContentView().getChildAt(1);

		KeyValuePairArrayAdapter rodAdpt = getAdapter(Common.TACKLE_ID_ROD);
		KeyValuePairArrayAdapter reelAdpt = getAdapter(Common.TACKLE_ID_REEL);
		KeyValuePairArrayAdapter floatAdpt = getAdapter(Common.TACKLE_ID_FLOAT);
		KeyValuePairArrayAdapter lineAdpt = getAdapter(Common.TACKLE_ID_LINE);
		KeyValuePairArrayAdapter fooklineAdpt = getAdapter(Common.TACKLE_ID_FOOKLINE);
		KeyValuePairArrayAdapter komaseAdpt = getAdapter(Common.TACKLE_ID_KOMASE);

		store = new DBStore(this);
		store.update(
				id,
				((Button) tab1.findViewById(R.id.datebutton)).getText()
						.toString(),
				((EditText) tab1.findViewById(R.id.placeText)).getText()
						.toString(),
				((EditText) tab1.findViewById(R.id.sizeText)).getText()
						.toString(),
				createImageFile(),
				String.valueOf(rodAdpt.getKey(((Spinner) tab2.findViewById(R.id.rodspinner))
						.getSelectedItemPosition())),
				String.valueOf(reelAdpt.getKey(((Spinner) tab2.findViewById(R.id.reelspinner))
						.getSelectedItemPosition())),
				String.valueOf(floatAdpt.getKey(((Spinner) tab2.findViewById(R.id.floatnamespinner))
						.getSelectedItemPosition())),
				String.valueOf(((Spinner) tab2.findViewById(R.id.floatspinner))
						.getSelectedItemPosition()),
				String.valueOf(lineAdpt.getKey(((Spinner) tab2.findViewById(R.id.linespinner))
						.getSelectedItemPosition())),
				String.valueOf(fooklineAdpt.getKey(
						((Spinner) tab2.findViewById(R.id.fooklinespinner))
								.getSelectedItemPosition())),
				String.valueOf(((Spinner) tab2.findViewById(R.id.fookspinner))
						.getSelectedItemPosition()), String
						.valueOf(komaseAdpt.getKey(((Spinner) tab2
								.findViewById(R.id.komase1spinner))
								.getSelectedItemPosition())), String
						.valueOf(komaseAdpt.getKey(((Spinner) tab2
								.findViewById(R.id.komase2spinner))
								.getSelectedItemPosition())), ((EditText) tab2
						.findViewById(R.id.okiamiText)).getText().toString(),
				((EditText) tab2.findViewById(R.id.esaText)).getText()
						.toString(), ((EditText) tab2
						.findViewById(R.id.memoText)).getText().toString(),
				((Button) tab1.findViewById(R.id.timebutton)).getText()
						.toString());
		store.close();

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				EditMainActivity.this);
		alertDialog.setTitle("更新");
		alertDialog.setMessage("更新しました。");
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						setResult(RESULT_OK);
						finish();
					}
				});
		alertDialog.create();
		alertDialog.show();
	}

	private void toDelete(final int id) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				EditMainActivity.this);
		alertDialog.setTitle("削除確認");
		alertDialog.setMessage("削除します。よろしいですか？");
		alertDialog.setPositiveButton("はい",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent it = getIntent();
						final ItemBean item = (ItemBean) it
								.getSerializableExtra("SelectData");
						if (item != null) {
							if (!item.getPhotoPath().equals("")) {
								File file = new File(item.getPhotoPath());
								file.delete();
							}
						}
						setResult(RESULT_OK);
						execDelete(id);
						finish();
					}
				});
		alertDialog.setNegativeButton("いいえ",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						return;
					}
				});
		alertDialog.create();
		alertDialog.show();
	}

	private void execDelete(int id) {
		store = new DBStore(this);
		store.delete(id);
		store.close();
	}

	private KeyValuePairArrayAdapter getAdapter(int id) {
		DBStore store = new DBStore(this);
		KeyValuePairArrayAdapter adapter = new KeyValuePairArrayAdapter(this,
				android.R.layout.simple_spinner_item, store.getSpinnerData(id));
		store.close();
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent it = getIntent();
		ItemBean bean = (ItemBean) it.getSerializableExtra("SelectData");
		switch (item.getItemId()) {
		case R.id.menu_exit:
			moveTaskToBack(true);
			return true;
		case R.id.menu_regist:
			if (bean == null) {
				toRegist();
			} else {
				toUpdate(Integer.parseInt(bean.get_id()));
			}
			return true;
		case R.id.menu_del:
			toDelete(Integer.parseInt(bean.get_id()));
			return true;
		case R.id.menu_preference:
			Intent itp = new Intent(EditMainActivity.this,
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
		menu.getItem(4).setVisible(false);
		Intent it = getIntent();
		ItemBean bean = (ItemBean) it.getSerializableExtra("SelectData");
		if (bean == null) {
			menu.getItem(3).setVisible(false);
		} else {
			menu.getItem(2).setIcon(drawable.ic_menu_save);
			menu.getItem(2).setTitle("更新");
		}
		return super.onPrepareOptionsMenu(menu);
	}
}
