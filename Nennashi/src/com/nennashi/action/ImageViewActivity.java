package com.nennashi.action;

import com.nennashi.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);

		Intent it = getIntent();
		Bitmap bmp = (Bitmap) it.getParcelableExtra("ImageData");
		if (bmp != null) {
			ImageView iv = (ImageView) findViewById(R.id.imageview);
			iv.setImageBitmap(bmp);
			iv.setScaleType(ImageView.ScaleType.CENTER);
		}
	}
}
