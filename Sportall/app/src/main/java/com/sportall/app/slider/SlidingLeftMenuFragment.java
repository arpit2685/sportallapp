package com.sportall.app.slider;




import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import com.sportall.app.main.R;
import com.sportall.app.main.SignIn;
import com.sportall.app.main.Sportapp;
import com.sportall.app.model.MenuItem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * This is the Fragment class used to show the Left Sliding Menu.
 * This will shows all the available options on Dashboard.
 * @author asus i5
 *
 */
public class SlidingLeftMenuFragment extends Fragment {
	
	Context mContext;
	String username_toshow;
	
	SharedPreferences setting_user;
	
	public static final String PREFS_NAME = "LoginPrefs";
	private ListView menuList = null;
	private TextView name;
	
	//private GoogleApiClient mGoogleApiClient;
		public GoogleApiClient mGoogleApiClient;
		private String personName;
	
	
	
	
	/**
	 * Create the view and bind it to activity.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.list, null);
		
		name = (TextView) view.findViewById(R.id.rec_username);
		/*setting_user = getActivity().getSharedPreferences(PREFS_UERNAME, 0);
		setting_user.getString("UserNamre", personName);*/
		name.setText("Hi");
		
		
		menuList = (ListView) view.findViewById(R.id.menuListview);
		menuList.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				
				if(id == 4){
					
					SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.remove("logged");
					editor.commit();
					
					
					Intent intent = new Intent(getActivity(), SignIn.class);
					intent.putExtra("user_logout", true);
					startActivity(intent);
					getActivity().finish();
						
					
					
					}
										
					//Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
					
					
			
			}
		});	
		return view;
	}

	/**
	 * Called when activity is created
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(Sportapp.menuItems != null){
			MenuAdapter adapter = new MenuAdapter(getActivity());
			for(MenuItem menuItem : Sportapp.menuItems)
				adapter.add(menuItem);
			menuList.setAdapter(adapter);
			
		}
		
	}

	
	/**
	 * This adapter is used to initialize and shoew the list data of Menu.
	 * @author asus i5
	 *
	 */
	public class MenuAdapter extends ArrayAdapter<MenuItem> {

		public MenuAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			/*ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);*/
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setTypeface(Sportapp.REGULAR_TYPEFACE);
			
			title.setText(getItem(position).tag);
			
			return convertView;
		}

	}
	
	
}
