package com.example.animaciones_graficos_y_multimedia;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class audioFragment extends Fragment {

    private Button buttonAudio, buttonGrabarAudio,buttonRepoducirAudio;
    private TextView ruta;

    private static final int REQUEST_CODE = 1;
    private static String nombreAudio = null;
    private static final String FORMATO = ".3gp";
    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null;

    private File fileImagen;

    private void comenzarReproduccion(){
        mediaPlayer = new MediaPlayer();
        try{

            if (nombreAudio!= null){
                Toast.makeText(getActivity(), "Reproduciendo audio...", Toast.LENGTH_SHORT).show();
                mediaPlayer.setDataSource(nombreAudio);
                mediaPlayer.prepare();
                mediaPlayer.start();
                buttonRepoducirAudio.setText("Detener Reproduccion");

            }else{
                Toast.makeText(getActivity(), "Sin Audio que reproducir", Toast.LENGTH_SHORT).show();
            }


        }catch(IOException e){
            Toast.makeText(getActivity(), "Ha ocurrido un error en la reproducción", Toast.LENGTH_SHORT).show();
        }
    }

    private void detenerReproduccion(){
        mediaPlayer.release();
        mediaPlayer = null;
        buttonRepoducirAudio.setText("Reproducir audio");
        Toast.makeText(getActivity(), "Reproducción de audio detenida", Toast.LENGTH_SHORT).show();
    }

    private void onPlay(boolean comenzarRep){
        if (comenzarRep){
            comenzarReproduccion();
        }else{
            detenerReproduccion();
        }
    }


    private void detenerGrabacion(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        String rutass = Environment.getExternalStorageDirectory() + "/" +"audio"+ FORMATO;

        Toast.makeText(getActivity(), "Se ha guardado el audio en:\n" +
                Environment.getExternalStorageDirectory() + "/" +"audio"+ FORMATO, Toast.LENGTH_LONG).show();

        buttonGrabarAudio.setText("Capturar");
        ruta.setText("");
        ruta.setText(rutass);
    }

    private void grabando(boolean comenzado){
        if (comenzado){
            comenzarGrabacion();
        }else{
            detenerGrabacion();
        }
    }


    private void comenzarGrabacion(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(nombreAudio);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try{
            mediaRecorder.prepare();
        }catch(IOException e){
            Toast.makeText(getActivity(), "No se grabará correctamente", Toast.LENGTH_SHORT).show();
        }
        mediaRecorder.start();
        buttonGrabarAudio.setText("Detener");
        Toast.makeText(getActivity(), "Grabando audio...", Toast.LENGTH_SHORT).show();
    }

    private boolean verificacion = true;
    private boolean verificacion2 = true;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     return inflater.inflate(R.layout.fragment_audio, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonGrabarAudio = (Button) view.findViewById(R.id.btn_camara);
        buttonAudio = (Button) view.findViewById(R.id.btn_abrir);
        buttonRepoducirAudio = (Button) view.findViewById(R.id.btnPlayAudio);
        ruta = (TextView) view.findViewById(R.id.tv_ruta);



        nombreAudio = Environment.getExternalStorageDirectory() + "/" + "audio" + FORMATO;

        buttonGrabarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    grabando(verificacion);
                    verificacion = !verificacion;
            }
        });

        buttonRepoducirAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(verificacion2);
                verificacion2 = !verificacion2;
            }
        });


        buttonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,2);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //nombreAudio = Environment.getExternalStorageDirectory() + "/" + "audio" + FORMATO;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mediaRecorder != null){
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.updateView("Media Cloud","Audio");
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent Data){
        super.onActivityResult(requestCode,resultCode,Data);

        if(requestCode == 2 && resultCode==RESULT_OK && null !=Data){
            Uri imagenseleccionada = Data.getData();
            if (imagenseleccionada != null){
                String[] path = {MediaStore.Audio.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(imagenseleccionada,path,null,null,null);
                cursor.moveToFirst();
                int columna = cursor.getColumnIndex(path[0]);
                String pathAudio = cursor.getString(columna);
                cursor.close();

                nombreAudio = pathAudio;
                ruta.setText("");
                ruta.setText(pathAudio);


            }







        }

    }
}
