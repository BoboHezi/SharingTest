package eli.per.sharingtest.shareentity;

import android.app.Activity;
import android.content.Context;

import java.io.File;

public abstract class BaseShare {

    public Context context;
    public Activity activity;

    public BaseShare(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    /**
     * 初始化SDK
     */
    public abstract void initSDK();

    /**
     * 分享图片
     * @param imageFile
     */
    public abstract void shareImage(File imageFile);

    /**
     * 分享视频
     * @param videoFile
     */
    public abstract void shareVideo(File videoFile);

    /**
     * 分享图片组
     * @param imageFiles
     */
    public abstract void shareMultiImage(File imageFiles[]);

}
