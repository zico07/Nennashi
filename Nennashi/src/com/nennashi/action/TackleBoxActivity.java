package com.nennashi.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import com.nennashi.R;

public class TackleBoxActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tacklebox);

		findViewById(R.id.rodbutton).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent(TackleBoxActivity.this,
						TackleViewActivity.class);
				it.putExtra("SelectType", String.valueOf(Common.TACKLE_ID_ROD));
				startActivity(it);
			}
		});

		findViewById(R.id.reelbutton).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent(TackleBoxActivity.this,
						TackleViewActivity.class);
				it.putExtra("SelectType", String.valueOf(Common.TACKLE_ID_REEL));
				startActivity(it);
			}
		});

		findViewById(R.id.floatbutton).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Intent it = new Intent(TackleBoxActivity.this,
								TackleViewActivity.class);
						it.putExtra("SelectType",
								String.valueOf(Common.TACKLE_ID_FLOAT));
						startActivity(it);
					}
				});

		findViewById(R.id.linebutton).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it = new Intent(TackleBoxActivity.this,
						TackleViewActivity.class);
				it.putExtra("SelectType", String.valueOf(Common.TACKLE_ID_LINE));
				startActivity(it);
			}
		});

		findViewById(R.id.fooklinebutton).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Intent it = new Intent(TackleBoxActivity.this,
								TackleViewActivity.class);
						it.putExtra("SelectType",
								String.valueOf(Common.TACKLE_ID_FOOKLINE));
						startActivity(it);
					}
				});

		findViewById(R.id.komasebutton).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Intent it = new Intent(TackleBoxActivity.this,
								TackleViewActivity.class);
						it.putExtra("SelectType",
								String.valueOf(Common.TACKLE_ID_KOMASE));
						startActivity(it);
					}
				});

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
