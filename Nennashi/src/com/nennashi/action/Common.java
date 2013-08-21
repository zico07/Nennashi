package com.nennashi.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.widget.Toast;

public class Common {
	public static final int SORT_DIV_ASC = 0;
	public static final int SORT_DIV_DESC = 1;
	public static final int DATE_DIALOG_ID = 0;
	public static final int TIME_DIALOG_ID = 1;
	public static final int REQUEST_CODE_EXEC_CAMERA = 100;
	public static final int REQUEST_CODE_ATTACH_IMAGE = 200;
	public static final int CONTEXT_MENU_IMAGE_UP_ID = 1;
	public static final int CONTEXT_MENU_IMAGE_DEL_ID = 2;
	public static final String PREFERENCE_FILE_NAME = "com.nennashi_preferences";
	public static final String PREFERENCE_DATE_DISP_CHECK = "datedispchk";
	public static final String PREFERENCE_DATE_CONDITION_KEY = "datecondition";
	public static final String PREFERENCE_FAVORITE_ROD1_KEY = "favoriteRod1";
	public static final String PREFERENCE_FAVORITE_REEL1_KEY = "favoriteReel1";
	public static final String PREFERENCE_FAVORITE_ROD2_KEY = "favoriteRod2";
	public static final String PREFERENCE_FAVORITE_REEL2_KEY = "favoriteReel2";
	public static final String PREFERENCE_AREA_KEY = "areaList";
	public static final String PREFERENCE_CITY_KEY = "cityList";

	public static final int TACKLE_ID_ROD = 1;
	public static final int TACKLE_ID_REEL = 2;
	public static final int TACKLE_ID_FLOAT = 3;
	public static final int TACKLE_ID_LINE = 4;
	public static final int TACKLE_ID_FOOKLINE = 5;
	public static final int TACKLE_ID_KOMASE = 6;

	public static final String PREFERENCE_CITY_KEY_HOKKAIDO = "area_hokkaido_spinner";
	public static final String PREFERENCE_CITY_KEY_AOMORI = "area_aomori_spinner";
	public static final String PREFERENCE_CITY_KEY_AKITA = "area_akita_spinner";
	public static final String PREFERENCE_CITY_KEY_IWATE = "area_iwate_spinner";
	public static final String PREFERENCE_CITY_KEY_YAMAGATA = "area_yamagata_spinner";
	public static final String PREFERENCE_CITY_KEY_MIYAGI = "area_miyagi_spinner";
	public static final String PREFERENCE_CITY_KEY_FUKUSHIMA = "area_fukushima_spinner";
	public static final String PREFERENCE_CITY_KEY_IBARAKI = "area_ibaraki_spinner";
	public static final String PREFERENCE_CITY_KEY_TOCHIGI = "area_tochigi_spinner";
	public static final String PREFERENCE_CITY_KEY_GUNMA = "area_gunma_spinner";
	public static final String PREFERENCE_CITY_KEY_SAITAMA = "area_saitama_spinner";
	public static final String PREFERENCE_CITY_KEY_TOKYO = "area_tokyo_spinner";
	public static final String PREFERENCE_CITY_KEY_KANAGAWA = "area_kanagawa_spinner";
	public static final String PREFERENCE_CITY_KEY_CHIBA = "area_chiba_spinner";
	public static final String PREFERENCE_CITY_KEY_SHIZUOKA = "area_shizuoka_spinner";
	public static final String PREFERENCE_CITY_KEY_YAMANASHI = "area_yamanashi_spinner";
	public static final String PREFERENCE_CITY_KEY_NIIGATA = "area_niigata_spinner";
	public static final String PREFERENCE_CITY_KEY_NAGANO = "area_nagano_spinner";
	public static final String PREFERENCE_CITY_KEY_TOYAMA = "area_toyama_spinner";
	public static final String PREFERENCE_CITY_KEY_ISHIKAWA = "area_ishikawa_spinner";
	public static final String PREFERENCE_CITY_KEY_FUKUI = "area_fukui_spinner";
	public static final String PREFERENCE_CITY_KEY_GIFU = "area_gifu_spinner";
	public static final String PREFERENCE_CITY_KEY_AICHI = "area_aichi_spinner";
	public static final String PREFERENCE_CITY_KEY_MIE = "area_mie_spinner";
	public static final String PREFERENCE_CITY_KEY_SHIGA = "area_shiga_spinner";
	public static final String PREFERENCE_CITY_KEY_KYOTO = "area_kyoto_spinner";
	public static final String PREFERENCE_CITY_KEY_OSAKA = "area_osaka_spinner";
	public static final String PREFERENCE_CITY_KEY_NARA = "area_nara_spinner";
	public static final String PREFERENCE_CITY_KEY_WAKAYAMA = "area_wakayama_spinner";
	public static final String PREFERENCE_CITY_KEY_HYOGO = "area_hyogo_spinner";
	public static final String PREFERENCE_CITY_KEY_OKAYAMA = "area_okayama_spinner";
	public static final String PREFERENCE_CITY_KEY_TOTTORI = "area_tottori_spinner";
	public static final String PREFERENCE_CITY_KEY_HIROSHIMA = "area_hiroshima_spinner";
	public static final String PREFERENCE_CITY_KEY_SHIMANE = "area_shimane_spinner";
	public static final String PREFERENCE_CITY_KEY_YAMAGUCHI = "area_yamaguchi_spinner";
	public static final String PREFERENCE_CITY_KEY_KAGAWA = "area_kagawa_spinner";
	public static final String PREFERENCE_CITY_KEY_TOKUSHIMA = "area_tokushima_spinner";
	public static final String PREFERENCE_CITY_KEY_EHIME = "area_ehime_spinner";
	public static final String PREFERENCE_CITY_KEY_KOCHI = "area_kochi_spinner";
	public static final String PREFERENCE_CITY_KEY_FUKUOKA = "area_fukuoka_spinner";
	public static final String PREFERENCE_CITY_KEY_SAGA = "area_saga_spinner";
	public static final String PREFERENCE_CITY_KEY_NAGASAKI = "area_nagasaki_spinner";
	public static final String PREFERENCE_CITY_KEY_OITA = "area_oita_spinner";
	public static final String PREFERENCE_CITY_KEY_KUMAMOTO = "area_kumamoto_spinner";
	public static final String PREFERENCE_CITY_KEY_MIYAZKI = "area_miyazaki_spinner";
	public static final String PREFERENCE_CITY_KEY_KAGOSHIMA = "area_kagoshima_spinner";
	public static final String PREFERENCE_CITY_KEY_OKINAWA = "area_okinawa_spinner";

