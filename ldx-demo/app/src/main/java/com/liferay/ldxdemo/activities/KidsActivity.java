package com.liferay.ldxdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;

/**
 * @author Javier Gamarra
 */
public class KidsActivity extends Fragment implements View.OnTouchListener {

	private static final float MIN_DISTANCE = 200f;
	private float x1;
	private float x2;

	public static KidsActivity newInstance() {

		Bundle args = new Bundle();

		KidsActivity fragment = new KidsActivity();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_kids, container, false);

		(view.findViewById(R.id.liferay_webview)).setOnTouchListener(this);

		return view;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x1 = event.getX();
				break;
			case MotionEvent.ACTION_UP:
				x2 = event.getX();
				float deltaX = x2 - x1;
				if (Math.abs(deltaX) > MIN_DISTANCE) {
					getFragmentManager().
							beginTransaction().
							replace(R.id.content_frame, ShoesActivity.newInstance()).
							addToBackStack(null).
							commit();
				}
				break;
		}
		return false;
	}

//		setTitle(R.string.title_section5);
//		setTitle(listArray[position]);
}
