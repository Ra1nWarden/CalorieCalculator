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
import project.caloriecalculator.data.ItemCursorAdapter;
import project.caloriecalculator.data.RecommendItemCursorAdapter;
import project.caloriecalculator.ui.ItemListFragment;

/**
 * An activity that lists recommended foods / exercises.
 */
public final class RecommendActivity extends AppCompatActivity {

    static final String LIST_TYPE_KEY = "ListType";
    private static final String TAG = "RecommendActivity";
    private static final String RAW_QUERY = "SELECT * FROM ";
    private static final String RECOMMENDED_EXERCISE_TABLE = "exercise_recommend";
    private static final String RECOMMENDED_FOOD_TABLE = "food_recommend";
    private ItemCursorAdapter.ListType listType;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.add_item_main);
        setUpList();
        setUpActionBar();
    }

    private void setUpList() {
        listType = (ItemCursorAdapter.ListType) getIntent().getExtras().getSerializable
                (LIST_TYPE_KEY);
        ItemListFragment recommendItemListFragment = (ItemListFragment) getSupportFragmentManager
                ().findFragmentById(R.id
                .main_list);
        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(this);
        Cursor cursor = null;
        if (listType == ItemCursorAdapter.ListType.EXERCISE) {
            cursor = databaseOpenHelper.getReadableDatabase().rawQuery(RAW_QUERY +
                    RECOMMENDED_EXERCISE_TABLE, null);
            recommendItemListFragment.getTitleView().setText(getResources().getString(R.string
                    .exercise_title));
            recommendItemListFragment.getCountView().setText(getResources().getString(R.string
                    .exercise_recommend));
        } else if (listType == ItemCursorAdapter.ListType.FOOD) {
            cursor = databaseOpenHelper.getReadableDatabase().rawQuery(RAW_QUERY +
                    RECOMMENDED_FOOD_TABLE, null);
            recommendItemListFragment.getTitleView().setText(getResources().getString(R.string
                    .food_title));
            recommendItemListFragment.getCountView().setText(getResources().getString(R.string.food_recommend));
        }
        if (cursor != null) {
            RecommendItemCursorAdapter adapter = new RecommendItemCursorAdapter(this, cursor, 0);
            recommendItemListFragment.getListView().setAdapter(adapter);
            recommendItemListFragment.getTitleView().setClickable(false);
        } else {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Error in loading cursor!");
            }
        }
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            RelativeLayout layout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.recommend_list_action_bar, null);
            TextView titleView = (TextView) layout.findViewById(R.id.recommend_list_title_text);
            if (listType == ItemCursorAdapter.ListType.EXERCISE) {
                titleView.setText(RecommendActivity.this.getResources().getString(R.string
                        .recommend_exercise_list_title));
            } else if (listType == ItemCursorAdapter.ListType.FOOD) {
                titleView.setText(RecommendActivity.this.getResources().getString(R.string
                        .recommend_food_list_title));
            }
            Button btn = (Button) layout.findViewById(R.id.done_button);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecommendActivity.this.finish();
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
