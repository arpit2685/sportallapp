package com.sportall.app.main;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sportall.app.base.BaseActivity;
import com.sportall.app.views.CircularImageView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/**
 * 
 * @author ljkj
 *
 */
public class Profile extends BaseActivity{

	private CircularImageView userImageView;
	private TextView tv_userName;
	private TextView tv_user_email;
	private ImageView btn_saveProfile;
	private ListView sports_list;
	
	EditText ed_city , ed_state , ed_country;
	
	String[] items;
	String[] selectedSports_array;
	private HashMap<String, Boolean> spHashMap = null;
    private boolean [] checkedItems = null;
    
    ArrayList<String> selected_sport_list;
    
    ArrayList<String> user_sports_from_parse;
    
    
	//Intent String
	private String user_img_url;
	private String userName;
	private String userEmail;
	private String userLocation;
	private String userSports;
	private String completelocation;
	private String userGender;
	
	//Edittext strings
	private String User_city;
	private String User_state;
	private String User_country;
	

	

	private TextView tv_addsports;
	
	public String selectedSport = "" ;
	
    AlertDialog dialog;
	
    private String selectedSportfromarray = "";
    
    public static final String PREF_GOOGLE = "google_pref";
    public static final String PREF_UERNAME = "username_pref";
	
    String google_id_from_preference;
    
