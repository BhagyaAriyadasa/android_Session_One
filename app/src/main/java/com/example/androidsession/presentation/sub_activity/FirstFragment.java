package com.example.androidsession.presentation.sub_activity;

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
import com.example.androidsession.database.data_service.CityDS;
import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.table_entity.CityEntity;
import com.example.androidsession.database.table_entity.LoginEntity;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = view.findViewById(R.id.listView);
        LoginDS loginDS = new LoginDS(getContext());

        List<LoginEntity> activeUsers = new ArrayList<>();
        try {
            activeUsers = loginDS.getActiveUsers();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> usersNames = new ArrayList<>();
        for (LoginEntity user : activeUsers) {
            usersNames.add(user.getUserName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, usersNames);
        listView.setAdapter(adapter);

        List<LoginEntity> finalActiveUsers = activeUsers;
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUserName = usersNames.get(position);
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Do you want to set is active as false?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for (LoginEntity user : finalActiveUsers) {
                                    if (user.getUserName().equals(selectedUserName)) {
                                        user.isIsActive();
                                        loginDS.updateUser(user);
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

        return view;
    }
}
