package com.sportall.app.main;

import java.io.InputStream;
import com.sportall.app.views.CircularImageView;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView;

public class EditProfile extends FragmentActivity implements
AdapterView.OnItemSelectedListener {
	private TextView tv_change_profile_pic;
	String userName;
	String userEmail;
	String user_img_url;
	private EditText ed_username;
	private EditText ed_email;
	private static final int SELECT_PICTURE = 1;
	
	String[] items = { "Badminton", "Baseball", "Basketball", "Boxing", "Cricket", "Cycling",
			"Gymnastics","Handball","Hockey","Horse Racing","Ice Hockey","Motorsports",
			"Netball","Rowing","Rugby","Sailing","Shooting","Soccer","Softball","Surfing",
			"Swimming","Table Tennis","Tennis","Triathlon","Volleyball","Diving", "Equestrian",
			"Fencing", "list" };
	
	private CircularImageView userImageView;
	 private String selectedImagePath;


	@SuppressWarnings("rawtypes")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.editprofile);
		ed_username = (EditText)findViewById(R.id.userName);
		ed_email = (EditText)findViewById(R.id.user_email);
		
		
		// getting intent data
	    Intent in = getIntent();        
	    user_img_url = in.getStringExtra("user_image_url");
	    userName  = in.getStringExtra("user_name");
	    userEmail = in.getStringExtra("user_email");
	    userImageView = (CircularImageView)findViewById(R.id.user_image);
	    
	    Log.i("Image Url recieved ", user_img_url);
	    
	    Spinner spin = (Spinner)findViewById(R.id.spinner4);
	    spin.setOnItemSelectedListener(this);

		ArrayAdapter aa = new ArrayAdapter(
				this,
				android.R.layout.simple_spinner_item, 
				items);

		aa.setDropDownViewResource(
		   android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa);
	    
	    Log.i("User details got", "Username Recieved" + userName +" "+ "UserEmail Recieved" +userEmail);
	  
		tv_change_profile_pic = (TextView)findViewById(R.id.tv_changepicture);
		
		String terms_and_use="Change picture";
		SpannableString content = new SpannableString(terms_and_use);
		content.setSpan(new UnderlineSpan(), 0, terms_and_use.length(), 0);
		tv_change_profile_pic.setText(content);
		
		tv_change_profile_pic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
			}
		});
		
		setImage();
		
		
	}

	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                userImageView.setImageURI(selectedImageUri);
            }
        }
    }
 
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		
		Toast.makeText(getApplicationContext(), "items[position]", Toast.LENGTH_SHORT).show();
	}

	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	public void setImage(){
		Log.i("here i am", "setImageinvoked");
		
		//new LoadProfileImage(userImageView).execute(user_img_url);
		new DownloadImageTask(userImageView).execute(user_img_url);
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
	
}
