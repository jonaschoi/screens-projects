package com.liferay.ldxdemo.activities;

import android.os.Bundle;

import com.liferay.ldxdemo.R;

/**
 * @author dipenp
 */
public class WomenActivity extends NavDrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.activity_women, frameLayout);

		/**
		 * Setting title and itemChecked  
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(R.string.title_section4);
//		setTitle(listArray[position]);

	}
}
