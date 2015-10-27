package com.liferay.ldxdemo.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.fragments.CategoryFragment;
import com.liferay.ldxdemo.fragments.KidsFragment;
import com.liferay.ldxdemo.fragments.MenFragment;
import com.liferay.ldxdemo.fragments.ShoesFragment;
import com.liferay.ldxdemo.fragments.WalletFragment;
import com.liferay.ldxdemo.fragments.WomenFragment;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.push.PushScreensActivity;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * @author Javier Gamarra
 */
public class NavDrawerActivity extends PushScreensActivity implements FragmentLoaded {

	private static final float MIN_DISTANCE = 200f;
	private int position;
	private ListView drawerList;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private String[] menuItems = {"Shop by Category", "My Wallet", "Men", "Women", "Kids", "Shoes"};

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
		setContentView(R.layout.navigation_drawer_base_layout);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.nav_list);

		addDrawerItems();
		setupDrawer();

		position = getIntent().getIntExtra("position", 0);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
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
	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	protected void addDrawerItems() {
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItems);
		drawerList.setAdapter(adapter);

		drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				drawerList.setItemChecked(position, true);
				inflateFragment(position);
			}
		});
	}

	private void setupDrawer() {
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(getString(R.string.app_slogan));
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle(menuItems[position]);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};

		drawerToggle.setDrawerIndicatorEnabled(true);
		drawerLayout.setDrawerListener(drawerToggle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
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

		// Activate the navigation drawer toggle
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void inflateFragment(int position) {

		drawerLayout.closeDrawer(drawerList);
		this.position = position;

		Fragment fragment = getFragmentToRender(position);

		getSupportFragmentManager().
				beginTransaction().
				replace(R.id.content_frame, fragment).
				addToBackStack("category").
				commit();
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

	@Override
	protected void onPushNotificationReceived(final JSONObject jsonObject) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				DDLListScreenlet ddLList = (DDLListScreenlet) findViewById(R.id.wallet_default);
				if (ddLList != null) {
					Crouton.clearCroutonsForActivity(NavDrawerActivity.this);
					LiferayCrouton.info(NavDrawerActivity.this, "Reloading list...");
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

	private void moveToNextFragment() {
		position = (position + 1) % menuItems.length;

		getSupportActionBar().setTitle(menuItems[position]);

		getSupportFragmentManager().
				beginTransaction().
				replace(R.id.content_frame, getFragmentToRender(position)).
				addToBackStack(null).
				commit();
	}
}
