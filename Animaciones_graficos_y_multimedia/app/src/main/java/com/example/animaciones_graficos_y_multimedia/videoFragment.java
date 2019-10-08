package com.example.animaciones_graficos_y_multimedia;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class videoFragment extends Fragment {

    private Button btnAbrirVideo, btnGrabarVideo;
    private VideoView videoView;
    private File fileImagen;
    private static int REQUEST_CODE = 1;
    private TextView ruta;

    Toolbar toolbar;

    private static final String FOLDER = "/cameraVideoFolder/";
    private static final String FORMATO_IMAGEN = ".mp4";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        btnAbrirVideo = (Button)view.findViewById(R.id.btn_abrir);
        btnGrabarVideo = (Button)view.findViewById(R.id.btn_camara);
        videoView = (VideoView)view.findViewById(R.id.video_selected);

        ruta = (TextView) view.findViewById(R.id.tv_ruta);

        btnGrabarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                File miFile = new File(Environment.getExternalStorageDirectory(), FOLDER);
                boolean isCreada = miFile.exists();

                if (!isCreada){
                    isCreada=miFile.mkdirs();
                }
                if (isCreada){
                    String path = Environment.getExternalStorageDirectory()+  FOLDER  + "video" + FORMATO_IMAGEN;
                    fileImagen=new File(path);
                }

                Intent videoIntent = new Intent (MediaStore.ACTION_VIDEO_CAPTURE);
                videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                startActivityForResult(videoIntent,REQUEST_CODE);
            }
        });


        btnAbrirVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,2);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            MediaController mediaController = new MediaController(getActivity());

            videoView.setMediaController(mediaController);

            videoView.setVideoURI(Uri.fromFile(fileImagen));
            videoView.start();


            ruta.setText(fileImagen.getPath());

            mediaController.setAnchorView(videoView);

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.start();
                }
            });
        }

        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri imagenseleccionada = data.getData();
            if (imagenseleccionada != null) {
                String[] path = {MediaStore.Video.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(imagenseleccionada, path, null, null, null);
                cursor.moveToFirst();
                int columna = cursor.getColumnIndex(path[0]);
                String pathVideo = cursor.getString(columna);
                cursor.close();


                fileImagen = new File(pathVideo);

                ruta.setText(pathVideo);

                MediaController mediaController = new MediaController(getActivity());
                videoView.setMediaController(mediaController);

                videoView.setVideoURI(Uri.fromFile(fileImagen));
                videoView.start();

            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.updateView("Media Cloud","Video");
        }
    }
}
