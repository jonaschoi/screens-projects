package com.liferay.ldxdemo.activities;

import android.os.Bundle;

import com.liferay.ldxdemo.R;

/**
 * @author dipenp
 *
 */
public class CategoryActivity extends NavDrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.activity_category, frameLayout);

		/**
		 * Setting title and itemChecked
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(R.string.title_section1);
//		setTitle(listArray[position]);

//		findViewById(R.id.image_view).setBackgroundResource(R.drawable.image1);
	}
}
