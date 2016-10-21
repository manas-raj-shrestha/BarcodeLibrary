package com.lftechnology.detection;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class BarcodeWindow extends LinearLayout {
    CameraSourceManager cameraSourceManager;
    CameraSourcePreview cameraSourcePreview;
    GraphicOverlay graphicOverlay;

    public BarcodeWindow(Context context) {
        this(context, null, 0);
    }

    public BarcodeWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarcodeWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        addCameraSourcePreview(attrs);
    }

    private void addCameraSourcePreview(AttributeSet attrs) {
        cameraSourcePreview = new CameraSourcePreview(getContext(), attrs);
        this.addView(cameraSourcePreview);
        graphicOverlay = new GraphicOverlay(getContext(), attrs);
        cameraSourcePreview.addView(graphicOverlay);
        cameraSourceManager = new CameraSourceManager(getContext(), cameraSourcePreview, graphicOverlay);

    }

    public void startCamera() {
        cameraSourceManager.startCameraSource();
    }

    public void stopPreview() {
        cameraSourcePreview.stop();
    }

    public void releaseCamera() {
        cameraSourceManager.releaseCamera();
    }


}
