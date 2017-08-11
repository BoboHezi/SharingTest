package eli.per.sharingtest.shareentity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.io.File;

public class ShareQQ extends BaseShare implements IUiListener{

    private String appID = "1106253699";
    public Tencent tencent ;

    public ShareQQ(Context context, Activity activity) {
        super(context, activity);
        initSDK();
    }

    @Override
    public void initSDK() {
        tencent = Tencent.createInstance(appID, context);
    }

    /**
     * 分享图片给好友
     * @param imageFile 图片文件
     */
    public void shareImage(File imageFile) {
        if (imageFile == null) {
            imageFile = new File(context.getExternalFilesDir(null) + "/photo/photo.jpg");
        }

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageFile.getPath());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "SharingTest");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);

        tencent.shareToQQ(activity, params, this);
    }

    /**
     * 分享视频给好友
     * @param videoFile
     */
    @Override
    public void shareVideo(File videoFile) {
    }

    /**
     * 分享图片组给好友
     * @param imageFiles
     */
    @Override
    public void shareMultiImage(File[] imageFiles) {
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
