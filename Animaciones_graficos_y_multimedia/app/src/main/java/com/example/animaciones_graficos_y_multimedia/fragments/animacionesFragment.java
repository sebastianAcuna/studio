package com.example.animaciones_graficos_y_multimedia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.animaciones_graficos_y_multimedia.MainActivity;
import com.example.animaciones_graficos_y_multimedia.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class animacionesFragment extends Fragment implements View.OnClickListener{
    private View view;
    private ImageView star;
    private FloatingActionButton play;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_animaciones, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        star = (ImageView) view.findViewById(R.id.start_animated);
        play = (FloatingActionButton) view.findViewById(R.id.play_anim);


        play.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        animarStar();
    }

    private void animarStar(){
        //if(star.getVisibility() == View.VISIBLE){
            AnimationSet show =(AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.animation_star_in_out);
            show.setRepeatMode(Animation.INFINITE);
            star.startAnimation(show);
        //}
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.play_anim) {

            Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
            animarStar();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.updateView("Media Cloud","Animaciones");
        }
    }
}
