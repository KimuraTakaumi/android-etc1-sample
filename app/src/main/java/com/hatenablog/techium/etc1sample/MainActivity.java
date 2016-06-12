package com.hatenablog.techium.etc1sample;

import android.opengl.ETC1Util;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private EtcGlView mEtcGlView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            InputStream is = getAssets().open("techium.pkm");
            ETC1Util.ETC1Texture texture = ETC1Util.createTexture(is);
            mEtcGlView = new EtcGlView(this, texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(mEtcGlView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEtcGlView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEtcGlView.onPause();
    }
}