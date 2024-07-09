package com.example.androidsession.presentation.sub_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.androidsession.R;

import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_first, container, false);
        }
}
