package com.liferay.ldxdemo.fragments;

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
public class CouponFragment extends Fragment {

	public static CouponFragment newInstance() {
		Bundle args = new Bundle();
		CouponFragment fragment = new CouponFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.content_coupon, container, false);
	}

}