package project.caloriecalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import project.caloriecalculator.R;
import project.caloriecalculator.ui.ItemCursorAdapter;

/**
 * The main activity of the app.
 */
public final class MainActivity extends AppCompatActivity implements PopupMenu
        .OnMenuItemClickListener {

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
            RelativeLayout layout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.main_action_bar, null);
            actionBar.setCustomView(layout);
            actionBar.setDisplayShowCustomEnabled(true);
        } else {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Action bar is null at setUpActionBar()");
            }
        }
    }

    public void showPopUp(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.add_menu, popup.getMenu());
        popup.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.food) {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            Bundle args = new Bundle();
            args.putSerializable(AddItemActivity.LIST_TYPE_KEY, ItemCursorAdapter.ListType.FOOD);
            intent.putExtras(args);
            startActivity(intent);
        } else if (item.getItemId() == R.id.exercise) {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            Bundle args = new Bundle();
            args.putSerializable(AddItemActivity.LIST_TYPE_KEY, ItemCursorAdapter.ListType
                    .EXERCISE);
            intent.putExtras(args);
            startActivity(intent);
        }
        return true;
    }
}
