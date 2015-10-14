package com.liferay.ldxdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liferay.ldxdemo.R;

public class CouponActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon);
//		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//		setSupportActionBar(toolbar);

//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(R.string.title_activity_coupon1);
	}

}