package project.caloriecalculator.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import project.caloriecalculator.R;

/**
 * The main activity of the app.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main);
        setUpActionBar();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(LayoutInflater.from(this).inflate(R.layout.action_bar, null));
            actionBar.setDisplayShowCustomEnabled(true);
        } else {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Action bar is null at setUpActionBar()");
            }
        }
    }


}
