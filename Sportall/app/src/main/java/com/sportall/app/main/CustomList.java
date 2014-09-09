package com.sportall.app.main;

import com.sportall.app.main.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



		public class CustomList extends ArrayAdapter<String>{
		private final Activity context;
		String toadd;
		
		public CustomList(Activity context,
		
				String toadd) {
		
			super(context, R.layout.sport_row);
		
			this.context = context;
		
			
			
		}
		
			@SuppressLint({ "ViewHolder", "InflateParams" })
			@Override
			
			public View getView(int position, View view, ViewGroup parent) {
		
			LayoutInflater inflater = context.getLayoutInflater();
		
			View rowView= inflater.inflate(R.layout.sport_row, null, true);
		
			TextView tv_cooment = (TextView) rowView.findViewById(R.id.tv_sport);
			
			tv_cooment.setText(toadd);
		
			return rowView;
		
		}
		}