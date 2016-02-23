package project.caloriecalculator.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import project.caloriecalculator.R;

/**
 * Adapter for the list containing calorie items.
 */
public final class ItemCursorAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;

    public static final String NAME_COLUMN = "name";
    public static final String CALORIE_COLUMN = "calorie";

    public ItemCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.list_item, null);
        ViewHolder holder = new ViewHolder();
        holder.titleText = (TextView) view.findViewById(R.id.name);
        holder.calorieText = (TextView) view.findViewById(R.id.calorie);
        holder.titleIndex = cursor.getColumnIndexOrThrow(NAME_COLUMN);
        holder.calorieIndex = cursor.getColumnIndexOrThrow(CALORIE_COLUMN);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.calorieText.setText(cursor.getString(viewHolder.calorieIndex));
        viewHolder.titleText.setText(cursor.getString(viewHolder.titleIndex));
    }

    static class ViewHolder {
        int titleIndex, calorieIndex;
        TextView titleText, calorieText;
    }

    public static enum ListType {
        EXERCISE,
        FOOD
    }
}
