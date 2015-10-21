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
public class KidsActivity extends Fragment {

	public static KidsActivity newInstance() {

		Bundle args = new Bundle();

		KidsActivity fragment = new KidsActivity();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_kids, container, false);
	}

//		setTitle(R.string.title_section5);
//		setTitle(listArray[position]);
}
