package project.caloriecalculator.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import project.caloriecalculator.R;

public final class ItemListFragment extends Fragment {

    private static final String TAG = "ItemListFragment";

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.selected_list);
        try {
            listView.setOnItemClickListener((AdapterView.OnItemClickListener) getActivity());
        } catch (ClassCastException e) {
            if (Log.isLoggable(TAG, Log.ERROR)) {
                Log.e(TAG, "Error in setting OnItemClickListener!");
            }
        }
        return view;
    }

    public ListView getListView() {
        return listView;
    }

}
