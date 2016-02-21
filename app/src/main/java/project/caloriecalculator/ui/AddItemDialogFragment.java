package project.caloriecalculator.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import project.caloriecalculator.R;
import project.caloriecalculator.activities.AddItemActivity;

/**
 * A fragment that allows the user to add a specific item.
 */
public final class AddItemDialogFragment extends DialogFragment {

    private static final String TAG = "AddItemDialogFragment";
    private static final String ITEM_NAME_KEY = "ItemName";
    private ItemCursorAdapter.ListType itemType;
    private Context context;
    private OnAddItemListener addItemListener;
    private String itemName;
    private View dialogView;

    public static AddItemDialogFragment createDialogWithItem(String itemName) {
        Bundle args = new Bundle();
        args.putString(ITEM_NAME_KEY, itemName);
        AddItemDialogFragment f = new AddItemDialogFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        itemType = ((AddItemActivity) getActivity()).getListType();
        context = getActivity();
        itemName = getArguments().getString(ITEM_NAME_KEY);
        dialogView = getActivity().getLayoutInflater().inflate(R.layout.add_item_dialog, null);
        builder.setTitle(context.getResources().getString(R.string.add) + itemName)
                .setView(dialogView)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (addItemListener != null) {
                            String cntString = ((TextView) dialogView.findViewById(R.id
                                    .count_field)).getText().toString();
                            int cnt = Integer.parseInt(cntString);
                            if (cnt > 0) {
                                addItemListener.add(cnt, itemName, itemType);
                            }
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .remove(AddItemDialogFragment.this)
                                .commit();
                    }
                });
        TextView nameText = (TextView) dialogView.findViewById(R.id.item_name);
        nameText.setText(itemName);
        TextView unitText = (TextView) dialogView.findViewById(R.id.unit_view);
        if (itemType == ItemCursorAdapter.ListType.EXERCISE) {
            unitText.setText(context.getResources().getString(R.string.exercise_unit));
        } else if (itemType == ItemCursorAdapter.ListType.FOOD) {
            unitText.setText(context.getResources().getString(R.string.food_unit));
        }
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addItemListener = (OnAddItemListener) activity;
        } catch (ClassCastException e) {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Attaching activity does not implement OnAddItemListener interface!");
            }
        }
    }

    public interface OnAddItemListener {
        void add(int cnt, String itemName, ItemCursorAdapter.ListType itemType);
    }

}
