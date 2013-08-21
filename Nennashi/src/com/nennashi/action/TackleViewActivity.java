package com.nennashi.action;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nennashi.R;
import com.nennashi.data.TackleItemBean;
import com.nennashi.parts.TackleListAdapter;
import com.nennashi.sql.DBStore;

public class TackleViewActivity extends ListActivity {
	private String type;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tackleview);
		try {
			Intent it = getIntent();
			type = (String) it.getSerializableExtra("SelectType");
			TextView title = (TextView) findViewById(R.id.titletxt);
			switch (Integer.parseInt(type)) {
			case Common.TACKLE_ID_ROD:
				title.setText(R.string.tackle_rod_txt);
				break;
			case Common.TACKLE_ID_REEL:
				title.setText(R.string.tackle_reel_txt);
				break;
			case Common.TACKLE_ID_FLOAT:
				title.setText(R.string.tackle_float_txt);
				break;
			case Common.TACKLE_ID_LINE:
				title.setText(R.string.tackle_line_txt);
				break;
			case Common.TACKLE_ID_FOOKLINE:
				title.setText(R.string.tackle_fookline_txt);
				break;
			case Common.TACKLE_ID_KOMASE:
				title.setText(R.string.tackle_komase_txt);
				break;
			}
			setData();

			findViewById(R.id.registbutton).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					toRegist();
				}
			});

			findViewById(R.id.updatebutton).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					toUpdate();
				}
			});

			findViewById(R.id.deletebutton).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					toDelete();
				}
			});
		} catch (Exception e) {
			Common.showMessage(TackleViewActivity.this, e.getStackTrace()
					.toString());
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TackleItemBean item = (TackleItemBean) l.getItemAtPosition(position);
		EditText name = (EditText) findViewById(R.id.tackleNameText);
		TextView gyo = (TextView) findViewById(R.id.tackleNo);
		TextView rowid = (TextView) findViewById(R.id.rowid);
		RadioButton radioButton1=(RadioButton)findViewById(R.id.radiobutton_1);
		RadioButton radioButton2=(RadioButton)findViewById(R.id.radiobutton_2);
		RadioButton radioButtonNashi=(RadioButton)findViewById(R.id.radiobutton_nashi);
		gyo.setText(item.get_gyo());
		name.setText(item.get_name());
		rowid.setText(item.get_rowid());
		if(item.get_favorite().equals("1")){
			radioButton1.setChecked(true);
		}else if(item.get_favorite().equals("2")){
			radioButton2.setChecked(true);
		}else{
			radioButtonNashi.setChecked(true);
		}
	}

	private void setData() {
		TackleListAdapter adapter = new TackleListAdapter(getApplicationContext(), getData());
		setListAdapter(adapter);
	}

	private ArrayList<TackleItemBean> getData(){
		String where = null;

		switch (Integer.parseInt(type)) {
		case Common.TACKLE_ID_ROD:
			where = "_id ='" + Common.TACKLE_ID_ROD + "'";
			break;
		case Common.TACKLE_ID_REEL:
			where = "_id ='" + Common.TACKLE_ID_REEL + "'";
			break;
		case Common.TACKLE_ID_FLOAT:
			where = "_id ='" + Common.TACKLE_ID_FLOAT + "'";
			break;
		case Common.TACKLE_ID_LINE:
			where = "_id ='" + Common.TACKLE_ID_LINE + "'";
			break;
		case Common.TACKLE_ID_FOOKLINE:
			where = "_id ='" + Common.TACKLE_ID_FOOKLINE + "'";
			break;
		case Common.TACKLE_ID_KOMASE:
			where = "_id ='" + Common.TACKLE_ID_KOMASE + "'";
			break;
		}

		DBStore store = new DBStore(this);
		ArrayList<TackleItemBean> data = store.tackleLoadAll(where);
		store.close();

		return data;
	}

	private void clearDisp(){
		TextView no = (TextView) findViewById(R.id.tackleNo);
		EditText name = (EditText) findViewById(R.id.tackleNameText);
		RadioButton radioButtonN=(RadioButton)findViewById(R.id.radiobutton_nashi);

		no.setText("");
		name.setText("");
		radioButtonN.setChecked(true);
	}

	@Override
	public void onRestart() {
		super.onRestart();
		setData();
	}

	private void toRegist() {
		DBStore store = new DBStore(this);

		EditText name = (EditText) findViewById(R.id.tackleNameText);
		RadioButton radioButton1=(RadioButton)findViewById(R.id.radiobutton_1);
		RadioButton radioButton2=(RadioButton)findViewById(R.id.radiobutton_2);
		RadioButton radioButtonNashi=(RadioButton)findViewById(R.id.radiobutton_nashi);

		if(radioButton1.isChecked() == true) {
			store.Tackleadd(Integer.parseInt(type),getListAdapter().getCount()+1, name.getText().toString(),1);
        }else if(radioButton2.isChecked() == true){
        	store.Tackleadd(Integer.parseInt(type),getListAdapter().getCount()+1, name.getText().toString(),2);
        }else{
        	store.Tackleadd(Integer.parseInt(type),getListAdapter().getCount()+1, name.getText().toString(),0);
        }
		store.close();

		if(radioButtonNashi.isChecked() == false) {
			ArrayList<TackleItemBean> beanList = getData();
			for(TackleItemBean bean:beanList){
				if(radioButton1.isChecked() == true) {
					if(bean.get_favorite().equals("1") && bean.get_id().equals(type) && !bean.get_gyo().equals(String.valueOf(getListAdapter().getCount()+1))){
						DBStore store2 = new DBStore(this);
						store2.TackleUpdate(Integer.parseInt(bean.get_id()),Integer.parseInt(bean.get_gyo()),Integer.parseInt(bean.get_rowid()),bean.get_name(),0);
						store2.close();
					}
				}else if(radioButton2.isChecked() == true){
					if(bean.get_favorite().equals("2") && bean.get_id().equals(type) && !(bean.get_gyo().equals(String.valueOf(getListAdapter().getCount()+1)))){
						DBStore store2 = new DBStore(this);
						store2.TackleUpdate(Integer.parseInt(bean.get_id()),Integer.parseInt(bean.get_gyo()),Integer.parseInt(bean.get_rowid()),bean.get_name(),0);
						store2.close();
					}
				}
			}
		}
		setData();

		clearDisp();
		Toast.makeText(this, "登録しました。", Toast.LENGTH_LONG).show();
	}

	private void toUpdate() {
		TextView gyo = (TextView) findViewById(R.id.tackleNo);
		TextView rowid = (TextView) findViewById(R.id.rowid);
		EditText name = (EditText) findViewById(R.id.tackleNameText);
		RadioButton radioButton1=(RadioButton)findViewById(R.id.radiobutton_1);
		RadioButton radioButton2=(RadioButton)findViewById(R.id.radiobutton_2);
		RadioButton radioButtonNashi=(RadioButton)findViewById(R.id.radiobutton_nashi);

		try {

			DBStore store = new DBStore(this);
			if(radioButton1.isChecked() == true) {
				store.TackleUpdate(Integer.parseInt(type), Integer.parseInt(gyo.getText().toString()),Integer.parseInt(rowid.getText().toString()), name.getText().toString(), 1);
	        }else if(radioButton2.isChecked() == true){
	        	store.TackleUpdate(Integer.parseInt(type), Integer.parseInt(gyo.getText().toString()),Integer.parseInt(rowid.getText().toString()), name.getText().toString(), 2);
	        }else{
	        	store.TackleUpdate(Integer.parseInt(type), Integer.parseInt(gyo.getText().toString()),Integer.parseInt(rowid.getText().toString()), name.getText().toString(), 0);
	        }
			store.close();

			if(radioButtonNashi.isChecked() == false) {
				ArrayList<TackleItemBean> beanList = getData();
				for(TackleItemBean bean:beanList){
					if(radioButton1.isChecked() == true) {
						if(bean.get_favorite().equals("1") && bean.get_id().equals(type) && !bean.get_gyo().equals(gyo.getText().toString())){
							DBStore store2 = new DBStore(this);
							store2.TackleUpdate(Integer.parseInt(bean.get_id()),Integer.parseInt(bean.get_gyo()),Integer.parseInt(bean.get_rowid()),bean.get_name(),0);
							store2.close();
						}
					}else if(radioButton2.isChecked() == true){
						if(bean.get_favorite().equals("2") && bean.get_id().equals(type) && !(bean.get_gyo().equals(gyo.getText().toString()))){
							DBStore store2 = new DBStore(this);
							store2.TackleUpdate(Integer.parseInt(bean.get_id()),Integer.parseInt(bean.get_gyo()),Integer.parseInt(bean.get_rowid()),bean.get_name(),0);
							store2.close();
						}
					}
				}
			}

			setData();

			clearDisp();
			Toast.makeText(this, "更新しました。", Toast.LENGTH_LONG).show();

		} catch (Exception e) {

		}
	}

	private void toDelete() {
		TextView no = (TextView) findViewById(R.id.tackleNo);
		TextView rowid = (TextView) findViewById(R.id.rowid);
		try {
			DBStore store = new DBStore(this);
			store.TackleDelete(Integer.parseInt(type), Integer.parseInt(no.getText().toString()),Integer.parseInt(rowid.getText().toString()));
			store.close();
			ArrayList<TackleItemBean> beanList = getData();

			int length = beanList.size();

			for (int i = 0; i < length; i++) {
				TackleItemBean bean = beanList.get(i);
				int val =i+1;
				DBStore store2 = new DBStore(this);
				store2.TackleUpdateForNo(Integer.parseInt(bean.get_id()),Integer.parseInt(bean.get_gyo()),Integer.parseInt(bean.get_rowid()),val);
				store2.close();
			}

			setData();

			clearDisp();
			Toast.makeText(this, "削除しました。", Toast.LENGTH_LONG).show();
		} catch (Exception e) {

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
