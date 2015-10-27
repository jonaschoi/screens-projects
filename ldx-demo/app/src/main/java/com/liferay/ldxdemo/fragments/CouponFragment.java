package com.liferay.ldxdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;

/**
 * @author Javier Gamarra
 */
public class CouponFragment extends AbstractWebContentFragment {

//		setTitle(R.string.title_activity_coupon1);

	public static CouponFragment newInstance(Integer recordId, Integer recordSetId, int ddmStructureId) {
		Bundle args = new Bundle();
		args.putInt("recordId", recordId);
		args.putInt("recordSetId", recordSetId);
		args.putInt("ddmStructureId", ddmStructureId);
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