package com.example.animaciones_graficos_y_multimedia.figuras;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.animaciones_graficos_y_multimedia.R;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cubo {

    private FloatBuffer vertexBuffer;
    private FloatBuffer texturaBuffer;
    private int numCaras = 6;
    int[] texturaIDs = new int[1];

    private Bitmap[] bitmaps  = new Bitmap[numCaras];
    private Context context;

    private float texturaCoord[] = {
            0.0f, 0.5f,
            0.5f,0.5f,
            0.0f,0.0f,
            0.5f,0.0f
    };

    private float cubeHalfSize = 1.2f;

    private float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f,  0.5f, 0.0f,
            0.5f,  0.5f, 0.0f
    };

private int[] textureIDs = new int[numCaras];

    private int[] imageFileIDs = {
        R.raw.textura_metalica,
        R.raw.textura_metalica,
        R.raw.textura_metalica,
        R.raw.textura_metalica,
        R.raw.textura_metalica,
        R.raw.textura_metalica
    };

    public Cubo() {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());


        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(texturaCoord.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texturaBuffer = tbb.asFloatBuffer();
        texturaBuffer.put(texturaCoord);
        texturaBuffer.position(0);
    }

//        for (int cara = 0 ; cara < numCaras; cara++){
//
//            bitmaps[cara] = BitmapFactory.decodeStream(context.getResources().openRawResource(imageFileIDs[cara]));
//
//            int imgWidth = bitmaps[cara].getWidth();
//            int imgheight = bitmaps[cara].getHeight();
//            float faceWidth = 2.0f;
//            float faceheight = 2.0f;
//
//            if (imgWidth > imgheight){
//                faceheight = faceheight * imgheight / imgWidth;
//            }else{
//                faceWidth = faceWidth * imgWidth / imgheight;
//            }
//
//
//            float faceleft = -faceWidth /2;
//            float faceRight = -faceleft;
//            float faceTop = faceheight / 2;
//            float faceBottom = -faceTop;
//
//
//            float[] vertices = {
//              faceleft,faceBottom, 0.0f,
//                    faceRight, faceBottom, 0.0f,
//                    faceleft,faceTop,0.0f,
//                    faceRight, faceTop, 0.0f,
//            };
//
//            //vertexBuffer.put(vertices);
//
//            //gl.glColor4f(colores[cara][0],colores[cara][1], colores[cara][2],colores[cara][3]);
//            //gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, cara*4, 4);
//        }
        //vertexBuffer.position(0);

//        vertexBuffer.put(vertices);
//        vertexBuffer.position(0);
//
//        float[] textCoords = {
//                0.0f, 1.0f,
//                1.0f, 1.0f,
//                0.0f, 0.0f,
//                1.0f, 0.0f
//        };
//
//        ByteBuffer tbb = ByteBuffer.allocateDirect(textCoords.length * 4 * numCaras);
//        tbb.order(ByteOrder.nativeOrder());
//        textBuffer = tbb.asFloatBuffer();
//        for (int face = 0 ; face < numCaras; face++){
//            textBuffer.put(textCoords);
//        }
//        textBuffer.position(0);
//    }


    public void cargarTextura(GL10 gl){
        gl.glGenTextures(6, textureIDs, 0);


        for (int face =  0; face < numCaras; face++){
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[face]);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0 , bitmaps[face], 0);
            bitmaps[face].recycle();
        }





    }

    public void dibujar(GL10 gl){
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);


        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT,0, texturaBuffer);

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, 0.5f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(270.0f, 0.0f ,0.5f, 0.0f);
        gl.glTranslatef(0.0f, 0.f, 0.5f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(180.0f, 0.0f ,0.5f, 0.0f);
        gl.glTranslatef(0.0f, 0.f, 0.5f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(90.0f, 0.0f ,0.5f, 0.0f);
        gl.glTranslatef(0.0f, 0.f, 0.5f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(270.0f, 0.5f ,0.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.f, 0.5f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0, 4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotatef(90.0f, 0.5f ,0.0f, 0.0f);
        gl.glTranslatef(0.0f, 0.f, 0.5f);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0, 4);
        gl.glPopMatrix();

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

    }


    public void cargarTextura(GL10 gl, Context context){
        gl.glGenTextures(1, texturaIDs, 0);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texturaIDs[0]);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        InputStream istream = context.getResources().openRawResource(R.raw.textura_metalica);

        Bitmap bitmap;

        try{
            bitmap = BitmapFactory.decodeStream(istream);
        }finally {
            try{
                istream.close();
            }catch(IOException e){}
        }

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0 , bitmap, 0);
        bitmap.recycle();
    }
}
