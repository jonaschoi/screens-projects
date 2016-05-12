package com.liferay.ldxdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.liferay.ldxdemo.R;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;

/**
 * @author Javier Gamarra
 */
public class MenFragment extends NamedFragment implements WebContentDisplayListener {

	public static MenFragment newInstance() {
		return new MenFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_men, container, false);

		WebContentDisplayScreenlet webContentDisplayScreenlet = (WebContentDisplayScreenlet) view.findViewById(R.id.web_men);
		webContentDisplayScreenlet.setListener(this);
		return view;
	}

	@Override
	public String getName() {
		return "Men";
	}

	@Override
	public WebContent onWebContentReceived(WebContentDisplayScreenlet source, WebContent webContent) {
		webContent.setHtml("<div id=\"scoped-content\"><style type = \"text/css\" scoped>" + WomenFragment.WEB_STYLE + "</style>" + webContent.getHtml() + "</div>");
		return webContent;
	}

	@Override
	public void onWebContentFailure(WebContentDisplayScreenlet source, Exception e) {

	}

	@Override
	public void onWebContentClicked(WebView.HitTestResult result, MotionEvent event) {

	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}
}
