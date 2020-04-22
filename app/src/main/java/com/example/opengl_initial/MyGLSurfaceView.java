package com.example.opengl_initial;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        setRenderer(renderer);

        // Renderiza uma vista somente quando houver alterações nos dados do desenho.
        // Para permitir que o desenho gire automaticamente, esta linha é comentada:
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }
}