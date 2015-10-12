package com.liferay.ldxdemo.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.liferay.ldxdemo.R;

/**
 * @author dipenp
 *
 * This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 * We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 * This activity layout contain one frame layout in which we will add our child activity layout.    
 */
public abstract class NavDrawerActivity extends AppCompatActivity {

	protected ListView mDrawerList;
	protected DrawerLayout mDrawerLayout;
	private ArrayAdapter<String> mAdapter;
	private ActionBarDrawerToggle mDrawerToggle;
	protected FrameLayout frameLayout;
	protected static int position;
	protected String[] listArray = { "Shop by Category", "My Wallet", "Men", "Women", "Kids", "Shoes" };


		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.navigation_drawer_base_layout);

			frameLayout = (FrameLayout)findViewById(R.id.content_frame);
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.nav_list);
//			mActivityTitle = getTitle().toString();

			addDrawerItems();
			setupDrawer();

			// enable ActionBar app icon to behave as action to toggle nav drawer
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
		}

	protected void addDrawerItems() {
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray);
		mDrawerList.setAdapter(mAdapter);

		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				openActivity(position);
			}
		});
	}

	private void setupDrawer() {
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(getString(R.string.app_name));
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

	protected void openActivity(int position) {

	mDrawerLayout.closeDrawer(mDrawerList);
	NavDrawerActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

		switch (position) {
			case 0:
				startActivity(new Intent(this, CategoryActivity.class));
				break;
			case 1:
				startActivity(new Intent(this, WalletActivity.class));
				break;
			case 2:
				startActivity(new Intent(this, MenActivity.class));
				break;
			case 3:
				startActivity(new Intent(this, WomenActivity.class));
				break;
			case 4:
				startActivity(new Intent(this, KidsActivity.class));
				break;
			case 5:
				startActivity(new Intent(this, ShoesActivity.class));
				break;

			default:
				break;
		}
	}
}
