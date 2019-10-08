package com.example.animaciones_graficos_y_multimedia.fragments;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.animaciones_graficos_y_multimedia.MainActivity;
import com.example.animaciones_graficos_y_multimedia.R;
import com.example.animaciones_graficos_y_multimedia.Render;

import java.util.Objects;

public class graficosFragment extends Fragment {


    private GLSurfaceView lienzo;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        lienzo= new GLSurfaceView(getActivity());
        lienzo.setRenderer(new Render(getActivity()));


        return lienzo;


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.updateView("Media Cloud","Gráficos");
        }
    }
}
