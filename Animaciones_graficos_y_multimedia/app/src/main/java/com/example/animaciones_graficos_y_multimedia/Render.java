package com.example.animaciones_graficos_y_multimedia;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.animaciones_graficos_y_multimedia.figuras.Cubo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Render implements GLSurfaceView.Renderer {

    private Cubo cubo;
    private Context context;

    private static float anguloCubo = 0.0f;
    private static float speedCubo = -1.5f;



    public Render(Context context){
        this.context = context;
        cubo = new Cubo();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        cubo.cargarTextura(gl,context);
        gl.glEnable(GL10.GL_TEXTURE_2D);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -0.5f);
        gl.glScalef(0.8f, 0.8f, 0.8f);
        gl.glRotatef(anguloCubo,1.0f,1.0f,0.0f);

        cubo.dibujar(gl);

        anguloCubo += speedCubo;


    }
}
