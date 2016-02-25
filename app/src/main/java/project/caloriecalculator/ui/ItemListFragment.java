package project.caloriecalculator.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import project.caloriecalculator.R;

public final class ItemListFragment extends Fragment {

    private static final String TAG = "ItemListFragment";

    private ListView listView;
    private TextView titleView;
    private TextView countView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.selected_list);
        View headerView = inflater.inflate(R.layout.list_header, container, false);
        titleView = (TextView) headerView.findViewById(R.id.header_item_title);
        countView = (TextView) headerView.findViewById(R.id.header_count_title);
        listView.addHeaderView(headerView);
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

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getCountView() {
        return countView;
    }

}
