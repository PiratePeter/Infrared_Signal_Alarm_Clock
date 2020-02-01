package ch.piratepeter.infraredsignalalarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Messages {

    /**
     * @param icon e.g. R.drawable.ic_launcher_background
     */
    public static void createPushupMessage(Context context, String title, String body, int icon) {
        NotificationManager notification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(context.getApplicationContext())
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.notify(0, notify);
    }

    public static void createSnackbarMessage(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    public static void createToastMessage(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
