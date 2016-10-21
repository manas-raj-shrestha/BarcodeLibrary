package com.lftechnology.detection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.vision.barcode.Barcode;
import com.lftechnology.barcode.R;

public class BarcodeActivity extends AppCompatActivity implements BarcodeListener {

    private BarcodeWindow barcodeWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        barcodeWindow = (BarcodeWindow) findViewById(R.id.barcode_window);
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        barcodeWindow.startCamera();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        barcodeWindow.stopPreview();
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        barcodeWindow.releaseCamera();
    }

    @Override
    public void onBarcodeDetected(Barcode barcode) {
        Log.e("Detected Code", barcode.rawValue);
    }
}
