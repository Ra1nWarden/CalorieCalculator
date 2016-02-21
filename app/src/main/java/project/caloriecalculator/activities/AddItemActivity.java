package project.caloriecalculator.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.caloriecalculator.R;
import project.caloriecalculator.data.DatabaseOpenHelper;
import project.caloriecalculator.ui.ItemCusorAdapter;
import project.caloriecalculator.ui.ItemListFragment;

/**
 * An activity that allows the user to add items.
 */
public final class AddItemActivity extends AppCompatActivity {

    private static String TAG = "AddItemActivity";
    private static final String RAW_QUERY = "SELECT * FROM ";

    private static final String FOOD_TABLE = "food";
    private static final String EXERCISE_TABLE = "exercise";

    static final String LIST_TYPE_KEY = "ListType";

    private ItemCusorAdapter.ListType listType;
    private ItemListFragment itemListFragment;
    private DatabaseOpenHelper databaseOpenHelper;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.add_item_main);
        setUpList();
        setUpActionBar();
    }

    private void setUpList() {
        listType = (ItemCusorAdapter.ListType) getIntent().getExtras().getSerializable
                (LIST_TYPE_KEY);
        itemListFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id
                .main_list);
        databaseOpenHelper = new DatabaseOpenHelper(this);
        Cursor cursor = null;
        if (listType == ItemCusorAdapter.ListType.EXERCISE) {
            cursor = databaseOpenHelper.getReadableDatabase().rawQuery(RAW_QUERY +
                    EXERCISE_TABLE, null);
        } else if (listType == ItemCusorAdapter.ListType.FOOD) {
            cursor = databaseOpenHelper.getReadableDatabase().rawQuery(RAW_QUERY + FOOD_TABLE,
                    null);
        }
        if (cursor != null) {
            ItemCusorAdapter adapter = new ItemCusorAdapter(this, cursor, 0);
            itemListFragment.getListView().setAdapter(adapter);
        }
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            RelativeLayout layout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.list_action_bar, null);
            TextView titleView = (TextView) layout.findViewById(R.id.list_title_text);
            if (listType == ItemCusorAdapter.ListType.EXERCISE) {
                titleView.setText(AddItemActivity.this.getResources().getString(R.string
                        .exercise_list_title));
            } else if (listType == ItemCusorAdapter.ListType.FOOD) {
                titleView.setText(AddItemActivity.this.getResources().getString(R.string
                        .food_list_title));
            }
            Button btn = (Button) layout.findViewById(R.id.done_button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddItemActivity.this.finish();
                }
            });
            actionBar.setCustomView(layout);
            actionBar.setDisplayShowCustomEnabled(true);
        } else {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Action bar is null at setUpActionBar()");
            }
        }
    }
}
