package ch.piratepeter.infraredsignalalarmclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ch.piratepeter.infraredsignalalarmclock.handlers.EditTextHandler;
import ch.piratepeter.infraredsignalalarmclock.handlers.FloatingActionButtonHandler;
import ch.piratepeter.infraredsignalalarmclock.handlers.SpinnerHandler;


public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_THEME = "SELECTED_THEME";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            int theme = sharedPreferences.getInt(SELECTED_THEME, R.style.AppTheme);
            setTheme(theme);
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);

            // Toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Spinner
            new SpinnerHandler(this);

            // Floating Action Button
            new FloatingActionButtonHandler(this);

            // Edit Text
            new EditTextHandler(this);
        } catch (Exception e) {
            Messages.createToastMessage(this, e.getMessage());
            Messages.createPushupMessage(this, e.getClass().toString(), e.getMessage(), R.drawable.ic_launcher_background);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_basictheme:
                changeTheme(R.style.AppTheme);
                return true;
            case R.id.action_marine:
                changeTheme(R.style.Marine);
                return true;
            case R.id.action_blackbeard:
                changeTheme(R.style.Blackbeard);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeTheme(int theme) {
        setTheme(theme);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SELECTED_THEME, theme);
        editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
