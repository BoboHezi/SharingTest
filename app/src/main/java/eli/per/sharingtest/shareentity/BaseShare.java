package eli.per.sharingtest.shareentity;

import android.app.Activity;
import android.content.Context;

import java.io.File;

public abstract class BaseShare {

    protected Context context;
    protected Activity activity;

    public BaseShare(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    /**
     * 初始化SDK
     */
    protected abstract void initSDK();

    /**
     * 分享图片
     *
     * @param imageFile
     */
    protected abstract void shareImage(File imageFile);

    /**
     * 分享视频
     *
     * @param videoFile
     */
    protected abstract void shareVideo(File videoFile);

    /**
     * 分享图片组
     *
     * @param imageFiles
     */
    protected abstract void shareMultiImage(File imageFiles[]);

}
