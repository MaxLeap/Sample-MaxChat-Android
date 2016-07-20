package cn.maxleap.chatdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 专门访问和设置SharePreference的工具类, 保存和配置一些设置信息
 */
public class PrefUtils {

	private static final String SHARE_PREFS_NAME = "maxleap";

	public static void putBoolean(Context ctx, String key, boolean value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context ctx, String key,
			boolean defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		return pref.getBoolean(key, defaultValue);
	}

	public static void putString(Context ctx, String key, String value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putString(key, value).commit();
	}

	/**
	 *保存一个bitmap
	 * @param ctx
	 * @param key
	 * @param bitmap
     */
	public static void putBitmap(Context ctx, String key, Bitmap bitmap) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		//第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);

		//第二步:利用Base64将字节数组输出流中的数据转换成字符串String
		byte[] byteArray=byteArrayOutputStream.toByteArray();
		String imageString=new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
		//第三步:将String保持至SharedPreferences
		pref.edit().putString(key, imageString).commit();
	}






	public static String getString(Context ctx, String key, String defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		return pref.getString(key, defaultValue);
	}

	public static void putInt(Context ctx, String key, int value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putInt(key, value).commit();
	}

	public static int getInt(Context ctx, String key, int defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		return pref.getInt(key, defaultValue);
	}

	public static Bitmap getBitmap(Context ctx, String key) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		//第一步:取出字符串形式的Bitmap
		String imageString=pref.getString(key, "");
		//第二步:利用Base64将字符串转换为ByteArrayInputStream
		byte[] byteArray=Base64.decode(imageString, Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
		//第三步:利用ByteArrayInputStream生成Bitmap
		Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
		return bitmap;
	}





}
