package com.liferay.dxpdemo.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.liferay.dxpdemo.R;
import com.liferay.dxpdemo.fragments.BalanceFragment;
import com.liferay.dxpdemo.fragments.FormFragment;
import com.liferay.dxpdemo.fragments.InvestmentFragment;
import com.liferay.dxpdemo.fragments.MyAccountFragment;
import com.liferay.dxpdemo.fragments.NotificationsFragment;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.push.PushScreensActivity;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;

public class MenuActivity extends PushScreensActivity implements FragmentLoaded, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

	private static final float MIN_DISTANCE = 200f;
	private int position;
	//	private String[] menuItems = {"My Account", "My Wallet", "Men", "Women", "Kids", "Shoes", "Review"};
	private String[] menuItems;
	private DrawerLayout drawer;
	private NavigationView navigationView;
	private GestureDetector.OnGestureListener listener = new GestureDetector.OnGestureListener() {
		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) < MIN_DISTANCE / 2
						&& e1.getX() - e2.getX() > MIN_DISTANCE
						&& Math.abs(velocityX) > MIN_DISTANCE) {
					moveToNextFragment();
					return false;
				}
			} catch (Exception e) {
				// nothing
			}
			return true;
		}
	};

	protected void createMenuItems() {
		Resources res = getResources();
		menuItems = res.getStringArray(R.array.menu_items);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createMenuItems();
		setContentView(R.layout.activity_menu);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		position = getIntent().getIntExtra("position", 0);
		navigationView.getMenu().getItem(position).setChecked(true);

		User user = SessionContext.getCurrentUser();

		View headerView = navigationView.getHeaderView(0);
		TextView loggedUser = (TextView) headerView.findViewById(R.id.logged_user);
		loggedUser.setText(user.getFirstName() + " " + user.getLastName());
		headerView.findViewById(R.id.liferay_portrait).setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		getSupportFragmentManager().
				beginTransaction().
				replace(R.id.content_frame, getFragmentToRender(position)).
				addToBackStack("category").
				commit();
	}

	@Override
	public void onFragmentLoaded(View view, final boolean interceptEvents) {
		if (view != null) {
			final GestureDetector gestureDetector = new GestureDetector(this, listener);
			view.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(final View view, final MotionEvent event) {
					gestureDetector.onTouchEvent(event);
					return interceptEvents;
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nav_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.tests:
				changeEnvironment("tests");
				break;
			case R.id.production:
				changeEnvironment("prod");
				break;
			case R.id.development:
			default:
				changeEnvironment("dev");
		}

		return super.onOptionsItemSelected(item);
	}

	private void changeEnvironment(String suffix) {
		LiferayServerContext.setServer(getId("liferay_server", suffix, R.string.liferay_server));
		LiferayServerContext.setCompanyId(Long.valueOf(getId("liferay_company_id", suffix, R.string.liferay_company_id)));
		LiferayServerContext.setGroupId(Long.valueOf(getId("liferay_server", suffix, R.string.liferay_group_id)));
	}

	private String getId(String property, String prefix, int defaultValue) {
		try {
			return getString(R.string.class.getField(property + "_" + prefix).getInt(null));
		}
		catch (IllegalAccessException | NoSuchFieldException e) {
			LiferayLogger.e("property does not exist");
		}
		return getString(defaultValue);
	}

	@NonNull
	private String getId(int anInt) throws IllegalAccessException, NoSuchFieldException {
		return getString(anInt);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.profile) {
			startActivity(new Intent(this, ProfileActivity.class));
		} else {

			if (id == R.id.screen_1) {
				position = 0;
			} else if (id == R.id.screen_2) {
				position = 1;
			} else if (id == R.id.screen_3) {
				position = 2;
			} else if (id == R.id.screen_4) {
				position = 3;
//			} else if (id == R.id.kids) {
//				position = 4;
//			} else if (id == R.id.shoes) {
//				position = 5;
//			} else if (id == R.id.screen_6) {
//				position = 6;
			}

			inflateFragmentAtPosition(position);
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

		outState.putInt("position", position);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		position = savedInstanceState.getInt("position");
	}

	@Override
	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	@Override
	protected void onPushNotificationReceived(JSONObject jsonObject) {
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				WebContentDisplayScreenlet webContentDisplayScreenlet = (WebContentDisplayScreenlet) findViewById(R.id.notifications);
//				if (webContentDisplayScreenlet != null) {
//					Crouton.clearCroutonsForActivity(MenuActivity.this);
//					Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
//					webContentDisplayScreenlet.load();
//				}
//			}
//		});
	}

	@Override
	protected void onErrorRegisteringPush(String message, Exception e) {

	}

	@Override
	protected String getSenderId() {
		return getString(R.string.sender_id);
	}

	private void moveToNextFragment() {
		position = (position + 1) % menuItems.length;

		inflateFragmentAtPosition(position);
	}

	private Fragment getFragmentToRender(int position) {
		switch (position) {
			case 1:
				return InvestmentFragment.newInstance();
			case 2:
				return NotificationsFragment.newInstance();
			case 3:
				return FormFragment.newInstance();
//			case 4:
//				return KidsFragment.newInstance();
//			case 5:
//				return ShoesFragment.newInstance();
			case 6:
				return BalanceFragment.newInstance();
			default:
				return MyAccountFragment.newInstance();
		}
	}

	public void inflateFragmentAtPosition(int position) {

		navigationView.getMenu().getItem(position).setChecked(true);

		getSupportActionBar().setTitle(menuItems[position]);

		getSupportFragmentManager().
				beginTransaction().
				replace(R.id.content_frame, getFragmentToRender(position)).
				addToBackStack(null).
				commit();

		drawer.closeDrawer(GravityCompat.START);
	}
}
