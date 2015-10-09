package com.liferay.ldxdemo.activities;

import android.os.Bundle;

import com.liferay.ldxdemo.R;

/**
 * @author dipenp
 *
 */
public class WalletActivity extends NavDrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 *  We will not use setContentView in this activty 
		 *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/
		
		/**
		 * Adding our layout to parent class frame layout.
		 */
		getLayoutInflater().inflate(R.layout.activity_wallet, frameLayout);
		
		/**
		 * Setting title and itemChecked  
		 */
		mDrawerList.setItemChecked(position, true);
		setTitle(listArray[position]);
		
		findViewById(R.id.image_view).setBackgroundResource(R.drawable.image1);
	}
}
