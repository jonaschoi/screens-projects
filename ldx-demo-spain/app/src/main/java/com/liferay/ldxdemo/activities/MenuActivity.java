package com.liferay.ldxdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.fragments.CategoryFragment;
import com.liferay.ldxdemo.fragments.KidsFragment;
import com.liferay.ldxdemo.fragments.MenFragment;
import com.liferay.ldxdemo.fragments.NamedFragment;
import com.liferay.ldxdemo.fragments.ReviewFragment;
import com.liferay.ldxdemo.fragments.ShoesFragment;
import com.liferay.ldxdemo.fragments.WalletFragment;
import com.liferay.ldxdemo.fragments.WomenFragment;
import com.liferay.ldxdemo.notification.PushService;
import com.liferay.ldxdemo.notification.SnackbarUtil;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.push.PushScreensActivity;

import org.json.JSONObject;

public class MenuActivity extends PushScreensActivity
		implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

	private int fragmentId;
	private DrawerLayout drawer;
	private NavigationView navigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		fragmentId = getIntent().getIntExtra("fragmentId", 0);
		//navigationView.getMenu().getItem(fragmentId).setChecked(true);
	}

	@Override
	protected void onResume() {
		super.onResume();

		inflateFragmentAtPosition(fragmentId);
		loadPortrait();
	}

	public void loadPortrait() {
		User user = SessionContext.getCurrentUser();

		View headerView = navigationView.getHeaderView(0);

		if (SessionContext.isLoggedIn()) {
			String text = user.getFirstName() + " " + user.getLastName();
			((TextView) headerView.findViewById(R.id.logged_user)).setText(text);
			headerView.findViewById(R.id.liferay_portrait).setOnClickListener(this);
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.profile) {
			startActivity(new Intent(this, ProfileActivity.class));
		} else {
			inflateFragmentAtPosition(id);
		}

		return true;
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, ProfileActivity.class));
	}

	@Override
	public void onBackPressed() {
		if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt("fragmentId", fragmentId);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		fragmentId = savedInstanceState.getInt("fragmentId");
	}

	@Override
	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	@Override
	protected void onPushNotificationReceived(final JSONObject jsonObject) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				DDLListScreenlet ddLList = (DDLListScreenlet) findViewById(R.id.wallet_default);
				if (ddLList != null) {
					SnackbarUtil.showMessage(MenuActivity.this, "Reloading list...");
					ddLList.loadPage(0);
				}
			}
		});
	}

	@Override
	protected void onErrorRegisteringPush(final String message, final Exception e) {
	}

	@Override
	protected String getSenderId() {
		return getString(R.string.sender_id);
	}

	private NamedFragment getFragmentToRender(int position) {
		switch (position) {
			case R.id.wallet:
				return WalletFragment.newInstance();
			case R.id.men:
				return MenFragment.newInstance();
			case R.id.women:
				return WomenFragment.newInstance();
			case R.id.kids:
				return KidsFragment.newInstance();
			case R.id.shoes:
				return ShoesFragment.newInstance();
			case R.id.review:
				return ReviewFragment.newInstance();
			default:
				return CategoryFragment.newInstance();
		}
	}

	public void inflateFragmentAtPosition(int fragmentId) {

		this.fragmentId = fragmentId;

		NamedFragment fragmentToRender = getFragmentToRender(fragmentId);

		getSupportActionBar().setTitle(fragmentToRender.getName());

		getSupportFragmentManager().
				beginTransaction().
				replace(R.id.content_frame, fragmentToRender).
				addToBackStack(null).
				commit();

		drawer.closeDrawer(GravityCompat.START);
	}
}
