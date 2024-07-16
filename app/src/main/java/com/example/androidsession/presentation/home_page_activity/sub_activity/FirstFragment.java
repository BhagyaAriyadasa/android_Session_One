package com.example.androidsession.presentation.home_page_activity.sub_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidsession.R;
import com.example.androidsession.database.table_entity.LoginEntity;
import com.example.androidsession.presentation.home_page_activity.HomePageActivityViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private HomePageActivityViewModel viewModel;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> usersNames = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        viewModel = new ViewModelProvider(this).get(HomePageActivityViewModel.class);
        listView = view.findViewById(R.id.listView);

        initView();
        observeData();

        viewModel.fetchActiveUsers(getContext());

        return view;
    }

    private void initView() {
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, usersNames);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedUserName = usersNames.get(position);
            String message = "Do you want to set " + selectedUserName + " as inactive?";
            AlertDialogActivity.showConfirmationDialog(getContext(), message, () -> {
                List<LoginEntity> activeUsers = viewModel.getActiveUsers().getValue();
                if (activeUsers != null) {
                    for (LoginEntity user : activeUsers) {
                        if (user.getUserName().equals(selectedUserName)) {
                            viewModel.deactivateUser(getContext(), user);
                            break;
                        }
                    }
                }
                usersNames.remove(position);
                adapter.notifyDataSetChanged();
            });
            return true;
        });
    }

    private void observeData() {
        viewModel.getActiveUsers().observe(getViewLifecycleOwner(), activeUsers -> {
            usersNames.clear();
            for (LoginEntity user : activeUsers) {
                usersNames.add(user.getUserName());
            }
            adapter.notifyDataSetChanged();
        });
    }
}
