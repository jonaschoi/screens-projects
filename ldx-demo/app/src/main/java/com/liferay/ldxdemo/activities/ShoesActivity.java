package com.liferay.ldxdemo.activities;

import android.os.Bundle;

import com.liferay.ldxdemo.R;

/**
 * @author dipenp
 */
public class ShoesActivity extends NavDrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.activity_shoes, frameLayout);

		/**
		 * Setting title and itemChecked  
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(R.string.title_section6);
//		setTitle(listArray[position]);

	}
}
