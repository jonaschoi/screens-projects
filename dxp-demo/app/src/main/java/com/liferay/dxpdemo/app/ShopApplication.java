package com.liferay.dxpdemo.app;

import android.app.Application;
import android.os.RemoteException;
import android.util.Log;

import com.liferay.dxpdemo.R;
import com.liferay.dxpdemo.beacon.NotificationUtil;

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
				Log.i(TAG, "I just saw a beacon for the first time!");
			}

			@Override
			public void didExitRegion(Region region) {
				Log.i(TAG, "I no longer see a beacon");
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
			String[] beacon_id = getApplicationContext().getResources().getStringArray(R.array.beacon_test);

			Identifier[] identifiers = createIdentifier(beacon_id);
			Region sth = new Region("shop", identifiers[0], identifiers[1], identifiers[2]);
			beaconManager.startMonitoringBeaconsInRegion(sth);
			beaconManager.startRangingBeaconsInRegion(sth);
		} catch (RemoteException e) {
			Log.e(TAG, "Error reading beacons", e);
		}
	}

	protected Identifier[] createIdentifier(String[] beacon_array) {
		Identifier[] identifiers = new Identifier[3];

		identifiers[0] = Identifier.fromUuid(UUID.fromString(beacon_array[0]));
		identifiers[1] = Identifier.fromInt(Integer.parseInt(beacon_array[1]));
		identifiers[2] = Identifier.fromInt(Integer.parseInt(beacon_array[2]));

		return identifiers;
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
		calendar.add(Calendar.SECOND, -30);
		return calendar.getTime();
	}
}
