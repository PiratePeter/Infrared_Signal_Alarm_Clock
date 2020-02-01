package ch.piratepeter.infraredsignalalarmclock.handlers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import ch.piratepeter.infraredsignalalarmclock.InfraredCommand;
import ch.piratepeter.infraredsignalalarmclock.Messages;
import ch.piratepeter.infraredsignalalarmclock.R;

public class SpinnerHandler implements AdapterView.OnItemSelectedListener {

    private static final String SELECTED_ITEM = "SELECTED_ITEM";
    private final AppCompatActivity activity;
    private final SharedPreferences sharedPreferences;

    public SpinnerHandler(AppCompatActivity activity) {
        this.activity = activity;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, InfraredCommand.names());
        Spinner spinner = activity.findViewById(R.id.colorSpinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        int itemPosition = sharedPreferences.getInt(SELECTED_ITEM, 0);
        spinner.setSelection(itemPosition);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int itemPosition = parent.getSelectedItemPosition();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SELECTED_ITEM, itemPosition);
        editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Messages.createToastMessage(activity, "Nothing selected!");
    }
}
