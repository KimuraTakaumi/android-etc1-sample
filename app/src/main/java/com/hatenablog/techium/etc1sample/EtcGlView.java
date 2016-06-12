package com.hatenablog.techium.etc1sample;

import android.content.Context;
import android.opengl.ETC1Util;
import android.opengl.GLSurfaceView;

public class EtcGlView extends GLSurfaceView {

    public EtcGlView(Context context, ETC1Util.ETC1Texture texture) {
        super(context);
        setRenderer(new EtcRenderer(texture));
    }
}