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
public class CouponActivity extends Fragment {

//		setTitle(R.string.title_activity_coupon1);

	public static CouponActivity newInstance(Integer recordId, Integer recordSetId, int ddmStructureId) {
		Bundle args = new Bundle();
		args.putInt("recordId", recordId);
		args.putInt("recordSetId", recordSetId);
		args.putInt("ddmStructureId", ddmStructureId);
		CouponActivity fragment = new CouponActivity();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_coupon, container, false);
	}

}