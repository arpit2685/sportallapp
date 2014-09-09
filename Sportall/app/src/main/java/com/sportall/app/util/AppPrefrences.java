package com.sportall.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Class is used to store the shared preferences used inside the app.
 * @author Sony
 *
 */
public class AppPrefrences 
{
	public static final String Sportall_APP_PREF = "sportall_pref";
	public static final String LOGIN_MODE = "loginmode";
	public static final String NAME = "name";
	public static final String NA = "NA";
	public static final String ISFIRSTTIME = "isFirstTime";
	
	public static final int LOGOUT = 0;
	public static final int GOOGLE_PLUS_LOGIN = 2;
	
	
	
	private SharedPreferences pref = null;
	private SharedPreferences.Editor edit = null;
	
	
	/**
	 * Constructor
	 * @param context
	 */
	public AppPrefrences(Context context)
	{
		pref = context.getSharedPreferences(Sportall_APP_PREF, Activity.MODE_PRIVATE);
		edit = pref.edit();
	}
	/**
	 * Finally commit the prefrences
	 */
	public void commit()
	{
		edit.commit();
	}
	/**
	 * Store the string value
	 * @param key
	 * @param value
	 */
	public void putString(String key,String value)
	{
		edit.putString(key, value);
		
	}
	/**
	 * Store the integer value.
	 * @param key
	 * @param value
	 */
	public void putInteger(String key,int value)
	{
		edit.putInt(key, value);
	}
	/**
	 * Get the string value form preference
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key,String defaultValue)
	{
		return pref.getString(key, defaultValue);
	}
	/**
	 * Get the Inter value.
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInteger(String key,int defaultValue)
	{
		return pref.getInt(key, defaultValue);
	}
	
	/**
	 * Save the long value in preference.
	 * @param key
	 * @param value
	 */
	public void putLong(String key,long value){
		edit.putLong(key, value);
	}

	/**
	 * Get the long value from preference
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public long getLong(String key,long defaultVal){
		return pref.getLong(key, defaultVal);
	}
	
	/**
	 * Save the boolean value in preference.
	 * @param key
	 * @param value
	 */
	public void putBoolean(String key,boolean value){
		edit.putBoolean(key, value);
	}

	/**
	 * Get the boolean value from preference
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public boolean getBoolean(String key,boolean defaultVal){
		return pref.getBoolean(key, defaultVal);
	}
}
