package com.lftechnology.detection;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiDetector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CameraSourceManager {

    private static final int RC_HANDLE_GMS = 9001;
    CameraSourcePreview mPreview;
    GraphicOverlay mGraphicOverlay;
    Context context;
    private CameraSource mCameraSource = null;

    public CameraSourceManager(Context context, CameraSourcePreview mPreview, GraphicOverlay mGraphicOverlay) {
        this.mGraphicOverlay = mGraphicOverlay;
        this.mPreview = mPreview;
        this.context = context;

        createCameraSource();
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private void createCameraSource() {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context.getApplicationContext()).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay, context);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        MultiDetector multiDetector = new MultiDetector.Builder()
                .add(barcodeDetector)
                .build();

        mCameraSource = new CameraSource.Builder(context.getApplicationContext(), multiDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f)
                .build();
    }

    public void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                context);
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog((Activity) context, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e("CAMERA", "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    public void onActivityPaused() {
        mPreview.stop();
    }

    public void releaseCamera() {
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }
}