	public static final String DB_NAME = "Nennashi.db";

	public static void showMessage(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static byte[] getByteArrayFromURL(String strUrl) {
		byte[] line = new byte[1024];
		byte[] result = null;
		HttpURLConnection con = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		int size = 0;
		try {
			// HTTP open
			URL url = new URL(strUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			in = con.getInputStream();

			// Read Byte
			out = new ByteArrayOutputStream();
			while (true) {
				size = in.read(line);
				if (size <= 0) {
					break;
				}
				out.write(line, 0, size);
			}
			result = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.disconnect();
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String[] getAreaEntries(String obj) {
		String[] mentries = null;
		if (obj.toString().equals("北海道")) {
			String[] entries = { "宗谷地方(稚内)", "上川地方(旭川)", "留萌地方(留萌)",
					"石狩地方(札幌)", "空知地方(岩見沢)", "後志地方(倶知安)", "網走地方(網走)",
					"北見地方(北見)", "紋別地方(紋別)", "根室地方(根室)", "釧路地方(釧路)", "十勝地方(帯広)",
					"胆振地方(室蘭)", "日高地方(浦河)", "渡島地方(函館)", "檜山地方(江差)" };
			return entries;
		} else if (obj.toString().equals("青森県")) {
			String[] entries = { "津軽(青森)", "下北(むつ)", "三八上北(八戸)" };
			return entries;
		} else if (obj.toString().equals("秋田県")) {
			String[] entries = { "沿岸(秋田)", "内陸(横手)" };
			return entries;
		} else if (obj.toString().equals("岩手県")) {
			String[] entries = { "内陸(盛岡)", "沿岸北部(宮古)", "沿岸南部(大船渡)" };
			return entries;
		} else if (obj.toString().equals("山形県")) {
			String[] entries = { "村山(山形)", "置賜(米沢)", "庄内(酒田)", "最上(新庄)" };
			return entries;
		} else if (obj.toString().equals("宮城県")) {
			String[] entries = { "東部(仙台)", "西部(白石)" };
			return entries;
		} else if (obj.toString().equals("福島県")) {
			String[] entries = { "浜通り(相馬)", "中通り(福島)", "会津(会津若松)" };
			return entries;
		} else if (obj.toString().equals("茨城県")) {
			String[] entries = { "北部(水戸)", "南部(土浦)" };
			return entries;
		} else if (obj.toString().equals("栃木県")) {
			String[] entries = { "南部(宇都宮)", "北部(大田原)" };
			return entries;
		} else if (obj.toString().equals("群馬県")) {
			String[] entries = { "南部(前橋)", "北部(水上)" };
			return entries;
		} else if (obj.toString().equals("埼玉県")) {
			String[] entries = { "南部(さいたま)", "北部(熊谷)", "秩父地方(秩父)" };
			return entries;
		} else if (obj.toString().equals("東京都")) {
			String[] entries = { "東京地方(東京)", "伊豆諸島北部(大島)", "伊豆諸島南部(八丈島)",
					"小笠原諸島(父島)" };
			return entries;
		} else if (obj.toString().equals("神奈川県")) {
			String[] entries = { "東部(横浜)", "西部(小田原)" };
			return entries;
		} else if (obj.toString().equals("千葉県")) {
			String[] entries = { "北西部(千葉)", "北東部(銚子)", "南部(館山)" };
			return entries;
		} else if (obj.toString().equals("静岡県")) {
			String[] entries = { "中部(静岡)", "伊豆(石廊崎)", "東部(三島)", "西部(浜松)" };
			return entries;
		} else if (obj.toString().equals("山梨県")) {
			String[] entries = { "中・西部(甲府)", "東部・富士五湖(河口湖)" };
			return entries;
		} else if (obj.toString().equals("新潟県")) {
			String[] entries = { "下越(新潟)", "中越(長岡)", "上越(高田)", "佐渡(相川)" };
			return entries;
		} else if (obj.toString().equals("長野県")) {
			String[] entries = { "北部(長野)", "中部(松本)", "西部(飯田)" };
			return entries;
		} else if (obj.toString().equals("富山県")) {
			String[] entries = { "東部(富山)", "西部(伏木)" };
			return entries;
		} else if (obj.toString().equals("石川県")) {
			String[] entries = { "加賀(金沢)", "能登(輪島)" };
			return entries;
		} else if (obj.toString().equals("福井県")) {
			String[] entries = { "嶺北(福井)", "嶺南(敦賀)" };
			return entries;
		} else if (obj.toString().equals("岐阜県")) {
			String[] entries = { "美濃地方(岐阜)", "飛騨地方(高山)" };
			return entries;
		} else if (obj.toString().equals("愛知県")) {
			String[] entries = { "西部(名古屋)", "東部(豊橋)" };
			return entries;
		} else if (obj.toString().equals("三重県")) {
			String[] entries = { "北中部(津)", "南部(尾鷲)" };
			return entries;
		} else if (obj.toString().equals("滋賀県")) {
			String[] entries = { "南部(大津)", "北部(彦根)" };
			return entries;
		} else if (obj.toString().equals("京都府")) {
			String[] entries = { "北部(舞鶴)", "南部(京都)" };
			return entries;
		} else if (obj.toString().equals("大阪府")) {
			String[] entries = { "大阪府(大阪)" };
			return entries;
		} else if (obj.toString().equals("奈良県")) {
			String[] entries = { "北部(奈良)", "南部(風屋)" };
			return entries;
		} else if (obj.toString().equals("和歌山県")) {
			String[] entries = { "北部(和歌山)", "南部(潮岬)" };
			return entries;
		} else if (obj.toString().equals("兵庫県")) {
			String[] entries = { "南部(神戸)", "北部(豊岡)" };
			return entries;
		} else if (obj.toString().equals("岡山県")) {
			String[] entries = { "南部(岡山)", "北部(津山)" };
			return entries;
		} else if (obj.toString().equals("鳥取県")) {
			String[] entries = { "東部(鳥取)", "中・西部(米子)" };
			return entries;
		} else if (obj.toString().equals("広島県")) {
			String[] entries = { "南部(広島)", "北部(庄島)" };
			return entries;
		} else if (obj.toString().equals("島根県")) {
			String[] entries = { "東部(松江)", "西部(浜田)", "隠岐(西郷)" };
			return entries;
		} else if (obj.toString().equals("山口県")) {
			String[] entries = { "西部(下関)", "中部(山口)", "東部(柳井)", "北部(萩)" };
			return entries;
		} else if (obj.toString().equals("香川県")) {
			String[] entries = { "香川県(高松)" };
			return entries;
		} else if (obj.toString().equals("徳島県")) {
			String[] entries = { "北部(徳島)", "南部(日和佐)" };
			return entries;
		} else if (obj.toString().equals("愛媛県")) {
			String[] entries = { "中予(松山)", "東予(新居浜)", "南予(宇和島)" };
			return entries;
		} else if (obj.toString().equals("高知県")) {
			String[] entries = { "中部(高知)", "東部(室戸岬)", "西部(清水)" };
			return entries;
		} else if (obj.toString().equals("福岡県")) {
			String[] entries = { "福岡地方(福岡)", "北九州地方(八幡)", "筑豊地方(飯塚)",
					"筑後地方(久留米)" };
			return entries;
		} else if (obj.toString().equals("佐賀県")) {
			String[] entries = { "南部(佐賀)", "北部(伊万里)" };
			return entries;
		} else if (obj.toString().equals("長崎県")) {
			String[] entries = { "南部(長崎)", "北部(佐世保)", "壱岐・対馬(厳原)", "五島(福江)" };
			return entries;
		} else if (obj.toString().equals("大分県")) {
			String[] entries = { "中部(大分)", "北部(中津)", "西部(日田)", "南部(佐伯)" };
			return entries;
		} else if (obj.toString().equals("熊本県")) {
			String[] entries = { "熊本地方(熊本)", "阿蘇地方(阿蘇乙姫)", "天草・芦北地方(牛深)",
					"球磨地方(人吉)" };
			return entries;
		} else if (obj.toString().equals("宮崎県")) {
			String[] entries = { "南部平野部(宮崎)", "北部平野部(延岡)", "南部山沿い(都城)",
					"北部山沿い(高千穂)" };
			return entries;
		} else if (obj.toString().equals("鹿児島県")) {
			String[] entries = { "薩摩地方(鹿児島)", "大隈地方(鹿屋)", "種子島・屋久島地方(西之表)",
					"奄美地方(名瀬)" };
			return entries;
		} else if (obj.toString().equals("沖縄県")) {
			String[] entries = { "本島中南部(那覇)", "本島北部(名護)", "久米島(久米島)",
					"大東島地方(南大東島)", "宮古島地方(宮古島)", "石垣島地方(石垣島)", "与那国島地方(与那国島)" };
			return entries;
		}
		return mentries;
	}

	public static int getAreaEntriesIdx(String obj) {
		if (obj.toString().equals("北海道")) {
			return 0;
		} else if (obj.toString().equals("青森県")) {
			return 1;
		} else if (obj.toString().equals("秋田県")) {
			return 2;
		} else if (obj.toString().equals("岩手県")) {
			return 3;
		} else if (obj.toString().equals("山形県")) {
			return 4;
		} else if (obj.toString().equals("宮城県")) {
			return 5;
		} else if (obj.toString().equals("福島県")) {
			return 6;
		} else if (obj.toString().equals("茨城県")) {
			return 7;
		} else if (obj.toString().equals("栃木県")) {
			return 8;
		} else if (obj.toString().equals("群馬県")) {
			return 9;
		} else if (obj.toString().equals("埼玉県")) {
			return 10;
		} else if (obj.toString().equals("東京都")) {
			return 11;
		} else if (obj.toString().equals("神奈川県")) {
			return 12;
		} else if (obj.toString().equals("千葉県")) {
			return 13;
		} else if (obj.toString().equals("静岡県")) {
			return 14;
		} else if (obj.toString().equals("山梨県")) {
			return 15;
		} else if (obj.toString().equals("新潟県")) {
			return 16;
		} else if (obj.toString().equals("長野県")) {
			return 17;
		} else if (obj.toString().equals("富山県")) {
			return 18;
		} else if (obj.toString().equals("石川県")) {
			return 19;
		} else if (obj.toString().equals("福井県")) {
			return 20;
		} else if (obj.toString().equals("岐阜県")) {
			return 21;
		} else if (obj.toString().equals("愛知県")) {
			return 22;
		} else if (obj.toString().equals("三重県")) {
			return 23;
		} else if (obj.toString().equals("滋賀県")) {
			return 24;
		} else if (obj.toString().equals("京都府")) {
			return 25;
		} else if (obj.toString().equals("大阪府")) {
			return 26;
		} else if (obj.toString().equals("奈良県")) {
			return 27;
		} else if (obj.toString().equals("和歌山県")) {
			return 28;
		} else if (obj.toString().equals("兵庫県")) {
			return 29;
		} else if (obj.toString().equals("岡山県")) {
			return 30;
		} else if (obj.toString().equals("鳥取県")) {
			return 31;
		} else if (obj.toString().equals("広島県")) {
			return 32;
		} else if (obj.toString().equals("島根県")) {
			return 33;
		} else if (obj.toString().equals("山口県")) {
			return 34;
		} else if (obj.toString().equals("香川県")) {
			return 35;
		} else if (obj.toString().equals("徳島県")) {
			return 36;
		} else if (obj.toString().equals("愛媛県")) {
			return 37;
		} else if (obj.toString().equals("高知県")) {
			return 38;
		} else if (obj.toString().equals("福岡県")) {
			return 39;
		} else if (obj.toString().equals("佐賀県")) {
			return 40;
		} else if (obj.toString().equals("長崎県")) {
			return 41;
		} else if (obj.toString().equals("大分県")) {
			return 42;
		} else if (obj.toString().equals("熊本県")) {
			return 43;
		} else if (obj.toString().equals("宮崎県")) {
			return 44;
		} else if (obj.toString().equals("鹿児島県")) {
			return 45;
		} else if (obj.toString().equals("沖縄県")) {
			return 46;
		}
		return 0;
	}
}
