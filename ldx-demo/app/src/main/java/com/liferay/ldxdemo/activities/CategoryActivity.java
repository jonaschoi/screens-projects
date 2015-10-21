package com.liferay.ldxdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;

/**
 * @author dipenp
 */
public class CategoryActivity extends Fragment {

	public static CategoryActivity newInstance() {
		return new CategoryActivity();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_category, container, false);
	}

//		setTitle(R.string.title_section1);
////		setTitle(listArray[position]);
}
