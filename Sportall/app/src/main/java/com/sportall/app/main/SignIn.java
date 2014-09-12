package com.sportall.app.main;

import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.sportall.app.main.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class SignIn extends Activity implements OnClickListener,
		ConnectionCallbacks, OnConnectionFailedListener {

	private static final int RC_SIGN_IN = 0;
	// Logcat tag
	private static final String TAG = "MainActivity";

	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 400;

	// Google client to interact with Google API
	//private GoogleApiClient mGoogleApiClient;
	public GoogleApiClient mGoogleApiClient;

	
	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	public boolean mIntentInProgress;

	public boolean mSignInClicked;

	public ConnectionResult mConnectionResult;

	private SignInButton btnSignIn;
	private Button btnSignOut, btnRevokeAccess;
	private ImageView imgProfilePic;
	private TextView txtName, txtEmail;
	private LinearLayout llProfileLayout;
	
	public static final String PREFS_NAME = "LoginPrefs";
	public static final String PREFS_STATUS = "firsttimePrefs";
	public static final String PREF_GOOGLE = "google_pref";
	
	boolean savetoparseYesorNo;
	//SharedPreferences settings;
	SharedPreferences firsttimelogin;
	boolean clicklogout;
	
	String personPhotoUrl;
	String personName;
	String email;
	String location;
	String google_id;
	String sports;
	int gender;
	String genderType;
	
	private boolean googleLogout;
	private boolean firsttime = false;
	private ParseObject login_data;
	
	ProgressDialog progressDialogAll;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_signin);
		
		
		
		if(getIntent().getExtras() != null && getIntent().getExtras().containsKey("user_logout"))
			googleLogout = getIntent().getExtras().getBoolean("user_logout");
		
		/**
		 *  Check if we successfully logged in before. 
         * If we did, redirect to profile page
	     */
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		if (settings.getString("logged", "").toString().equals("logged")) {
			Intent intent = new Intent(SignIn.this, Profile.class);
			startActivity(intent);
			SignIn.this.finish();
		}
		 
		
		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
		btnSignOut = (Button) findViewById(R.id.btn_sign_out);
		btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
		imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
		txtName = (TextView) findViewById(R.id.txtName);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		llProfileLayout = (LinearLayout) findViewById(R.id.llProfile);

		// Button click listeners
		btnSignIn.setOnClickListener(this);
		btnSignOut.setOnClickListener(this);
		btnRevokeAccess.setOnClickListener(this);
		
		Log.v("LogOut Flag", "googleLogout "+googleLogout);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API, null)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		
		
	}

	
	public void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
		
	}

	public void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
			
		}
		
	}

	/**
	 * Method to resolve any signin errors
	 * */
	public void resolveSignInError() {
		
		try{
			if (mConnectionResult != null && mConnectionResult.hasResolution()) {
				try {
					mIntentInProgress = true;
					mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
				} catch (SendIntentException e) {
					mIntentInProgress = false;
					mGoogleApiClient.connect();
				}
			}
			else
				mGoogleApiClient.connect();
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}

	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				
				resolveSignInError();
			}
		}

	}

	@Override
	public void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mGoogleApiClient.connect();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void onConnected(Bundle arg0) {
		
		if(!googleLogout && mSignInClicked){
			mSignInClicked = false;
			Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
			
			SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor edt = settings.edit();
			edt.putString("logged", "logged");
			edt.commit();
			
			getProfileInformation();
	
			
			
		}
		else if(googleLogout){
			signOutFromGplus();
			mGoogleApiClient.disconnect();
			googleLogout = false;
		}

	}
	
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	public void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				personName = currentPerson.getDisplayName();
				personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				location = currentPerson.getCurrentLocation();
				gender = currentPerson.getGender();
				google_id = currentPerson.getId();
				
				if(gender ==0){
					genderType = "Male";
				}else {
					if(gender ==1){
						genderType = "Female";
					}else {
						genderType = "Other";
					}
				}
				
				SharedPreferences pref_google_id = getSharedPreferences(PREF_GOOGLE, 0);
				SharedPreferences.Editor edt_id = pref_google_id.edit();
				edt_id.putString("google_id",google_id);
				edt_id.commit();
				

				Log.e(TAG, "ID "+google_id+" Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl + ",Location:" + location +" "+"Gender:" +gender);


				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
				personPhotoUrl = personPhotoUrl.substring(0,
						personPhotoUrl.length() - 2)
						+ PROFILE_PIC_SIZE;
				
				//Save data in parse
				saveUserData();
				
				

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * If user information will retrive from the google+
	 * then check the g+id is available or not
	 * if not then store it to parse database else retrive all data
	 */
	private void saveUserData(){
		 Log.e(TAG, "query "+google_id);
		 ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
		 query.whereEqualTo("user_google_id", google_id.trim());
		 query.getFirstInBackground(new GetCallback<ParseObject>() {
		   public void done(ParseObject login_data, ParseException e) {
			  
			   
		     if (login_data == null)  {
		       Log.e("Data", "The getFirst request failed."+e.getCode());
		       
		       savetoparse();
		       openProfile();
		       
		     } else {
			    	
			    	String test_google_id = login_data.getString("user_google_id");
			    	Log.v("Check reterieved data", test_google_id);
			    	personPhotoUrl = login_data.getString("userimage");
			    	personName = login_data.getString("username");
			    	email = login_data.getString("useremail");
			    	location = login_data.getString("userloction");
			    	sports = login_data.getString("usersports");

			    	Log.e(TAG, "Location:" +location +"personname" +personName + 
			    			"email" + email + "image url" +personPhotoUrl + "user sports" +sports);
			    	openProfile();
		     }
		   }
		 });
	}
	
	/**
	 * Save to parse
	 */
	public void savetoparse(){
		
		
		 	 login_data = new ParseObject("UserData");
			login_data.put("user_google_id", google_id);
			login_data.put("useremail", email);
			login_data.put("userimage", personPhotoUrl);
			login_data.put("username",personName);
			
			if(location != null)
				login_data.put("userloction", location);
			else
				login_data.put("userloction", "");
			login_data.put("usersports","");
			login_data.put("usergender", genderType);
			login_data.saveInBackground();
	}
	
	/**
	 * 
	 * 
	 * @param mUserData
	 */
	private void openProfile(){
		 Intent intent = new Intent(SignIn.this,Profile.class);
			intent.putExtra("user_image_url", personPhotoUrl);
			intent.putExtra("user_name", personName);
			intent.putExtra("user_email", email);
			intent.putExtra("user_location", location);
			intent.putExtra("user_sports", sports);
			intent.putExtra("user_google_id", google_id);
			intent.putExtra("user_gender", genderType);
			
			progressDialogAll.dismiss();
			
			startActivity(intent);
			finish();
	}
	/**
	 * Updating the UI, showing/hiding buttons and profile layout
	 * */
	private void updateUI(boolean isSignedIn) {
		if (isSignedIn) {
			btnSignIn.setVisibility(View.GONE);
			btnSignOut.setVisibility(View.VISIBLE);
			btnRevokeAccess.setVisibility(View.VISIBLE);
			llProfileLayout.setVisibility(View.VISIBLE);
		} else {
			btnSignIn.setVisibility(View.VISIBLE);
			btnSignOut.setVisibility(View.GONE);
			btnRevokeAccess.setVisibility(View.GONE);
			llProfileLayout.setVisibility(View.GONE);
		}
	}

	

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
		updateUI(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Button on click listener
	 * */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sign_in:
			// Signin button clicked
			signInWithGplus();
			break;
		
		}
	}

	/**
	 * Sign-in into google
	 * */
	public void signInWithGplus() {
		progressDialogAll = new ProgressDialog(SignIn.this);
		progressDialogAll.setMessage("Loading...");
		progressDialogAll.setCancelable(false);
		progressDialogAll.show();
		
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
		
	}

	/**
	 * Sign-out from google
	 * */
	public void signOutFromGplus() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			 mGoogleApiClient.connect();
			
		}
	}
	

	/**
	 * Revoking access from google
	 * */
	public void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
					.setResultCallback(new ResultCallback<Status>() {
						@Override
						public void onResult(Status arg0) {
							Log.e(TAG, "User access revoked!");
							mGoogleApiClient.connect();
							updateUI(false);
						}

					});
		}
	}

}
