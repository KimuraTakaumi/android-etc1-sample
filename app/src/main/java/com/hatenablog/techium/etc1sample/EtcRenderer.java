package com.hatenablog.techium.etc1sample;

import android.opengl.ETC1;
import android.opengl.ETC1Util;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EtcRenderer implements GLSurfaceView.Renderer {

    private ETC1Util.ETC1Texture mEtc1Texture;

    public EtcRenderer(ETC1Util.ETC1Texture etc1Texture) {
        mEtc1Texture = etc1Texture;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        gl10.glViewport(0, 0, width, height);
        gl10.glEnable(GL10.GL_TEXTURE_2D);
        int[] buffers = new int[1];
        gl10.glGenTextures(1, buffers, 0);
        int texture = buffers[0];
        gl10.glBindTexture(GL10.GL_TEXTURE_2D, texture);
        GLES10.glCompressedTexImage2D(GL10.GL_TEXTURE_2D, 0, ETC1.ETC1_RGB8_OES, mEtc1Texture.getWidth(), mEtc1Texture.getHeight(),
                0, mEtc1Texture.getData().remaining(), mEtc1Texture.getData());
//        ByteBuffer decodedData = ByteBuffer.allocateDirect(3 * mEtc1Texture.getWidth() * mEtc1Texture.getHeight())
//                    .order(ByteOrder.nativeOrder());
//        ETC1.decodeImage(mEtc1Texture.getData(),decodedData, mEtc1Texture.getWidth(), mEtc1Texture.getHeight(), 3, 3 * mEtc1Texture.getWidth() );

        gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
        float uv[] = {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };

        ByteBuffer bbuv = ByteBuffer.allocateDirect(uv.length * 4);
        bbuv.order(ByteOrder.nativeOrder());
        FloatBuffer fbuv = bbuv.asFloatBuffer();
        fbuv.put(uv);
        fbuv.position(0);

        gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl10.glTexCoordPointer(2, GL10.GL_FLOAT, 0, fbuv);
        float positions[] = {
                -1.0f, 1.0f, 0.0f,
                -1.0f, -1.0f, 0.0f,
                1.0f, 1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
        };

        ByteBuffer bb = ByteBuffer.allocateDirect(positions.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(positions);
        fb.position(0);

        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, fb);
        gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }
}
