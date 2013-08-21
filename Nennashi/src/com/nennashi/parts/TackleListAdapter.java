package com.nennashi.parts;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nennashi.R;
import com.nennashi.data.TackleItemBean;

public class TackleListAdapter extends ArrayAdapter<TackleItemBean> {

	private LayoutInflater mInflater;

	public TackleListAdapter(Context context, List<TackleItemBean> objects) {
		super(context, 0, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.tacklerowitem, null);
		}
		final TackleItemBean item = this.getItem(position);
		if (item != null) {
			TextView no = (TextView) convertView.findViewById(R.id.gyo);
			no.setText(item.get_gyo());

			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(item.get_name());

			TextView favorite = (TextView) convertView.findViewById(R.id.favorite);
			try
			{
				if (Integer.parseInt(item.get_favorite())== 1){
					favorite.setText("①");
				}else if(Integer.parseInt(item.get_favorite())== 2){
					favorite.setText("②");
				}else{
					favorite.setText("");
				}
			}catch (NumberFormatException e) {

			}

		}
		return convertView;
	}
}
