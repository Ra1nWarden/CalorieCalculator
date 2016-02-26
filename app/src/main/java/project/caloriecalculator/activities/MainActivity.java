package project.caloriecalculator.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import project.caloriecalculator.R;
import project.caloriecalculator.data.DatabaseOpenHelper;
import project.caloriecalculator.data.ItemCursorAdapter;
import project.caloriecalculator.data.RecordItemCursorAdapter;
import project.caloriecalculator.ui.ItemListFragment;

/**
 * The main activity of the app.
 */
public final class MainActivity extends AppCompatActivity implements PopupMenu
        .OnMenuItemClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MainActivity";
    private static final String RAW_QUERY = "SELECT * FROM record";

    private ItemListFragment listFragment;
    private DatabaseOpenHelper databaseOpenHelper;
    private TextView counterField;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main);
        listFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id
                .main_list);
        counterField = (TextView) findViewById(R.id.counter_field);
        databaseOpenHelper = new DatabaseOpenHelper(this);
        setUpActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpList();
        updateNumber();
    }

    private void updateNumber() {
        Cursor cursor = databaseOpenHelper.getReadableDatabase().rawQuery("SELECT SUM(item_cnt) " +
                "as total " +
                "FROM record", null);
        cursor.moveToFirst();
        counterField.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("total"))) + " " +
                "kj");
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

    private void setUpList() {
        Cursor cursor = databaseOpenHelper.getReadableDatabase().rawQuery(RAW_QUERY, null);
        RecordItemCursorAdapter adapter = new RecordItemCursorAdapter(this, cursor, 0);
        listFragment.getListView().setAdapter(adapter);
        listFragment.getTitleView().setText(getResources().getString(R.string.main_list_title));
        listFragment.getCountView().setText(getResources().getString(R.string.main_list_count));
    }

    public void showPopUp(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.add_menu, popup.getMenu());
        popup.show();
    }

    public void clearAll(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.clear_all)
                .setMessage(R.string.question)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseOpenHelper.getWritableDatabase().delete("record", null, null);
                        setUpList();
                        updateNumber();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    public void share(View view) {
        View rootView = getWindow().getDecorView().findViewById(R.id.main);
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        File file = new File(Environment.getExternalStorageDirectory() +
                "/screenshots.png");
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Progress"));
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
