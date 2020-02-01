package ch.piratepeter.infraredsignalalarmclock.handlers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.ConsumerIrManager;

import ch.piratepeter.infraredsignalalarmclock.InfraredCommand;
import ch.piratepeter.infraredsignalalarmclock.Messages;
import ch.piratepeter.infraredsignalalarmclock.R;

public class NotificationHandler extends BroadcastReceiver {
    public static final String COLOR = "color";

    public void onReceive(Context context, Intent intent) {
        try {
            String color = intent.getStringExtra(COLOR);
            ConsumerIrManager consumerIrManager = (ConsumerIrManager) context.getSystemService(context.CONSUMER_IR_SERVICE);
            if (consumerIrManager.hasIrEmitter()) {
                consumerIrManager.transmit(InfraredCommand.INFRARED_FREQUENCY, InfraredCommand.valueOf(color).getPattern());
            } else {
                Messages.createToastMessage(context, "This device is not compatible because it has no ir emitter!");
            }
            Messages.createPushupMessage(context, "Hey, wake up!", color, R.drawable.ic_launcher_background);
        } catch (Exception e) {
            Messages.createPushupMessage(context, e.getClass().toString(), e.getMessage(), R.drawable.ic_launcher_background);
        }
    }

}
