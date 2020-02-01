package ch.piratepeter.infraredsignalalarmclock.handlers;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import ch.piratepeter.infraredsignalalarmclock.Messages;
import ch.piratepeter.infraredsignalalarmclock.R;

public class EditTextHandler implements View.OnClickListener {

    public static final String SELECTED_HOURS = "SELECTED_HOURS";
    public static final String SELECTED_MINUTES = "SELECTED_MINUTES";
    private final AppCompatActivity activity;
    private final SharedPreferences sharedPreferences;
    private final EditText time;

    public EditTextHandler(AppCompatActivity activity) {
        this.activity = activity;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        this.time = activity.findViewById(R.id.timeEditText);
        this.time.setOnClickListener(this);
        int hourOfDay = sharedPreferences.getInt(SELECTED_HOURS, 0);
        int minutes = sharedPreferences.getInt(SELECTED_MINUTES, 0);
        String text = String.format("%02d:%02d", hourOfDay, minutes);
        this.time.setText(text);
    }

    @Override
    public void onClick(View view) {
        try {
            int hourOfDay = sharedPreferences.getInt(SELECTED_HOURS, 0);
            int minutes = sharedPreferences.getInt(SELECTED_MINUTES, 0);
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String text = String.format("%02d:%02d", hourOfDay, minutes);
                    editor.putInt(SELECTED_HOURS, hourOfDay);
                    editor.putInt(SELECTED_MINUTES, minutes);
                    editor.commit();
                    time.setText(text);
                }
            }, hourOfDay, minutes, true);
            timePickerDialog.show();
        } catch (Exception e) {
            Messages.createPushupMessage(activity, e.getClass().toString(), e.getMessage(), R.drawable.ic_launcher_background);
        }
    }
}
