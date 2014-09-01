package com.sportall.app.main;

import java.io.InputStream;
import com.sportall.app.view.CircularImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author ljkj
 *
 */
public class Profile extends FragmentActivity{

	private CircularImageView userImageView;
	private TextView tv_userName;
	private TextView tv_user_email;
	String user_img_url;
	private ArrayAdapter<String> listAdapter ;  
	private Spinner spinner_country;
	private ImageView btn_editProfile;
	private ListView sports_list;
	String userName;
	String userEmail;
	
	public String[] web = {			
			 "Badminton",
			 "Baseball",
			 "Cricket",
			 "Hockey",
			 "Table Tennis",
			 "Soccer",
			 "Tennis",
			 "Surfing" 
			  } ;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.profile);
	
	/**
	 * importing widgets from the layout
	 */
	userImageView = (CircularImageView)findViewById(R.id.user_image);
	tv_userName = (TextView)findViewById(R.id.userName);
	tv_user_email = (TextView)findViewById(R.id.user_email);
	btn_editProfile = (ImageView)findViewById(R.id.btn_edit);
	sports_list = (ListView)findViewById(R.id.lv_sport);
	
	/**
	 * CustomList class for showing the list data of Sport.
	 */
	CustomList adapter = new
	        CustomList(Profile.this, web);

	sports_list.setAdapter(adapter);
	sports_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                
            }
        });
	
	
	/**
	 * Getting the data from the signIn class, containing the user image URL, user name, user email.
	 */
	
    Intent in = getIntent();        
    user_img_url = in.getStringExtra("user_image_url");
    userName  = in.getStringExtra("user_name");
    userEmail = in.getStringExtra("user_email");
    
    Log.i("Image Url recieved ", user_img_url);
    
    Log.i("User details got", "Username Recieved" + userName +" "+ "UserEmail Recieved" +userEmail);
    tv_userName.setText(userName);
    tv_user_email.setText(userEmail);
    
    btn_editProfile.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Profile.this, EditProfile.class);
			intent.putExtra("user_image_url", user_img_url);
			intent.putExtra("user_name", userName);
			intent.putExtra("user_email", userEmail);
			startActivity(intent);
			finish();
		}
	});
	
    
    setImage();
    
   
	
	}
	
	/**
	 * setImage method to invoke DownloadImageTask to get an image from received URL and display in the view 
	 */
	public void setImage(){
		Log.i("here i am", "setImageinvoked");
		
		//new LoadProfileImage(userImageView).execute(user_img_url);
		new DownloadImageTask(userImageView).execute(user_img_url);
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
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}
	
	
	public void editprofile(View v){
		Intent intent = new Intent(Profile.this,EditProfile.class);
		intent.putExtra("user_image_url", user_img_url);
		intent.putExtra("user_name", userName);
		intent.putExtra("user_email", userEmail);
		startActivity(intent);
		finish();
		
	}
	
	
}
