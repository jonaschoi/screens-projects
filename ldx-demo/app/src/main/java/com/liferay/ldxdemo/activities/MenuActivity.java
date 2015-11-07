package com.liferay.ldxdemo.activities;

import android.os.Bundle;
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

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.fragments.CategoryFragment;
import com.liferay.ldxdemo.fragments.KidsFragment;
import com.liferay.ldxdemo.fragments.MenFragment;
import com.liferay.ldxdemo.fragments.ProfileFragment;
import com.liferay.ldxdemo.fragments.ShoesFragment;
import com.liferay.ldxdemo.fragments.WalletFragment;
import com.liferay.ldxdemo.fragments.WomenFragment;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.push.PushScreensActivity;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class MenuActivity extends PushScreensActivity implements FragmentLoaded, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

	private static final float MIN_DISTANCE = 200f;
	private int position;
	private String[] menuItems = {"Shop by Category", "My Wallet", "Men", "Women", "Kids", "Shoes"};
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

		User user = SessionContext.getLoggedUser();

		((TextView) findViewById(R.id.logged_user)).setText(user.getFirstName() + " " + user.getLastName());
		findViewById(R.id.liferay_portrait).setOnClickListener(this);
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
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.category) {
			inflateFragmentAtPosition(0);
		} else if (id == R.id.wallet) {
			inflateFragmentAtPosition(1);
		} else if (id == R.id.men) {
			inflateFragmentAtPosition(2);
		} else if (id == R.id.women) {
			inflateFragmentAtPosition(3);
		} else if (id == R.id.kids) {
			inflateFragmentAtPosition(4);
		} else if (id == R.id.shoes) {
			inflateFragmentAtPosition(5);
		} else {
			inflateProfile();
		}

		return true;
	}

	@Override
	public void onClick(View v) {
		inflateProfile();
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
	protected void onPushNotificationReceived(final JSONObject jsonObject) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				DDLListScreenlet ddLList = (DDLListScreenlet) findViewById(R.id.wallet_default);
				if (ddLList != null) {
					Crouton.clearCroutonsForActivity(MenuActivity.this);
					LiferayCrouton.info(MenuActivity.this, "Reloading list...");
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

	private void moveToNextFragment() {
		position = (position + 1) % menuItems.length;

		inflateFragmentAtPosition(menuItems[position], getFragmentToRender(position));
	}

	private Fragment getFragmentToRender(int position) {
		switch (position) {
			case 1:
				return WalletFragment.newInstance();
			case 2:
				return MenFragment.newInstance();
			case 3:
				return WomenFragment.newInstance();
			case 4:
				return KidsFragment.newInstance();
			case 5:
				return ShoesFragment.newInstance();
			default:
				return CategoryFragment.newInstance();
		}
	}

	private void inflateFragmentAtPosition(int i) {
		inflateFragmentAtPosition(menuItems[i], getFragmentToRender(i));
	}

	private void inflateProfile() {
		inflateFragmentAtPosition("Profile", ProfileFragment.newInstance());
	}

	private void inflateFragmentAtPosition(String title, Fragment fragment) {

		navigationView.getMenu().getItem(menuItems.length).setChecked(true);

		getSupportActionBar().setTitle(title);

		getSupportFragmentManager().
				beginTransaction().
				replace(R.id.content_frame, fragment).
				addToBackStack(null).
				commit();

		drawer.closeDrawer(GravityCompat.START);
	}
}
