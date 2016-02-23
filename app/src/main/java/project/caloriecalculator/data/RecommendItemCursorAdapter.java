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
 * An adapter for recommended list
 */
public class RecommendItemCursorAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;

    public static final String NAME_COLUMN = "name";
    public static final String AMOUNT_COLUMN = "amount";

    public RecommendItemCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.list_item, null);
        ViewHolder holder = new ViewHolder();
        holder.titleText = (TextView) view.findViewById(R.id.name);
        holder.amountText = (TextView) view.findViewById(R.id.calorie);
        holder.titleIndex = cursor.getColumnIndexOrThrow(NAME_COLUMN);
        holder.amountIndex = cursor.getColumnIndexOrThrow(AMOUNT_COLUMN);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.amountText.setText(cursor.getString(viewHolder.amountIndex));
        viewHolder.titleText.setText(cursor.getString(viewHolder.titleIndex));
    }

    static class ViewHolder {
        int titleIndex, amountIndex;
        TextView titleText, amountText;
    }
}
