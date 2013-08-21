package com.nennashi.parts;

import java.util.List;

import com.nennashi.R;
import com.nennashi.data.WeatherItemBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WeatherRSSAdapter extends ArrayAdapter<WeatherItemBean> {
	private LayoutInflater mInflater;
	private TextView mTitle;
	private TextView mDescr;

	public WeatherRSSAdapter(Context context, List<WeatherItemBean> objects) {
		super(context, 0, objects);
		// TODO Auto-generated constructor stub
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// 一行ごとのビューを作成する。
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = mInflater.inflate(R.layout.weatheritem, null);
		}
		WeatherItemBean item = this.getItem(position);
		if (item != null) {
			String title = item.getTitle().toString();
			mTitle = (TextView) view.findViewById(R.id.textView1);
			mTitle.setText(title);
			String descr = item.getDescription().toString();
			mDescr = (TextView) view.findViewById(R.id.textView2);
			mDescr.setText(descr);
		}
		return view;
	}
}
