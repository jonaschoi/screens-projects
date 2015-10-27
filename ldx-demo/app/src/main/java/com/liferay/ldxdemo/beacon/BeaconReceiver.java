package com.liferay.ldxdemo.beacon;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.liferay.ldxdemo.R;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;
/**
 * @author Javier Gamarra
 */
public class BeaconReceiver extends AppCompatActivity implements BeaconConsumer {

	protected static final String TAG = "MonitoringActivity";
	private BeaconManager beaconManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_wallet);
		beaconManager = BeaconManager.getInstanceForApplication(this);
		// To detect proprietary beacons, you must add a line like below corresponding to your beacon
		// type.  Do a web search for "setBeaconLayout" to get the proper expression.
		beaconManager.getBeaconParsers().add(new BeaconParser().
				setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
		beaconManager.bind(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		beaconManager.unbind(this);
	}

	@Override
	public void onBeaconServiceConnect() {
		beaconManager.setMonitorNotifier(new MonitorNotifier() {
			@Override
			public void didEnterRegion(Region region) {
				Log.i(TAG, "I just saw an beacon for the first time!");
			}

			@Override
			public void didExitRegion(Region region) {
				Log.i(TAG, "I no longer see an beacon");
			}

			@Override
			public void didDetermineStateForRegion(int state, Region region) {
				Log.i(TAG, "I have just switched from seeing/not seeing beacons: " + state);
			}
		});

		try {
			beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
		} catch (RemoteException e) {
		}
	}
}
