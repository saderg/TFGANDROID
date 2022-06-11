package com.example.autoregistros.principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.autoregistros.Graphics;
import com.example.autoregistros.R;
import com.example.autoregistros.databinding.FragmentDayBinding;

import java.util.Calendar;

public class DayFragment extends Fragment {

    private FragmentDayBinding binding;
    public TextView day;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance){
        super.onViewCreated(view, savedInstance);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}