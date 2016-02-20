package project.caloriecalculator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return view;
    }
}
