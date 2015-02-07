package com.example.gregolm.android_101.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

/**
 * Created by Gregory on 2/6/2015.
 */
public final class BitmapAssetFactory {
    public static Bitmap getAssetBitmap(Context ctx, String assetName) {
        try {
            return BitmapFactory.decodeStream(ctx.getAssets().open(assetName));
        } catch (IOException ex) {
            return null;
        }
    }
}
