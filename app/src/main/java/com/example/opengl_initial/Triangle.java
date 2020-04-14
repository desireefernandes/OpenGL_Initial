package com.example.opengl_initial;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Triangle {

    //Definindo as formas
    private FloatBuffer vertexBuffer;

    // número de coordenadas por vértice nessa matriz
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // no sentido anti-horário:
            0.0f,  0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    // Definir cor com os valores vermelho, verde, azul e alfa (opacidade)
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private final int mProgram;

    public Triangle() {

        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();

        GLES20.glAttachShader(mProgram, vertexShader);

        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);

        // inicializa o buffer de bytes de vértice para coordenadas de forma
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (número de valores de coordenadas * 4 bytes por float)
                triangleCoords.length * 4);
        // use a ordem de bytes nativa do hardware do dispositivo
        bb.order(ByteOrder.nativeOrder());

        // cria um buffer de ponto flutuante a partir do ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // adiciona as coordenadas ao FloatBuffer
        vertexBuffer.put(triangleCoords);
        // define o buffer para ler a primeira coordenada
        vertexBuffer.position(0);
    }

        //Metodo draw() para desenhar a forma
        private int positionHandle;
        private int colorHandle;

        private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

        public void draw() {
            GLES20.glUseProgram(mProgram);

            positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

            GLES20.glEnableVertexAttribArray(positionHandle);

            GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                    GLES20.GL_FLOAT, false,
                    vertexStride, vertexBuffer);

            colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

            GLES20.glUniform4fv(colorHandle, 1, color, 0);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

            GLES20.glDisableVertexAttribArray(positionHandle);
        }

    //Desenhar a forma
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

}