package com.liferay.ldxdemo.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
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
public class NavDrawerActivity extends PushScreensActivity {

	protected static int position;
	protected ListView mDrawerList;
	protected DrawerLayout mDrawerLayout;
	protected FrameLayout frameLayout;
	protected String[] listArray = {"Shop by Category", "My Wallet", "Men", "Women", "Kids", "Shoes"};
	private ArrayAdapter<String> mAdapter;
	private ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base_layout);

		frameLayout = (FrameLayout) findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.nav_list);

		addDrawerItems();
		setupDrawer();

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		getSupportFragmentManager().
				beginTransaction().
				replace(R.id.content_frame, CategoryActivity.newInstance()).
				addToBackStack("category").
				commit();
	}

	@Override
	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	protected void addDrawerItems() {
		mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArray);
		mDrawerList.setAdapter(mAdapter);

		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mDrawerList.setItemChecked(position, true);
				inflateFragment(position);
			}
		});
	}

	private void setupDrawer() {
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(getString(R.string.app_slogan));
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle(listArray[position]);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};

		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
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
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void inflateFragment(int position) {

		mDrawerLayout.closeDrawer(mDrawerList);
		NavDrawerActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

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
}
