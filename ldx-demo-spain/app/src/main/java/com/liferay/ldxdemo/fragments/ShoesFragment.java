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
public class ShoesFragment extends NamedFragment {

	public static ShoesFragment newInstance() {
		return new ShoesFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.content_shoes, container, false);
	}

	@Override
	public String getName() {
		return "Shoes";
	}
}
