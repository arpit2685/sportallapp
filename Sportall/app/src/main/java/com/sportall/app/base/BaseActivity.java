package com.sportall.app.base;

import com.sportall.app.main.R;
import com.sportall.app.slider.SlidingFragmentActivity;
import com.sportall.app.slider.SlidingLeftMenuFragment;
import com.sportall.app.slider.SlidingMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This is the Base Activity for implementing Slider 
 * in every Activity.
 * @author asus i5
 *
 */
public abstract class BaseActivity extends SlidingFragmentActivity {
	 
	//protected abstract void performSearch(String tosearch);
	 
	private SlidingMenu sm = null;
	/**
	 * OnCreate is used to initialize the view.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		try {

			// set the Behind View - Left
			setBehindContentView(R.layout.menu_frame);
			Fragment mFrag = null;
			if (savedInstanceState == null) {
				FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
				mFrag = new SlidingLeftMenuFragment();
				t.replace(R.id.menu_frame, mFrag);
				t.commit();
			} else {
				mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
			}

			// customize the SlidingMenu
			sm = getSlidingMenu();
			sm.setMode(SlidingMenu.LEFT);
			sm.setShadowWidthRes(R.dimen.shadow_width);
			sm.setShadowDrawable(R.drawable.shadow);
			sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
			sm.setFadeDegree(0.35f);
			sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set The content View
	 */
	@Override
	public void setContentView(int id) {
		super.setContentView(id);
		
		
	}
	/**
	 * Handle the click event of Menu
	 * @param view
	 */
	public void showSlidingMenu(View view){
		sm.showMenu();
	}

		
	
		
		
	
}
