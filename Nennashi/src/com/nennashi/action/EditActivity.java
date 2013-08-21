/**
 * 未使用
 */
package com.nennashi.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import com.nennashi.R;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.nennashi.data.ItemBean;
import com.nennashi.sql.DBStore;

public class EditActivity extends Activity {
	private DBStore store;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		ImageView iv = (ImageView) findViewById(R.id.image);
		if (iv.getDrawable() instanceof BitmapDrawable) {
			menu.setHeaderTitle("写真操作");
			menu.add(0, Common.CONTEXT_MENU_IMAGE_UP_ID, 0, "写真を拡大する");
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

			Intent it = new Intent(EditActivity.this, ImageViewActivity.class);

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
					Common.showMessage(EditActivity.this, e.getStackTrace()
							.toString());
				} catch (IOException e) {
					Common.showMessage(EditActivity.this, e.getStackTrace()
							.toString());
				}

				break;
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		return null;
	}

	private void toRegist() {
	}

	private void toDelete(final int id) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				EditActivity.this);
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
			Intent itp = new Intent(EditActivity.this, SettingsPreference.class);
			itp.setAction(Intent.ACTION_VIEW);
			startActivity(itp);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void toUpdate(int parseInt) {


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
