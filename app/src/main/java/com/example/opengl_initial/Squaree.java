package com.example.opengl_initial;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Squaree {
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    static final int COORDS_PER_VERTEX = 3;
    private final int mProgram;

    //ATRIBUTOS
    //Coordenadas
    private float squareeCoords[] = new float[12];
    //Ordem
    private short drawOrder[] = new short[6];
    //Cor
    private float color[] = new float[4];

    //METODO SET
    //Coordenadas
    public void setSquareCoords(float[] squareCoords) {
        this.squareeCoords = squareCoords;
    }
    //Ordem
    public void setDrawOrder(short[] drawOrder){
        this.drawOrder = drawOrder;
    }
    //Cor
    public void setColor(float[] color) {
        this.color = color;
    }

    //CONSTRUCTOR
    public Squaree(float[] squareeCoords, short[] drawOrder, float[] color) {
        this.setSquareCoords(squareeCoords);
        this.setDrawOrder(drawOrder);
        this.setColor(color);

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
                squareeCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareeCoords);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

    //Metodo draw() para desenhar a forma
    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = squareeCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes por vertex

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