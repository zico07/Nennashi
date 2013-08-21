package com.nennashi.action;

import com.nennashi.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WaveActivity extends Activity {
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wave);

		progressDialog = new ProgressDialog(this);

		// Webビューの作成
		WebView webView = (WebView) findViewById(R.id.webview);
		webView.setVerticalScrollbarOverlay(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

				progressDialog.setTitle("通信中");
				progressDialog.setMessage("データ取得中・・・");
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.show();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressDialog.dismiss();
			}
		});
		WebSettings settings = webView.getSettings();
		settings.setSupportMultipleWindows(true);
		settings.setLoadsImagesAutomatically(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);
		settings.setLightTouchEnabled(true);
		settings.setJavaScriptEnabled(true);
		settings.setPluginsEnabled(true);
		webView.loadUrl("http://weather.yahoo.co.jp/weather/wave/");

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
		case R.id.menu_exit: {
			moveTaskToBack(true);
			return true;
		}
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
