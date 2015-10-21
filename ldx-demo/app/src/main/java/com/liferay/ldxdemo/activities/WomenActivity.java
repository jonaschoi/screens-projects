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
public class WomenActivity extends Fragment {

	public static WomenActivity newInstance() {

		Bundle args = new Bundle();

		WomenActivity fragment = new WomenActivity();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_women, container, false);
	}

//		setTitle(R.string.title_section4);
////		setTitle(listArray[position]);
}