    ProgressDialog progressDialogAll;
	private TextView tv_userGender;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_profile);
		
		progressDialogAll = new ProgressDialog(Profile.this);
		progressDialogAll.setMessage("Loading...");
		progressDialogAll.setCancelable(false);
		progressDialogAll.show();

		selected_sport_list = new ArrayList<String>();
		
		user_sports_from_parse = new ArrayList<String>();
		
		 SharedPreferences pref_google_id = getSharedPreferences(PREF_GOOGLE, 0);
		 google_id_from_preference = pref_google_id.getString("google_id", "");
		 Log.v("id from pref", google_id_from_preference);
		
		initViews();
		
		
		
	}
	
	/**
	 * Initialize the Views
	 */
	private void initViews(){
		
		/**
		 * importing widgets from the layout
		 */
		userImageView = (CircularImageView)findViewById(R.id.user_image);
		tv_userName = (TextView)findViewById(R.id.userName);
		tv_user_email = (TextView)findViewById(R.id.user_email);
		btn_saveProfile = (ImageView)findViewById(R.id.btn_save);
		sports_list = (ListView)findViewById(R.id.lv_sport);
		ed_city = (EditText)findViewById(R.id.city);
		ed_state = (EditText)findViewById(R.id.state);
		ed_country = (EditText)findViewById(R.id.country);
		tv_addsports = (TextView)findViewById(R.id.tv_sportsadd);
		tv_userGender = (TextView)findViewById(R.id.userGender);
		
		spHashMap = new HashMap<String,Boolean>();
		
		ed_city.setOnEditorActionListener(new OnEditorActionListener() {
	       

			@Override
			  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
	                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	                in.hideSoftInputFromWindow(ed_city.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
	            }
	            return false;
			}
	    });
		
		
		
	    tv_addsports.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					showSportsList();
			    }
			});
	    	
		
	    btn_saveProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				updateUserData();
			}
		});
	    
	    //Getting user Detail from parse every time
	   userDetailfromParse();
	    
	    
	}
	
	
	
	/**
	 * Getting User Data from parse
	 */

	public void userDetailfromParse(){
		
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
		 query.whereEqualTo("user_google_id", google_id_from_preference.trim());
		
		 
		 query.getFirstInBackground(new GetCallback<ParseObject>() {
		   

		public void done(final ParseObject login_data, ParseException e) {
		     if (login_data == null) {
		       Log.d("Data", "The getFirst request failed. in profile"+e.getCode());
		       
		       try{
		    	   Intent in = getIntent();        
				    user_img_url = in.getStringExtra("user_image_url");
				    userName  = in.getStringExtra("user_name");
				    
				    SharedPreferences usernameSlider = getSharedPreferences(PREF_UERNAME, 0);
					SharedPreferences.Editor edt = usernameSlider.edit();
					edt.putString("userName", userName);
					edt.commit();
					
				    userEmail = in.getStringExtra("user_email");
				    userLocation = in.getStringExtra("user_location");
				    userSports = in.getStringExtra("user_sports");
				    userGender = in.getStringExtra("user_gender");
		       }catch(Exception ee){
		    	   ee.printStackTrace();
		       }
		       
		       
		     } else {
		    	  userEmail = login_data.getString("useremail");
		    	  userName = login_data.getString("username");
		    	  

		 		SharedPreferences usernameSlider = getSharedPreferences(PREF_UERNAME, 0);
				SharedPreferences.Editor edt = usernameSlider.edit();
				edt.putString("userName", userName);
				edt.commit();
		    	  
		    	  user_img_url = login_data.getString("userimage");
		    	  userLocation = login_data.getString("userloction");
		    	  userSports = login_data.getString("usersports");
		    	  userGender = login_data.getString("usergender");
		    	 
		     }
		     
		     if(userName != null)
			    	tv_userName.setText(userName);
			    //Log.v("from parse", parse_name);
			    if(userEmail != null)
			    	tv_user_email.setText(userEmail);
			   // Log.v("from parse",parse_email);
			    
			    tv_userGender.setText(userGender);
			    
			    //Set user location if available
			    setLocation();
			    
			  //Set user sports if available
			    setUserSports();
			    
			    new DownloadImageTask(userImageView).execute(user_img_url);
			    
			    fetchSports();
			 progressDialogAll.dismiss();
		   }
		
		
		 });
		
	}
	/**
	 * Getting Location from the Parse
	 */
	private void setLocation(){
		
		if(userLocation != null && userLocation.length() > 0){
			 String [] parselocation_array = userLocation.split(",");
			 if(parselocation_array != null && parselocation_array.length > 0){
				 String City_rec = parselocation_array[0];
		    	 String State_rec = parselocation_array[1];
		    	 String Country_rec = parselocation_array[2];
		    	 
		    	 ed_city.setText(City_rec);
		    	 ed_state.setText(State_rec);
		    	 ed_country.setText(Country_rec);
			 }
	    	
		}
		
		if(userSports != null && userSports.length() > 0){
			String [] sports = userSports.split(",");
			if(sports != null){
				for(int index = 0;index < sports.length;index++){
					spHashMap.put(sports[index], true);
				}
			}
		}
	}
	
	public void fetchSports(){
		  ParseQuery<ParseObject> query = ParseQuery.getQuery("UserSports");
		try {
			List<ParseObject> spList = query.find();
			if(spList != null && spList.size() > 0){
				items = new String[spList.size()];
				checkedItems = new boolean[spList.size()];
				for(int index = 0;index < spList.size();index++){
					items[index] = spList.get(index).getString("usersports");
					Log.v("Sports", items[index]);
					checkedItems[index] = spHashMap.containsKey(items[index]);
				}
			} 
			
		
		   
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Log.e("SP List", ""+spHashMap.toString());
	}
	
	
	/**
	 * set User Sport
	 * 
	 */
	
	public void setUserSports(){
		if(userSports != null && userSports.length() > 0){
			Log.v("i am here", "in if condition true");
			String [] parsesports_array = userSports.split(",");
			 if(parsesports_array != null && parsesports_array.length > 0){
				 Log.v("i am here", "in if checking array");
				 Log.v("i am here", ""+parsesports_array.length);
				 
				 int size = parsesports_array.length;
				 user_sports_from_parse.clear();
				 
				 for(int index=0; index<size; index++){
					 
					 selectedSportfromarray =  selectedSportfromarray + parsesports_array[index];
					 
					 user_sports_from_parse.add(parsesports_array[index]);
				 }
				 
				 populateSportList_fromparse();
			 }
		}
	}
	
	
	/**
	 * 
	 * Custom Adapter
	 *
	 */
	
	public class CustomList extends ArrayAdapter<String>{
		
		
		public CustomList(Context context, int resource, List<String> objects) {
			super(context, resource, objects);
			for(int i=0; i<objects.size();i++){
        		
        		Log.d("arraylist items in adapter", objects.get(i));
        	}
		}

			@SuppressLint({ "ViewHolder", "InflateParams" })
			@Override
			
			public View getView(int position, View view, ViewGroup parent) {
		
		
			View rowView= getLayoutInflater().inflate(R.layout.row, null);
		
			TextView tv_sport = (TextView) rowView.findViewById(R.id.row_title);
			Log.d("Item at pos "+position, selected_sport_list.get(position));
			tv_sport.setText(selected_sport_list.get(position));
		
			return rowView;
		
		}
	}
	
	
	
	/**
	 * Custom Adapter for Sports list
	 */
	
	public class SportsList extends ArrayAdapter<String>{
		
		
		public SportsList(Context context, int resource, List<String> objects) {
			super(context, resource, objects);
			for(int i=0; i<objects.size();i++){
        		
        		Log.d("arraylist items in adapter", objects.get(i));
        	}
        		
		}

			@SuppressLint({ "ViewHolder", "InflateParams" })
			@Override
			
			public View getView(int position, View view, ViewGroup parent) {
		
			View rowView= getLayoutInflater().inflate(R.layout.row, null);
		
			TextView tv_sport = (TextView) rowView.findViewById(R.id.row_title);
			Log.d("Item at pos "+position, user_sports_from_parse.get(position));
			tv_sport.setText(user_sports_from_parse.get(position));
		
			return rowView;
		
		}
		}
	
	
	
	
	/**
	 * populating sport list from parse if sports available in  parse
	 */
	private void populateSportList_fromparse() {
		 Log.v("i am here", "in populateSportList_fromparse");
		

		for(int i=0; i<user_sports_from_parse.size();i++){
			Log.d("count array list items", ""+user_sports_from_parse.size());
			Log.d("arraylist items before adapter", user_sports_from_parse.get(i));
		}
			
		SportsList sport_list_adapter = new SportsList(this, R.layout.row, user_sports_from_parse);
			        
			           sports_list.setAdapter(sport_list_adapter);
		
	}

	/**
	 * Show the Sports List
	 */
	private void showSportsList(){
		AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
    	builder.setTitle("Sports List");
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            	selected_sport_list.clear();
            	int size = checkedItems.length;
            	for(int index = 0;index < size; index++){            		
            		if(checkedItems[index]){
            			selectedSport =  selectedSport + items[index]+",";	  
            		    selected_sport_list.add(items[index]);
            		}
            	}
            	Log.v("Selected Sports", selectedSport); 
            	populateSportList();
            }
        });
    	
    	builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				checkedItems[which] = isChecked;
				
			}
		});
    	builder.show();
	}
	
	
	/**
	 * Populating Sports List
	 */
	public void populateSportList(){
		
		Log.d("arraylist items size", ""+selected_sport_list.size());
		for(int i=0; i<selected_sport_list.size();i++){
			
			Log.d("arraylist items before adapter", selected_sport_list.get(i));
		}
			
		CustomList sport_list_adapter = new CustomList(this, R.layout.row, selected_sport_list);
			       
			           sports_list.setAdapter(sport_list_adapter);
	}
	
	
	
	/**
	 * Update user data on clicking Save Button
	 */
	
	@SuppressWarnings("deprecation")
	private void updateUserData(){
		
		
		 User_city = ed_city.getText().toString().trim();
		 User_state = ed_state.getText().toString().trim();
		 User_country = ed_country.getText().toString().trim();
		completelocation = User_city+","+User_state+","+User_country;
		Log.v("Userdetail feeded", User_city+" "+User_state+" "+User_country);

		if(User_city.trim().length()<0 && User_state.trim().length()<0 && User_country.trim().length()<0){
			Toast.makeText(getApplicationContext(), "Please Enter Location", Toast.LENGTH_SHORT).show();
		}			
		else{
			//updateToParse();
			new SaveUserDataToParse(Profile.this).execute("update userdata");
		}
	}
	
	
	/**
	 * DownloadImageTask to get image from image URL
	 * @author ljkj
	 *
	 */
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				//Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
	
	
	
	/**
	 * Save User Data on Click of Save Button Task
	 */
	
	public class SaveUserDataToParse extends AsyncTask<String, String, String> {
		private Context context;
		private ProgressDialog progressDialog;

		public SaveUserDataToParse(Context context) {
		    this.context = context;
		}

		@Override
		protected void onPreExecute() {
		    progressDialog = new ProgressDialog(context);
		    progressDialog.setMessage("Loading...");
		    progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
		    //Do your loading here
			
			
			 ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
			 query.whereEqualTo("user_google_id", google_id_from_preference.trim());
			
			 
			 query.getFirstInBackground(new GetCallback<ParseObject>() {
			   public void done(final ParseObject login_data, ParseException e) {
			     if (login_data == null) {
			       
			     } else {
			    	  login_data.put("userloction", completelocation);
			    	  
			    	 
			    	  if(!selected_sport_list.isEmpty()){
			    		  String completeSports = null;
			    		  for(int i=0; i<selected_sport_list.size();i++){
			    			  
			    			  if(completeSports != null)
			    				  completeSports = completeSports + selected_sport_list.get(i)+",";
			    			  else
			    				  completeSports =  selected_sport_list.get(i)+",";
			    		  }
			    			  
			    		  login_data.put("usersports",completeSports );
			  		  }
			    	  
			    	  login_data.saveInBackground();
			     }
			   }
			 });
			
		    return "finish";
		}


		@Override
		protected void onPostExecute(String result) {
		    progressDialog.dismiss();
		    
		    Toast.makeText(getApplicationContext(), "Your Data has been successfully Saved", Toast.LENGTH_SHORT).show();
		   
		}
	}
	
	
	public void showinAlert(){
		
		
	}
	
}
