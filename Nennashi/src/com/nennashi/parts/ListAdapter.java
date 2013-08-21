package com.nennashi.parts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nennashi.R;
import com.nennashi.data.ItemBean;

public class ListAdapter extends ArrayAdapter<ItemBean> {

	private LayoutInflater mInflater;
	private TextView mInfo;
	private ImageView mPhoto;

	public ListAdapter(Context context, List<ItemBean> objects) {
		super(context, 0, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.rowitem, null);
		}
		final ItemBean item = this.getItem(position);
		if (item != null) {
			mInfo = (TextView) convertView.findViewById(R.id.infotext);
			mInfo.setText(new SimpleDateFormat("yyyy年MM月dd日 HH時mm分")
					.format(Date.parse(item.getCatchDate() + " "
							+ item.getCatchTime()))
					+ "\n" + item.getCatchPlace() + "\n" + item.getCatchSize());

			if (!item.getPhotoPath().equals("")) {
				try {
					File file = new File(item.getPhotoPath());
					FileInputStream imgStream;
					imgStream = new FileInputStream(file);
					Options options = new BitmapFactory.Options();
					options.inSampleSize = 4;
					Bitmap bm = BitmapFactory.decodeStream(imgStream, null,
							options);
					mPhoto = (ImageView) convertView.findViewById(R.id.image);
					mPhoto.setImageBitmap(bm);
				} catch (FileNotFoundException e) {
					// throw(e.getStackTrace().toString());
				}
			}else{
				mPhoto = (ImageView) convertView.findViewById(R.id.image);
				Bitmap bm = null;
				mPhoto.setImageBitmap(bm);
			}
		}
		return convertView;
	}
}
