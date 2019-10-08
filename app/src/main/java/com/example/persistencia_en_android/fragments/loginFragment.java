package com.example.persistencia_en_android.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.persistencia_en_android.MainActivity;
import com.example.persistencia_en_android.R;
import com.example.persistencia_en_android.Utilidad;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class loginFragment extends Fragment implements View.OnClickListener {


    private TextView user, pass;
    private CheckBox check;
    private SharedPreferences prefs;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(Utilidad.SHARED_CONFIG_TEL, MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(prefs.getBoolean(Utilidad.SHARED_CHECK_LOGIN,false)){
            user.setText(prefs.getString(Utilidad.SHARED_USER_LOGIN,""));
            pass.setText(prefs.getString(Utilidad.SHARED_PASS_LOGIN, ""));
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        user = (TextView) view.findViewById(R.id.user_name);
        pass =  (TextView) view.findViewById(R.id.pass_user);
        Button btnIngresar = (Button) view.findViewById(R.id.btn_ingresar);
        check = (CheckBox) view.findViewById(R.id.check_recordar);



        btnIngresar.setOnClickListener(this);


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity activity = (MainActivity)getActivity();
        if (activity != null){
            activity.setDrawerEnable(false);
            activity.updateView("Parking Control","");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ingresar){
            if (TextUtils.isEmpty(user.getText().toString() ) || TextUtils.isEmpty(pass.getText().toString())){
                Snackbar.make(Objects.requireNonNull(getView()), "Debe ingresar usuario - contraseña ", Snackbar.LENGTH_LONG).show();
            }else{

                if (check.isChecked()){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(Utilidad.SHARED_USER_LOGIN, user.getText().toString());
                    editor.putString(Utilidad.SHARED_PASS_LOGIN, pass.getText().toString());
                    editor.putBoolean(Utilidad.SHARED_CHECK_LOGIN, true);
                    editor.apply();
                }

                MainActivity activity = (MainActivity) getActivity();
                if (activity != null){
                    activity.cambiarFragment(new parqueosFragment(), "parqueos_fragment");
                }

            }
        }
    }
}
