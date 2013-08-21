package com.nennashi.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nennashi.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.provider.MediaStore;
import android.widget.ImageView;
import com.nennashi.data.ItemBean;

public class Edit1Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_tab1);

		try {
			Intent it = getIntent();
			final ItemBean item = (ItemBean) it
					.getSerializableExtra("SelectData");
			if (item == null) {
				// From Edit
				Date dt = new Date();
				Button datebtn = (Button) findViewById(R.id.datebutton);
				datebtn.setText(new SimpleDateFormat("yyyy/MM/dd").format(dt));

				Button timebtn = (Button) findViewById(R.id.timebutton);
				timebtn.setText(new SimpleDateFormat("HH:mm").format(dt));

			} else {
				// From View
				setPartsFromView(item);
			}
			// Common Setting
			initSetParts();

		} catch (Exception e) {
			Common.showMessage(Edit1Activity.this, e.getStackTrace().toString());
		}
	}

	private void setPartsFromView(ItemBean item) throws FileNotFoundException {
		// DateButton
		((Button) findViewById(R.id.datebutton)).setText(item.getCatchDate());

		// TimeButton
		((Button) findViewById(R.id.timebutton)).setText(item.getCatchTime());

		// Place
		((EditText) findViewById(R.id.placeText)).setText(item.getCatchPlace());

		// Size
		((EditText) findViewById(R.id.sizeText)).setText(item.getCatchSize()
				.replace("cm", ""));

		// Image
		if (!item.getPhotoPath().equals("")) {
			File file = new File(item.getPhotoPath());
			FileInputStream imgStream = new FileInputStream(file);
			Bitmap bm = BitmapFactory.decodeStream(imgStream);
			ImageView img = (ImageView) findViewById(R.id.image);
			img.setImageBitmap(bm);
		}

	}

	private void initSetParts() {
		((Button) findViewById(R.id.datebutton))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						showDialog(Common.DATE_DIALOG_ID);
					}
				});

		((Button) findViewById(R.id.timebutton))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						showDialog(Common.TIME_DIALOG_ID);
					}
				});

		ImageButton camerabtn = (ImageButton) findViewById(R.id.cameraButton);
		camerabtn.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String[] items = { "写真を撮影する", "写真を添付する" };
				new AlertDialog.Builder(Edit1Activity.this).setTitle("写真")
						.setItems(items, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
									startActivityForResult(new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE),
											Common.REQUEST_CODE_EXEC_CAMERA);
									break;
								case 1:
									if (!Environment.MEDIA_MOUNTED
											.equals(Environment
													.getExternalStorageState())) {
										Common.showMessage(Edit1Activity.this,
												"SDCARDが認識されません。");
										return;
									}

									Intent intent = new Intent();
									intent.setType("image/*");
									intent.setAction(Intent.ACTION_GET_CONTENT);
									startActivityForResult(intent,
											Common.REQUEST_CODE_ATTACH_IMAGE);
									break;
								default:
								}
							}
						}).show();
			}
		});

		// Image LongTouch
		ImageView imageview = (ImageView) findViewById(R.id.image);
		registerForContextMenu(imageview);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		ImageView iv = (ImageView) findViewById(R.id.image);
		if (iv.getDrawable() instanceof BitmapDrawable) {
			menu.setHeaderTitle("写真操作");
			//menu.add(0, Common.CONTEXT_MENU_IMAGE_UP_ID, 0, "写真を拡大する");
			menu.add(0, Common.CONTEXT_MENU_IMAGE_DEL_ID, 0, "写真を削除する");
		}
		super.onCreateContextMenu(menu, view, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ImageView iv = (ImageView) findViewById(R.id.image);
		switch (item.getItemId()) {
		case Common.CONTEXT_MENU_IMAGE_UP_ID:
			if (!(iv.getDrawable() instanceof BitmapDrawable)) {
				return true;
			}

			Intent it = new Intent(Edit1Activity.this, ImageViewActivity.class);

			BitmapDrawable a = (BitmapDrawable) iv.getDrawable();
			Bitmap bmp = a.getBitmap();

			int width = bmp.getWidth();
			int height = bmp.getHeight();
			int newWidth = 450;
			int newHeight = 450;

			// calculate the scale - in this case = 0.4f
			float scaleWidth = ((float) newWidth) / width;
			float scaleHeight = ((float) newHeight) / height;

			// createa matrix for the manipulation
			Matrix matrix = new Matrix();
			// resize the bit map
			matrix.postScale(scaleWidth, scaleHeight);

			// recreate the new Bitmap
			Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width,
					height, matrix, true);

			it.putExtra("ImageData", resizedBitmap);
			it.setAction(Intent.ACTION_VIEW);
			startActivity(it);
			return true;
		case Common.CONTEXT_MENU_IMAGE_DEL_ID:
			if (!(iv.getDrawable() instanceof BitmapDrawable)) {
				return true;
			}
			iv.setImageDrawable(null);

			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			ImageView img = (ImageView) findViewById(R.id.image);
			switch (requestCode) {
			case Common.REQUEST_CODE_EXEC_CAMERA:
				// Get Image Capture
				Bitmap captureImage = (Bitmap) data.getExtras().get("data");
				img.setImageBitmap(captureImage);
				break;
			case Common.REQUEST_CODE_ATTACH_IMAGE:
				// Attach Image
				try {
					InputStream in = getContentResolver().openInputStream(
							data.getData());
					Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap btmp = BitmapFactory.decodeStream(in, null, options);
					in.close();
					img.setImageBitmap(btmp);
				} catch (FileNotFoundException e) {
					Common.showMessage(Edit1Activity.this, e.getStackTrace()
							.toString());
				} catch (IOException e) {
					Common.showMessage(Edit1Activity.this, e.getStackTrace()
							.toString());
				}

				break;
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Button datebtn = (Button) findViewById(R.id.datebutton);
		Button timebtn = (Button) findViewById(R.id.timebutton);
		final int year = Integer.parseInt(datebtn.getText().toString()
				.split("/")[0]);
		final int month = Integer.parseInt(datebtn.getText().toString()
				.split("/")[1]) - 1;
		final int day = Integer.parseInt(datebtn.getText().toString()
				.split("/")[2]);
		final int hour = Integer.parseInt(timebtn.getText().toString()
				.split(":")[0]);
		final int minute = Integer.parseInt(timebtn.getText().toString()
				.split(":")[1]);

		switch (id) {
		case Common.DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, year, month,
					day);
		case Common.TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, hour, minute,
					true);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String strMonthOfYear = String.valueOf(monthOfYear + 1);
			if (strMonthOfYear.length() == 1) {
				strMonthOfYear = "0" + strMonthOfYear;
			}
			Button datebtn = (Button) findViewById(R.id.datebutton);
			datebtn.setText(String.valueOf(year) + "/" + strMonthOfYear + "/"
					+ String.valueOf(dayOfMonth));
		}
	};

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker timepicker, int hour, int minute) {
			Button timebtn = (Button) findViewById(R.id.timebutton);
			String strHour = String.valueOf(hour);
			String strMinute = String.valueOf(minute);
			if (strHour.length() == 1) {
				strHour = "0" + strHour;
			}
			if (strMinute.length() == 1) {
				strMinute = "0" + strMinute;
			}
			timebtn.setText(strHour + ":" + strMinute);
		}
	};
}
