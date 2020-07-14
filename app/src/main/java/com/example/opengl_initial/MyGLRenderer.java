package com.example.opengl_initial;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Shape mShape;

    private float[] rotationMatrix = new float[16];

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    public volatile float mAngle;

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        //Ao modificar as coodenadas da forma, modifique também o tamanho do array na classe
        mShape = new Shape(
                new float[]{
                        //  X      Y     Z
                        -0.5f,  0.5f, 0.0f,
                        -0.5f,  0.5f, 0.0f,
                         0.5f,  0.5f, 0.0f,
                         0.5f, -0.5f, 0.0f,
                        -0.5f, -0.5f, 0.0f,
                        -0.5f,  0.5f, 0.0f
                },

                new short[]{0, 1, 2, 0, 2, 3},

                new float[]{0.431353f, 0.168194f, 0.800000f, 1.0f}
        );

    }

    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        float[] scratch = new float[16];

        //long time = SystemClock.uptimeMillis() % 4000L;
        //float angle = 0.090f * ((int) time);

        //Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);
        Matrix.setRotateM(rotationMatrix, 0, mAngle, 0, 0, -1.0f);
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);

        mShape.draw(scratch);

    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    //Metodo utilitário para compilar o GLSL
    public static int loadShader(int type, String shaderCode) {

        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

}