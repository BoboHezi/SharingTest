package eli.per.sharingtest.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.ThumbnailUtils;

import java.io.ByteArrayOutputStream;

public class Util {

    /**
     * 将Bitmap对象转为字节数组
     * @param bitmap
     * @param needRecycle
     * @return
     */
    public static byte[] bitmap2ByteArray(final Bitmap bitmap, final boolean needRecycle) {
        int width;

        if (bitmap.getHeight() > bitmap.getWidth()) {
            width = bitmap.getWidth();
        } else {
            width = bitmap.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, width, width), new Rect(0, 0, width, width), null);

            if (needRecycle)
                bitmap.recycle();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            localBitmap.recycle();
            byte[] result = baos.toByteArray();
            try {
                baos.close();
                return result;
            } catch (Exception e){
            }
            width = bitmap.getHeight();
        }
    }

    /**
     *
     * @param type
     * @return
     */
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 获取视频的缩略图
     * @param videoPath 视频路径
     * @param width     缩略图宽度
     * @param height    缩略图高度
     * @param kind
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

}
