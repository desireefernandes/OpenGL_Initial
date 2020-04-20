package com.example.opengl_initial;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    //private Triangle mTriangle;
    private Square mSquare;
    private Squaree mSqueree;

    public void onSurfaceCreated (GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        /*
         mTriangle = new Triangle (
                //Coordenadas
                new float[] {
                         0.0f,  0.622008459f, 0.0f,
                        -0.5f, -0.311004243f, 0.0f,
                         0.5f, -0.311004243f, 0.0f },
                //Cor
                new float[] {0.431353f, 0.168194f, 0.800000f, 1.0f}
                );
         */
        mSquare = new Square(
                new float[] {
                        -0.5f,  0.5f, 0.0f,
                        -0.5f, -0.5f, 0.0f,
                         0.5f, -0.5f, 0.0f,
                         0.5f,  0.5f, 0.0f },

                new short[] { 0, 1, 2, 0, 2, 3 },

                new float[] { 0.431353f, 0.168194f, 0.800000f, 1.0f }
                );

        mSqueree = new Squaree(
                new float[] {
                        -0.5f,  0.5f, 0.0f,
                         0.5f,  0.5f, 0.0f,
                         0.5f, -0.5f, 0.0f,
                        -0.5f, -0.5f, 0.0f },

                new short[] { 0, 1, 2, 0, 2, 3 },

                new float[] { 0.431353f, 0.168194f, 0.800000f, 1.0f }
        );

    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
       //mTriangle.draw();
        mSquare.draw();
        mSqueree.draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    //Metodo utilitário para compilar o GLSL
    public static int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}