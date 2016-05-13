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
public class WomenFragment extends NamedFragment implements WebContentDisplayListener {

	public static String WEB_STYLE = "* {" +
			"box-sizing: border-box;" +
			"}" +
			"p {" +
			"margin-top: 0px;" +
			"margin-right: 0px;" +
			"margin-bottom: 10px;" +
			"margin-left: 0px;" +
			"}" +
			".jumbotron p {" +
			"margin-bottom: 15px;" +
			"font-size: 16px !important;" +
			"font-weight: 200 !important;" +
			"}"
			+ ".container-fluid-1280:before, .container-fluid-1280:after {\n" +
			"    content: \" \";" +
			"    display: table;" +
			"}" +
			"h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6 {" +
			"font-family: inherit !important;" +
			"font-weight: 500 !important;" +
			"line-height: 1.2 !important;" +
			"color: inherit;" +
			"}" +
			"h1, .h1, h2, .h2, h3, .h3 {" +
			"margin-top: 20px;" +
			"margin-bottom: 10px;" +
			"}" +
			"h1, .h1 {" +
			"font-size: 28px !important;" +
			"}" +
			"h2, .h2 {" +
			"font-size: 24px !important;" +
			"}" +
			".jumbotron h1, .jumbotron .h1 {" +
			"color: inherit;" +
			"}" +
			".jumbotron h2 {" +
			"font-size: 22px;" +
			"}" +
			".h1 {" +
			"font-size: 54px;" +
			"}" +
			".pull-right {" +
			"float: right;" +
			"}" +
			".text-right {" +
			"text-align: right;" +
			"}" +
			".pull-left {" +
			"float: left;" +
			"}" +
			".text-left {" +
			"text-align: left;" +
			"}" +
			".col-xs-1, .col-sm-1, .col-md-1, .col-lg-1, .col-xs-2, .col-sm-2, .col-md-2, .col-lg-2, .col-xs-3, .col-sm-3, .col-md-3, .col-lg-3, .col-xs-4, .col-sm-4, .col-md-4, .col-lg-4, .col-xs-5, .col-sm-5, .col-md-5, .col-lg-5, .col-xs-6, .col-sm-6, .col-md-6, .col-lg-6, .col-xs-7, .col-sm-7, .col-md-7, .col-lg-7, .col-xs-8, .col-sm-8, .col-md-8, .col-lg-8, .col-xs-9, .col-sm-9, .col-md-9, .col-lg-9, .col-xs-10, .col-sm-10, .col-md-10, .col-lg-10, .col-xs-11, .col-sm-11, .col-md-11, .col-lg-11, .col-xs-12, .col-sm-12, .col-md-12, .col-lg-12 {" +
			"position: relative;" +
			"min-height: 1px;" +
			"padding-left: 15px;" +
			"padding-right: 15px;" +
			"}" +
			"article, aside, details, figcaption, figure, footer, header, hgroup, main, menu, nav, section, summary {" +
			"display: block;" +
			"}" +
			".container-fluid-1280 {" +
			"margin-left: auto;" +
			"margin-right: auto;" +
			"max-width: 1280px;" +
			"padding-left: 15px;" +
			"padding-right: 15px;" +
			"}" +
			".container-fluid-1280::after {" +
			"clear: both;" +
			"}" +
			".jumbotron {" +
			"padding-top: 30px;" +
			"padding-bottom: 30px;" +
			"margin-bottom: 0px;" +
			"color: inherit;" +
			"background-color: transparent;" +
			"}" +
			".main-background-image {" +
			"background-position-x: 50%;" +
			"background-position-y: 0%;" +
			"background-repeat-x: no-repeat;" +
			"background-repeat-y: no-repeat;" +
			"background-size: cover;" +
			"color: rgb(255, 255, 255);" +
			"padding-bottom: 95px;" +
			"padding-top: 95px;" +
			"}" +
			".MobileCSS {padding: 0% !important; width: 100% !important; } " +
			".MobileCSS img { width: 100% !important; } ";

	public static WomenFragment newInstance() {
		return new WomenFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_women, container, false);

		WebContentDisplayScreenlet webContentDisplayScreenlet = (WebContentDisplayScreenlet) view.findViewById(R.id.web_women);
		webContentDisplayScreenlet.setListener(this);

		return view;
	}

	@Override
	public String getName() {
		return "Women";
	}

	@Override
	public WebContent onWebContentReceived(WebContentDisplayScreenlet source, WebContent webContent) {
		webContent.setHtml("<div id=\"scoped-content\"><style type = \"text/css\" scoped>" + WEB_STYLE + "</style>" + webContent.getHtml() + "</div>");
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
