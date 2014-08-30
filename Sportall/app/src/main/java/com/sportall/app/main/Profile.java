package com.sportall.app.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sportall.app.views.CircularImageView;

import java.io.InputStream;

public class Profile extends FragmentActivity{

	// Profile pic image size in pixels
		private static final int PROFILE_PIC_SIZE = 400;
	String user_img_url;
	private CircularImageView userImageView;
	private TextView tv_userName;
	private TextView tv_user_email;
	private ImageButton btn_logout;
	
	
	private String selectedCountry = null;
    private String selectedAnimal = null;
	private Spinner spinner_country;
	private ImageView btn_editProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.profile);
	
	
	userImageView = (CircularImageView)findViewById(R.id.user_image);
	tv_userName = (TextView)findViewById(R.id.userName);
	tv_user_email = (TextView)findViewById(R.id.user_email);
	btn_editProfile = (ImageView)findViewById(R.id.btn_edit);
	
	/*btn_editProfile.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {

			Intent intent = new Intent(Profile.this,EditProfile.class);
			startActivity(intent);
			finish();
		}
	});*/
	
	//btn_logout = (ImageButton)findViewById(R.id.logout);
	
	 //get reference to the spinner from the XML layout
	//spinner_country = (Spinner) findViewById(R.id.spinnerstate);
	
    
	/*//attach the listener to the spinner
	spinner_country.setOnItemSelectedListener(new MyOnItemSelectedListener());

    //Dynamically generate a spinner data 
    createSpinnerDropDown();*/
	
	/*btn_logout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Profile.this,SignIn.class);
			startActivity(intent);
			finish();
			SignIn objSignIn = new SignIn();
			objSignIn.signOutFromGplus();
			
		}
	});*/
	
	// getting intent data
    Intent in = getIntent();        
    user_img_url = in.getStringExtra("user_image_url");
    String userName  = in.getStringExtra("user_name");
    String userEmail = in.getStringExtra("user_email");
    
    Log.i("Image Url recieved ", user_img_url);
    
    Log.i("User details got", "Username Recieved" + userName +" "+ "UserEmail Recieved" +userEmail);
    tv_userName.setText(userName);
    tv_user_email.setText(userEmail);
    
    setImage();
    
   
	
	}
	
	public void setImage(){
		Log.i("here i am", "setImageinvoked");
		
		//new LoadProfileImage(userImageView).execute(user_img_url);
		new DownloadImageTask(userImageView).execute(user_img_url);
	}
	
	public void contactus(View v){
		Intent intent = new Intent(Profile.this,EditProfile.class);
		startActivity(intent);
		finish();


	}
	
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
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}
	/*//Add animals into spinner dynamically
    private void createSpinnerDropDown() {
 
        //get reference to the spinner from the XML layout
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        
        //Array list of animals to display in the spinner
        List<String> list = new ArrayList<String>();
        list.add("Bear");
        list.add("Camel");
        list.add("Cat");
        list.add("Cat");
        list.add("Deer");
        list.add("Dog");
        list.add("Goat");
        list.add("Horse");
        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        //set the view for the Drop down list
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the ArrayAdapter to the spinner
        spinner.setAdapter(dataAdapter);
        //attach the listener to the spinner
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
    }
 
    public class MyOnItemSelectedListener implements OnItemSelectedListener {
 
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            
            String selectedItem = parent.getItemAtPosition(pos).toString();
            
            //check which spinner triggered the listener
            switch (parent.getId()) {
            //country spinner
            case R.id.spinner:
                //make sure the country was already selected during the onCreate
                if(selectedCountry != null){
                    Toast.makeText(parent.getContext(), "Country you selected is " + selectedItem,
                    Toast.LENGTH_LONG).show();
                }
                selectedCountry = selectedItem;
                break;
            //animal spinner   
            case R.id.spinner1:
                //make sure the animal was already selected during the onCreate
                if(selectedAnimal != null){
                    Toast.makeText(parent.getContext(), "Animal selected is " + selectedItem,
                    Toast.LENGTH_LONG).show();
                }   
                selectedAnimal = selectedItem;
                break;
            }
 
            
        }
 
        public void onNothingSelected(AdapterView<?> parent) {
            // Do nothing.
        }

		
    }*/
	
}
