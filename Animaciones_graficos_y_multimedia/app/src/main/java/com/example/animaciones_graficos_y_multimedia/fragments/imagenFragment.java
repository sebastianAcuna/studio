package com.example.animaciones_graficos_y_multimedia.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.animaciones_graficos_y_multimedia.MainActivity;
import com.example.animaciones_graficos_y_multimedia.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class imagenFragment extends Fragment implements View.OnClickListener {

    private Bitmap bitmap= null;
    private View view;
    private Button buttonImagen, buttonCamara;
    private TextView ruta;
    private ImageView imagen;
    private static final String FORMATO_IMAGEN = ".jpg";
    private static final String FOLDER = "/cameraPicturesFolder/";
    private File fileImagen;

    private Uri imagenUri;
    private static int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_imagen, container, false);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonCamara = (Button) view.findViewById(R.id.btn_camara);
        buttonImagen = (Button) view.findViewById(R.id.btn_abrir);
        ruta = (TextView) view.findViewById(R.id.tv_ruta);
        imagen = (ImageView) view.findViewById(R.id.image_selected);



        buttonCamara.setOnClickListener(this);
        buttonImagen.setOnClickListener(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.updateView("Media Cloud","Imagen");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_abrir:
                Intent i= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,2);
            break;
            case R.id.btn_camara:

                File miFile = new File(Environment.getExternalStorageDirectory(), FOLDER);
                boolean isCreada = miFile.exists();

                if (!isCreada){
                    isCreada=miFile.mkdirs();
                }

                if (isCreada){
                    String path = Environment.getExternalStorageDirectory()+  FOLDER  + "imagen" + FORMATO_IMAGEN;
                    fileImagen=new File(path);
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                startActivityForResult(intent, REQUEST_CODE);
            break;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent Data){
        super.onActivityResult(requestCode,resultCode,Data);


        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            //Uri imagenseleccionada = Data.getData();
            ruta.setText(fileImagen.getPath());
            bitmap = BitmapFactory.decodeFile(fileImagen.getPath());
            int height= bitmap.getHeight();
            int width=bitmap.getWidth();
            float scaleA =((float)(width/2))/width;
            float scaleB =((float)(height/2))/height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleA,scaleB);
            Bitmap nuevaimagen= Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);


            imagen.setImageBitmap(nuevaimagen);

        }

        if(requestCode == 2 && resultCode==RESULT_OK && null !=Data){
            Uri imagenseleccionada = Data.getData();
            if (imagenseleccionada != null){
                String[] path = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(imagenseleccionada,path,null,null,null);
                cursor.moveToFirst();
                int columna = cursor.getColumnIndex(path[0]);
                String pathimagen = cursor.getString(columna);
                cursor.close();
                bitmap = BitmapFactory.decodeFile(pathimagen);
                BitmapFactory.Options options= new BitmapFactory.Options();
                int height= bitmap.getHeight();
                int width=bitmap.getWidth();
                float scaleA =((float)(width/2))/width;
                float scaleB =((float)(height/2))/height;
                Matrix matrix = new Matrix();
                matrix.postScale(scaleA,scaleB);
                Bitmap nuevaimagen= Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
                ruta.setText(pathimagen);
                imagen.setImageBitmap(nuevaimagen);
            }







        }

    }

}
