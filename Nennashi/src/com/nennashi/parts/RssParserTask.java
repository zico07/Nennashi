package com.nennashi.parts;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.nennashi.action.WeatherActivity;
import com.nennashi.data.WeatherItemBean;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Xml;
import android.widget.ListView;

public class RssParserTask extends
		AsyncTask<String, Integer, WeatherRSSAdapter> {

	private WeatherActivity mActivity;
	private WeatherRSSAdapter mAdapter;
	private ProgressDialog mProgressDialog;
	private ListView mListView;

	public RssParserTask(WeatherActivity activity, WeatherRSSAdapter adapter,
			ListView _listView) {
		mActivity = activity;
		mListView = _listView;
		mAdapter = adapter;
	}

	@Override
	protected void onPreExecute() {
		mProgressDialog = new ProgressDialog(mActivity);
		mProgressDialog.setTitle("通信中");
		mProgressDialog.setMessage("データ取得中・・・");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.show();
	}

	@Override
	protected WeatherRSSAdapter doInBackground(String... params) {
		WeatherRSSAdapter result = null;
		try {
			URL url = new URL(params[0]);
			InputStream is = url.openConnection().getInputStream();
			result = parseXml(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPostExecute(WeatherRSSAdapter result) {
		mProgressDialog.dismiss();
		mListView.setAdapter(result);
	}

	public WeatherRSSAdapter parseXml(InputStream is) throws IOException,
			XmlPullParserException {
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, null);

			int eventType = parser.getEventType();
			WeatherItemBean currentItem = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tag = null;
				switch (eventType) {
				case XmlPullParser.START_TAG:
					tag = parser.getName();
					if (tag.equals("item")) {
						currentItem = new WeatherItemBean();
					} else if (currentItem != null) {
						if (tag.equals("title")) {
							currentItem.setTitle(parser.nextText());
						} else if (tag.equals("description")) {
							currentItem.setDescription(parser.nextText()
									.replaceAll("<.+?>", ""));
						}
					}
					break;
				case XmlPullParser.END_TAG:
					tag = parser.getName();
					if (tag.equals("item")) {
						mAdapter.add(currentItem);
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mAdapter;
	}
}