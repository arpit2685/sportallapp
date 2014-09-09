package com.sportall.app.main;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import com.sportall.app.model.MenuItem;



import android.app.Application;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
/**
 * This is the application class hold the static data of the app.
 * @author asus i5
 *
 */
public class Sportapp extends Application {
	
	

	private String YOUR_APPLICATION_ID = "oXvtWggSpYRb9rX9aDEQDEEUuG7Kaq2uFrnEV26t";
	private String YOUR_CLIENT_KEY = "UM38AP5BB1o3mAN7tLGW7Z7VGAnfwMScflIuIeqZ";
	
	
	//Is Logcat Allow Flag
	public static final boolean isLogcatAllow = true;
	
	//Type Face
	public static Typeface LIGHT_TYPEFACE = null;
	public static Typeface REGULAR_TYPEFACE = null;
	public static Typeface BOLD_TYPEFACE = null;
	
	public static List<MenuItem> menuItems = null;
	
	
	/**
	 * Called when the application is starting, before any activity, service, 
	 * or receiver objects (excluding content providers) have been created. 
	 * Implementations should be as quick as possible (for example using lazy initialization of state) 
	 * since the time spent in this function directly impacts the performance of starting the first activity, 
	 * service, or receiver in a process. If you override this method, be sure to call super.onCreate().
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, YOUR_APPLICATION_ID , YOUR_CLIENT_KEY );


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		
		try {
			
			initializeMenuItems();
			
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Initialize the List of Menu Items
	 */
	private void initializeMenuItems(){
		
		//Add the menu and Its icon
		menuItems = new ArrayList<MenuItem>();
		
		menuItems.add(new MenuItem(getResources().getString(R.string.profile), R.drawable.signout));
		menuItems.add(new MenuItem(getResources().getString(R.string.search_athlete), R.drawable.signout));
		menuItems.add(new MenuItem(getResources().getString(R.string.send_invitation), R.drawable.signout));
		menuItems.add(new MenuItem(getResources().getString(R.string.invites), R.drawable.signout));
		menuItems.add(new MenuItem(getResources().getString(R.string.sign_out), R.drawable.signout));
		
	}
	
	
	
}
