package com.liferay.ldxdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;

/**
 * @author Javier Gamarra
 */
public class ShoesActivity extends Fragment {

	public static ShoesActivity newInstance() {

		Bundle args = new Bundle();

		ShoesActivity fragment = new ShoesActivity();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_shoes, container, false);
	}

//		setTitle(R.string.title_section6);
//		setTitle(listArray[position]);

}
