package course.examples.alarms.alarmcreate;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlarmCreateActivity extends Activity {

	private AlarmManager mAlarmManager;
	private Intent mNotificationReceiverIntent;
	private PendingIntent mNotificationReceiverPendingIntent;
	private static final long INITIAL_ALARM_DELAY = 1 * 60 * 1000L;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		// Get the AlarmManager Service
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		// Create an Intent to broadcast to the AlarmNotificationReceiver
		mNotificationReceiverIntent = new Intent(AlarmCreateActivity.this,
				AlarmNotificationReceiver.class);

		// Create an PendingIntent that holds the NotificationReceiverIntent
		mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
				AlarmCreateActivity.this, 0, mNotificationReceiverIntent, 0);

		// Set up single alarm Button
		final Button singleAlarmButton = (Button) findViewById(R.id.single_alarm_button);
		singleAlarmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Set single alarm
				mAlarmManager.setExact(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis() + INITIAL_ALARM_DELAY,
						mNotificationReceiverPendingIntent);

				// Show Toast message
				Toast.makeText(getApplicationContext(), "Single Alarm Set",
						Toast.LENGTH_LONG).show();
			}
		});

		// Set up repeating Alarm Button
		final Button repeatingAlarmButton = (Button) findViewById(R.id.repeating_alarm_button);
		repeatingAlarmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Set repeating alarm
				mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
						1*60*1000,
						mNotificationReceiverPendingIntent);

				// Show Toast message
				Toast.makeText(getApplicationContext(), "Repeating Alarm Set",
						Toast.LENGTH_LONG).show();
			}
		});

		// Set up cancel repeating alarm Button
		final Button cancelRepeatingAlarmButton = (Button) findViewById(R.id.cancel_repeating_alarm_button);
		cancelRepeatingAlarmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Cancel all alarms using mNotificationReceiverPendingIntent
				mAlarmManager.cancel(mNotificationReceiverPendingIntent);

				// Show Toast message
				Toast.makeText(getApplicationContext(),
						"Repeating Alarms Cancelled", Toast.LENGTH_LONG).show();
			}
		});
	}
}