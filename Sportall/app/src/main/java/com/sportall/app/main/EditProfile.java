package com.sportall.app.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class EditProfile extends FragmentActivity{
	private TextView tv_change_profile_pic;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.editprofile);
		
		tv_change_profile_pic = (TextView)findViewById(R.id.tv_changepicture);
		
		String terms_and_use="Change picture";
		SpannableString content = new SpannableString(terms_and_use);
		content.setSpan(new UnderlineSpan(), 0, terms_and_use.length(), 0);
		tv_change_profile_pic.setText(content);
		
		tv_change_profile_pic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

}
