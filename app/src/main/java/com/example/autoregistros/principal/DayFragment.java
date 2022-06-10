package com.example.autoregistros.principal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        day = root.findViewById(R.id.textDay);

        Calendar calendario = Calendar.getInstance();
        int day = calendario.get(Calendar.DAY_OF_MONTH + 1);
        int month = calendario.get(Calendar.MONTH + 1);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}