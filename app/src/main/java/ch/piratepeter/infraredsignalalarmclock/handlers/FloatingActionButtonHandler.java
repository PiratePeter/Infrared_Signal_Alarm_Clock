package ch.piratepeter.infraredsignalalarmclock.handlers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import ch.piratepeter.infraredsignalalarmclock.Messages;
import ch.piratepeter.infraredsignalalarmclock.R;

public class FloatingActionButtonHandler implements View.OnClickListener {

    private static final long MILLISECONDS_PER_DAY = 86_400_000L; // 24 * 60 * 60 * 1000
    private static final long MILLISECONDS_PER_HOUR = 3_600_000L; // 60 * 60 * 1000
    private static final long MILLISECONDS_PER_MINUTE = 60_000L; // 60 * 1000
    private static final long MILLISECONDS_PER_SECOND = 1_000L; // 1000
    private final AppCompatActivity activity;
    private final SharedPreferences sharedPreferences;

    public FloatingActionButtonHandler(AppCompatActivity activity) {
        this.activity = activity;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        FloatingActionButton fab = activity.findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            Spinner colorSpinner = activity.findViewById(R.id.colorSpinner);
            String color = colorSpinner.getSelectedItem().toString();
            int targetHourOfDay = sharedPreferences.getInt(EditTextHandler.SELECTED_HOURS, 0);
            int targetMinutes = sharedPreferences.getInt(EditTextHandler.SELECTED_MINUTES, 0);
            int targetSeconds = 0;

            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);
            int currentSecond = calendar.get(Calendar.SECOND);

            long delay = MILLISECONDS_PER_DAY +
                    (targetHourOfDay - currentHour) * MILLISECONDS_PER_HOUR +
                    (targetMinutes - currentMinute) * MILLISECONDS_PER_MINUTE +
                    (targetSeconds - currentSecond) * MILLISECONDS_PER_SECOND;
            delay %= MILLISECONDS_PER_DAY;

            scheduleNotification(delay, color);
            Messages.createToastMessage(activity, String.format("Alarm set in %s ms", delay));
        } catch (Exception e) {
            Messages.createPushupMessage(activity, e.getClass().toString(), e.getMessage(), R.drawable.ic_launcher_background);
        }
    }

    private void scheduleNotification(long delay, String color) {
        Intent notificationIntent = new Intent(activity, NotificationHandler.class);
        notificationIntent.putExtra(NotificationHandler.COLOR, color);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, MILLISECONDS_PER_DAY, pendingIntent);
    }
}
