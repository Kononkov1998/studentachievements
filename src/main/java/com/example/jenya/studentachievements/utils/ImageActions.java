package com.example.jenya.studentachievements.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.jenya.studentachievements.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageActions {
    public static File convertBitmapToFile(Bitmap bitmap, Context ctx) throws IOException {
        File f = new File(ctx.getCacheDir(), "avatar");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
        return f;
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static int getAvatarSizeInPx(Context ctx) {
        return ctx.getResources().getDimensionPixelSize(R.dimen.image_size);
    }

    public static float dpFromPx(float px, Context ctx) {
        return px / ctx.getApplicationContext().getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(float dp, Context ctx) {
        return dp * ctx.getApplicationContext().getResources().getDisplayMetrics().density;
    }
}
