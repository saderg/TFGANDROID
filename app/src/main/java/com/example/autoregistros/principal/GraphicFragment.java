package com.example.autoregistros.principal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.autoregistros.Graphics;
import com.example.autoregistros.R;
import com.example.autoregistros.databinding.FragmentGraphicBinding;

import java.text.SimpleDateFormat;

public class GraphicFragment extends Fragment {

    private FragmentGraphicBinding binding;
    View vista;
    int id_usuario;
    ImageButton buttonGraphic;
    EditText dateGraphic;
    String date;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGraphicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int id_usuario = this.getArguments().getInt("id_usuario");
        System.out.println("IDUSUARIO" + id_usuario);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance){
        super.onViewCreated(view, savedInstance);

        dateGraphic = view.findViewById(R.id.graphic_edit_text);

        buttonGraphic = view.findViewById(R.id.graphic_button);
        buttonGraphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = dateGraphic.getText().toString();
                System.out.println("VISTA " + date);
                Intent intent = new Intent(getActivity(), Graphics.class);
                intent.putExtra("date", date);
                //intent.putExtra("id_usuario", id_user);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}