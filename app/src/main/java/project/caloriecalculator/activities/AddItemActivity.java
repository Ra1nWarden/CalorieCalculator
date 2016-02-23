package project.caloriecalculator.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.caloriecalculator.R;
import project.caloriecalculator.data.DatabaseOpenHelper;
import project.caloriecalculator.ui.AddItemDialogFragment;
import project.caloriecalculator.data.ItemCursorAdapter;
import project.caloriecalculator.ui.ItemListFragment;

/**
 * An activity that allows the user to add items.
 */
public final class AddItemActivity extends AppCompatActivity implements
        AddItemDialogFragment.OnAddItemListener,
        AdapterView.OnItemClickListener {

    private static final String TAG = "AddItemActivity";
    private static final String RAW_QUERY = "SELECT * FROM ";
    private static final String DIALOG_TAG = "AddItemDialog";

    private static final String FOOD_TABLE = "food";
    private static final String EXERCISE_TABLE = "exercise";

    static final String LIST_TYPE_KEY = "ListType";

    private ItemCursorAdapter.ListType listType;
    private ItemListFragment itemListFragment;
    private DatabaseOpenHelper databaseOpenHelper;
    private ItemCursorAdapter adapter;

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
        itemListFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id
                .main_list);
        databaseOpenHelper = new DatabaseOpenHelper(this);
        Cursor cursor = null;
        if (listType == ItemCursorAdapter.ListType.EXERCISE) {
            cursor = databaseOpenHelper.getReadableDatabase().rawQuery(RAW_QUERY +
                    EXERCISE_TABLE, null);
        } else if (listType == ItemCursorAdapter.ListType.FOOD) {
            cursor = databaseOpenHelper.getReadableDatabase().rawQuery(RAW_QUERY + FOOD_TABLE,
                    null);
        }
        if (cursor != null) {
            adapter = new ItemCursorAdapter(this, cursor, 0);
            itemListFragment.getListView().setAdapter(adapter);
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
                    LayoutInflater.from(this).inflate(R.layout.add_list_action_bar, null);
            TextView titleView = (TextView) layout.findViewById(R.id.list_title_text);
            if (listType == ItemCursorAdapter.ListType.EXERCISE) {
                titleView.setText(AddItemActivity.this.getResources().getString(R.string
                        .exercise_list_title));
            } else if (listType == ItemCursorAdapter.ListType.FOOD) {
                titleView.setText(AddItemActivity.this.getResources().getString(R.string
                        .food_list_title));
            }
            Button recommendBtn = (Button) layout.findViewById(R.id.recommend_button);
            recommendBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putSerializable(RecommendActivity.LIST_TYPE_KEY, listType);
                    Intent intent = new Intent(AddItemActivity.this, RecommendActivity.class);
                    intent.putExtras(args);
                    startActivity(intent);
                }
            });
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

    public ItemCursorAdapter.ListType getListType() {
        return listType;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView parentList = (ListView) parent;
        Cursor cursor = (Cursor) parentList.getAdapter().getItem(position);
        AddItemDialogFragment dialog = AddItemDialogFragment.createDialogWithItem(cursor
                .getString(cursor.getColumnIndex(ItemCursorAdapter.NAME_COLUMN)));
        dialog.show(getSupportFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void add(int cnt, String itemName, ItemCursorAdapter.ListType itemType) {

    }
}
