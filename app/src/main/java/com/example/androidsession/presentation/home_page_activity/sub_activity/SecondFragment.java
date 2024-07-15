package com.example.androidsession.presentation.home_page_activity.sub_activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import com.example.androidsession.R;
import com.example.androidsession.database.data_service.LoginDS;
import com.example.androidsession.database.table_entity.LoginEntity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class SecondFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView = view.findViewById(R.id.listView);
        LoginDS loginDS = new LoginDS(getContext());

        List<LoginEntity> activeUsers = new ArrayList<>();
        try {
            activeUsers = loginDS.getInactiveUsers();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> usersNames = new ArrayList<>();
        for (LoginEntity user : activeUsers) {
            usersNames.add(user.getUserName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, usersNames);
        listView.setAdapter(adapter);

        return view;
    }
}
