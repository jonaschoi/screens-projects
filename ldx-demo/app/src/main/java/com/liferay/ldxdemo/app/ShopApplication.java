package com.liferay.ldxdemo.app;

import android.app.Application;
import android.os.RemoteException;
import android.util.Log;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.beacon.NotificationUtil;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Javier Gamarra
 */
public class ShopApplication extends Application implements BeaconConsumer {

	public static final String BEACON_LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"; //see line 81
	protected static final String TAG = "ShopApp";
	private static final double MIN_DISTANCE = 2D;
	private Date lastNotificationSent = new Date(0);
	private BeaconManager beaconManager;

	@Override
	public void onCreate() {
		super.onCreate();

		bindBeacon();
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


		beaconManager.setRangeNotifier(new RangeNotifier() {
			@Override
			public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
				processNearBeacons(collection);
			}
		});

		try {
			Identifier beaconId1 = Identifier.fromUuid(UUID.fromString(getString(R.string.beacon_uuid1)));
			Identifier beaconId2 = Identifier.fromInt(Integer.parseInt(getString(R.string.beacon_uuid2)));
			Identifier beaconId3 = Identifier.fromInt(Integer.parseInt(getString(R.string.beacon_uuid3)));
			Region sth = new Region("shop", beaconId1, beaconId2, beaconId3);
			beaconManager.startMonitoringBeaconsInRegion(sth);
			beaconManager.startRangingBeaconsInRegion(sth);
		} catch (RemoteException e) {
			Log.e(TAG, "Error reading beacons", e);
		}
	}

	private void bindBeacon() {
		beaconManager = BeaconManager.getInstanceForApplication(this);
		beaconManager.getBeaconParsers().add(new BeaconParser().
				setBeaconLayout(getString(R.string.beacon_layout)));
		beaconManager.bind(this);
	}

	private void processNearBeacons(Collection<Beacon> collection) {
		for (Beacon beacon : collection) {
			double distance = beacon.getDistance();
			if (distance < MIN_DISTANCE) {

				Log.e("Beacon close, at: ", String.valueOf(distance) + " meters");

				if (lastNotificationSent.before(getTimeFiveMinutesAgo())) {
					NotificationUtil.sendNotification(getApplicationContext());

					lastNotificationSent = new Date();
				}
			}
		}
	}

	private Date getTimeFiveMinutesAgo() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -5);
		return calendar.getTime();
	}
}
