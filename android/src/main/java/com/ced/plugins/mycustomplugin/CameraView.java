package com.ced.plugins.mycustomplugin;

import android.content.Context;
import android.util.AttributeSet;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

public class CameraView extends CameraBridgeViewBase implements CameraBridgeViewBase.CvCameraViewListener2{

    //private CvProcessor cvProcessor;

    public CameraView(Context context, int cameraId) {
        super(context, cameraId);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        return null;
    }

    @Override
    protected boolean connectCamera(int width, int height) {
        return false;
    }

    @Override
    protected void disconnectCamera() {

    }
}
