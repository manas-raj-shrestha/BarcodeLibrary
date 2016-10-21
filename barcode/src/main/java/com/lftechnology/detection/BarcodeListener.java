package com.lftechnology.detection;

import com.google.android.gms.vision.barcode.Barcode;

public interface BarcodeListener {
    void onBarcodeDetected(Barcode barcode);
}
