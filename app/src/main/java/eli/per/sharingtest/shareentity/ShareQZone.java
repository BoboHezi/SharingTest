package eli.per.sharingtest.shareentity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.io.File;
import java.util.ArrayList;

public class ShareQZone extends BaseShare implements IUiListener {

    private String appID = "1106253699";
    public Tencent tencent;

    public ShareQZone(Context context, Activity activity) {
        super(context, activity);
        initSDK();
    }

    @Override
    public void initSDK() {
        tencent = Tencent.createInstance(appID, context);
    }

    /**
     * 分享图片到空间
     *
     * @param imageFile 图片文件
     */
    public void shareImage(File imageFile) {
        if (imageFile == null) {
            imageFile = new File(context.getExternalFilesDir(null) + "/photo/photo.jpg");
        }
        ArrayList<String> images = new ArrayList<>();
        images.add(imageFile.getPath());
        publishImages(images);
    }

    /**
     * 分享多张图片到空间
     *
     * @param imageFiles 图片文件数组
     */
    public void shareMultiImage(File imageFiles[]) {
        if (imageFiles == null) {
            imageFiles = new File(context.getExternalFilesDir(null) + "/photo/").listFiles();
        }
        ArrayList<String> images = new ArrayList<>();
        for (File file : imageFiles) {
            images.add(file.getPath());
        }
        publishImages(images);
    }

    /**
     * 分享视频到空间
     *
     * @param videoFile 视频文件
     */
    public void shareVideo(File videoFile) {
        if (videoFile == null) {
            videoFile = new File(context.getExternalFilesDir(null) + "/video/VID_20170811_105225.mp4");
        }
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHVIDEO);
        params.putString(QzonePublish.PUBLISH_TO_QZONE_VIDEO_PATH, videoFile.getPath());
        tencent.publishToQzone(activity, params, this);
    }


    /**
     * 发表图片
     *
     * @param images 图片地址数组
     */
    private void publishImages(ArrayList<String> images) {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
        tencent.publishToQzone(activity, params, this);
    }


    @Override
    public void onComplete(Object o) {
        Toast.makeText(context, "onComplete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context, "onError", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(context, "onCancel", Toast.LENGTH_SHORT).show();
    }
}
