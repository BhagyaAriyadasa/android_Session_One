package com.example.androidsession.presentation.home_page_activity.sub_activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidsession.R;
import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.table_entity.LoginEntity;
import com.example.androidsession.presentation.home_page_activity.HomePageActivityViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private HomePageActivityViewModel homePageActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = view.findViewById(R.id.listView);

        homePageActivityViewModel = new ViewModelProvider(this).get(HomePageActivityViewModel.class);

        homePageActivityViewModel.getActiveUsers().observe(getViewLifecycleOwner(), activeUsers -> {
            List<String> usersNames = new ArrayList<>();
            for (LoginEntity user : activeUsers) {
                usersNames.add(user.getUserName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, usersNames);
            listView.setAdapter(adapter);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view1, int position, long id) {
                    String selectedUserName = usersNames.get(position);
                    new AlertDialog.Builder(getContext())
                            .setTitle("Confirmation")
                            .setMessage("Do you want to set is active as false?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    for (LoginEntity user : activeUsers) {
                                        if (user.getUserName().equals(selectedUserName)) {
                                            homePageActivityViewModel.deactivateUser(getContext(), user);
                                            break;
                                        }
                                    }
                                    usersNames.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                    return true;
                }
            });
        });

        homePageActivityViewModel.fetchActiveUsers(getContext());

        return view;
    }
}
