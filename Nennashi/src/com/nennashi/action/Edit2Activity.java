package com.nennashi.action;

import com.nennashi.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.nennashi.data.ItemBean;
import com.nennashi.parts.KeyValuePairArrayAdapter;
import com.nennashi.sql.DBStore;

public class Edit2Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_tab2);
		try {
			Intent it = getIntent();

			// Common Setting
			initSetParts();

			final ItemBean item = (ItemBean) it
					.getSerializableExtra("SelectData");
			if (item == null) {

			} else {
				// From View
				setPartsFromView(item);
			}
		} catch (Exception e) {
			Common.showMessage(Edit2Activity.this, e.getStackTrace().toString());
		}
	}

	@Override
	public void onRestart() {
		super.onRestart();
		try {
		Intent it = getIntent();
		final ItemBean item = (ItemBean) it.getSerializableExtra("SelectData");
		if (item != null) {
			setPartsFromView(item);
		}
		} catch (Exception e) {
			Common.showMessage(Edit2Activity.this, e.getStackTrace().toString());
		}
	}

	private void setPartsFromView(ItemBean item)  {
		// Rod
		((Spinner) findViewById(R.id.rodspinner)).setSelection(getAdapter(Common.TACKLE_ID_ROD).getPosition(item.getRod()));

		// Reel
		((Spinner) findViewById(R.id.rodspinner)).setSelection(getAdapter(Common.TACKLE_ID_REEL).getPosition(item.getReel()));

		// Float Text
		((Spinner) findViewById(R.id.floatnamespinner)).setSelection(getAdapter(Common.TACKLE_ID_FLOAT).getPosition(item.getFloatStr()));

		// Float Spinner
		((Spinner) findViewById(R.id.floatspinner)).setSelection(item.getFloatInt());

		// Line
		((Spinner) findViewById(R.id.linespinner)).setSelection(getAdapter(Common.TACKLE_ID_LINE).getPosition(item.getLine()));

		// Fook Line
		((Spinner) findViewById(R.id.fooklinespinner)).setSelection(getAdapter(Common.TACKLE_ID_FOOKLINE).getPosition(item.getFookline()));

		// Fook
		((Spinner) findViewById(R.id.fookspinner)).setSelection(item.getFook());

		// Komase1
		((Spinner) findViewById(R.id.komase1spinner)).setSelection(getAdapter(Common.TACKLE_ID_KOMASE).getPosition(item.getKomase1()));

		// Komase2
		((Spinner) findViewById(R.id.komase2spinner)).setSelection(getAdapter(Common.TACKLE_ID_KOMASE).getPosition(item.getKomase2()));

		// Okiami
		((EditText) findViewById(R.id.okiamiText)).setText(item.getOkiami());

		// Esa
		((EditText) findViewById(R.id.esaText)).setText(item.getEsa());

		// Memo
		((EditText) findViewById(R.id.memoText)).setText(item.getMemo());
	}

	private void initSetParts() {
		// Favorit1
		((Button) findViewById(R.id.favorit1button))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						setFavorite(1);
					}
				});

		// Favorit2
		((Button) findViewById(R.id.favorit2button))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						setFavorite(2);
					}
				});

		((Spinner) findViewById(R.id.rodspinner))
				.setAdapter(getAdapter(Common.TACKLE_ID_ROD));

		((Spinner) findViewById(R.id.reelspinner))
				.setAdapter(getAdapter(Common.TACKLE_ID_REEL));

		((Spinner) findViewById(R.id.floatnamespinner))
				.setAdapter(getAdapter(Common.TACKLE_ID_FLOAT));

		((Spinner) findViewById(R.id.linespinner))
				.setAdapter(getAdapter(Common.TACKLE_ID_LINE));

		((Spinner) findViewById(R.id.fooklinespinner))
				.setAdapter(getAdapter(Common.TACKLE_ID_FOOKLINE));

		((Spinner) findViewById(R.id.komase1spinner))
				.setAdapter(getAdapter(Common.TACKLE_ID_KOMASE));

		((Spinner) findViewById(R.id.komase2spinner))
				.setAdapter(getAdapter(Common.TACKLE_ID_KOMASE));
	}

	private KeyValuePairArrayAdapter getAdapter(int id) {
		DBStore store = new DBStore(this);
		KeyValuePairArrayAdapter adapter = new KeyValuePairArrayAdapter(this,
				android.R.layout.simple_spinner_item, store.getSpinnerData(id));
		store.close();
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	private void setFavorite(int select,int partsid ,int tackleid){

		KeyValuePairArrayAdapter adapter = getAdapter(tackleid);
		((Spinner) findViewById(partsid)).setAdapter(adapter);
		DBStore store = new DBStore(this);
		((Spinner) findViewById(partsid)).setSelection(adapter.getPosition(store.getFavoritePosition(tackleid, select)));
		store.close();
	}
	private void setFavorite(int select) {
		setFavorite(select,R.id.rodspinner,Common.TACKLE_ID_ROD);
		setFavorite(select,R.id.reelspinner,Common.TACKLE_ID_REEL);
		setFavorite(select,R.id.floatnamespinner,Common.TACKLE_ID_FLOAT);
		setFavorite(select,R.id.linespinner,Common.TACKLE_ID_LINE);
		setFavorite(select,R.id.fooklinespinner,Common.TACKLE_ID_FOOKLINE);
		setFavorite(select,R.id.komase1spinner,Common.TACKLE_ID_KOMASE);
		//setFavorite(select,R.id.komase2spinner,Common.TACKLE_ID_KOMASE);

	}
}
